/**
 * Copyright (C) 2010 Cardiff University, Wales <smartp@cf.ac.uk>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
/**
 *
 */
package uk.ac.cardiff.raptor.store.impl;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.springframework.dao.DataAccessException;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import uk.ac.cardiff.model.event.Event;
import uk.ac.cardiff.raptor.store.EventHandler;
import uk.ac.cardiff.raptor.store.dao.RaptorDataConnection;
import uk.ac.cardiff.raptor.store.dao.StorageException;

/**
 * @author philsmart
 *
 */
public class PersistantEventHandler implements EventHandler {
    
	/** class logger. */
	private final Logger log = LoggerFactory.getLogger(PersistantEventHandler.class);

	/** data connection used to persist entries. */
	private RaptorDataConnection dataConnection;


	/** Used to hold events temporarily before they are persisted, allows
	 * resilience if events can not be immediately stored, for example
	 * failure of the underlying persistent store. Also, as its a set, it
	 * prevents the addition of duplicate values */
	private Set<Event> persistQueue;

	public PersistantEventHandler(RaptorDataConnection dataConnection) {
		this.setDataConnection(dataConnection);
		persistQueue = new HashSet<Event>();

	}

	/**
	 * Initialises the entry handler. In particular, loads all entries from the
	 * main datastore, through the <code>dataConnection</code> instance.
	 */
	public void initialise() {
		log.info("Persistant entry handler [{}] initialising", this);
		log.info("Persistent data store has {} entries for [{}]", this.getNumberOfEvents(),this);
		log.info("Persistant entry handler [{}] started", this);
	}


	public List query(String query) {
		 log.trace("SQL query to entry handler [{}]",query);
		return dataConnection.runQuery(query, null);
	}

	public Object queryUnique(String query) {
		 log.trace("SQL query to entry handler [{}]",query);
		return dataConnection.runQueryUnique(query, null);
	}

	public List query(String query, Object[] parameters) {
		log.trace("SQL query to entry handler [{}], with parameters [{}]",query,Arrays.asList(parameters));
		return dataConnection.runQuery(query, parameters);
	}

	public Object queryUnique(String query, Object[] parameters) {
		return dataConnection.runQueryUnique(query, parameters);
	}

	public void update(String query, Object[] parameters) throws StorageException{
		try{
			dataConnection.runUpdate(query, parameters);
		}
		catch(DataAccessException e){
			throw new StorageException("Could not perform entry handler update",e);
		}
	}

	public List query(String query, Object[] parameters, int maxNoResults) {
	      return dataConnection.runQuery(query, parameters, maxNoResults);
	}

	public void save(Object object) throws StorageException {
		try{
			dataConnection.save(object);
		}
		catch (DataAccessException e){
			throw new StorageException("Could not save object",e);
		}
	}

	public void saveAll(Collection object) throws StorageException {
		try{
			dataConnection.saveAll(object);
		}
		catch (DataAccessException e){
			throw new StorageException("Could not save collection",e);
		}
	}

	/**
	 * The <code>entries</code> are stored in the <code>persistQueue</code> until they are persisted. If an exception is thrown before
	 * they are persisted, they remain in the <code>persistQueue</code>. This method then saves this collection in batch, as opposed to the
	 * <code>addEntry</code> method which stores events one at a time. In order to detect duplicates, a query is run over the database in order
	 * to check duplicates within already stored events, as well as adding new events to a <code>Set</code> <code>persist</code> which
	 * prevents duplicates in the incomming <code>List</code> of entries
	 *
	 * @param entries the list of events that are to be stored
	 * @throws
	 */
	public void addEvents(List<Event> entries) throws StorageException{
		log.info("Persistent Entry Handler has {} entries, with {} new entries inputted, and {} exist in the queue",
				new Object[]{this.getNumberOfEvents(), entries.size(),persistQueue.size()});

		int duplicates = 0;
		persistQueue.addAll(entries);

		Set<Event> persist = new HashSet<Event>();
		for (Event event : persistQueue) {
			String query ="select count(*) from "+event.getClass().getSimpleName()+" where eventTime = ? and eventId =?";
			Object[] parameters= new Object[]{event.getEventTime(),event.getEventId()};
			long storedDuplicates = ((Long) dataConnection.runQueryUnique(query, parameters)).intValue();
			if (storedDuplicates == 0){
			    persist.add(event);
			}
			else{
			    duplicates++;
			}
		}
		try{
		    dataConnection.saveAll(persist);
		}
		catch(DataAccessException e){
		    throw new StorageException("Could not persist events",e);
		}
		persistQueue.clear();
		log.info("Total No. of Entries after addition = {}, finding {} duplicates", this.getNumberOfEvents(), duplicates);
	}

	public boolean addEvent(Event event) {

		String query ="select count(*) from "+event.getClass().getSimpleName()+" where eventTime = ? and eventId =?";
		Object[] parameters= new Object[]{event.getEventTime().toDate(),event.getEventId()};
		int numberOfDuplicates = ((Integer) dataConnection.runQueryUnique(query, parameters)).intValue();

		if (numberOfDuplicates == 0){
		   dataConnection.save(event);
		   return true;
		}
		else{
			log.error("Duplicated event found\n{}", event);
			return false;
		}

	}

	public List<Event> getEvents() {
		List<Event> runQuery = dataConnection.runQuery("from Event",null);
		return runQuery;
	}

	public void removeAllEvents() {
		log.error("Method removeAllEntries not yet implemented");
	}

	public void setDataConnection(RaptorDataConnection dataConnection) {
		this.dataConnection = dataConnection;
	}

	public RaptorDataConnection getDataConnection() {
		return dataConnection;
	}

	public void setEvents(Set<Event> entries) {

	}

	public long getNumberOfEvents() {
		Object result = dataConnection.runQueryUnique("select count(*) from Event", null);
		return (Long) result;
	}

	public DateTime getLatestEventTime(){
		return (DateTime) dataConnection.runQueryUnique("select max(eventTime) from Event", null);
	}

	//TODO Implementation may not work - PLEASE DO USE
	public void removeEventsBefore(DateTime earliestReleaseTime, Set<Integer> latestEqualEntries) {
		dataConnection.runQueryUnique("delete from Event where eventTime < ?", new Object[]{earliestReleaseTime.toDate()});
		for (Iterator<Integer> entries = latestEqualEntries.iterator();entries.hasNext();){
		    Integer hash = entries.next();
		    dataConnection.runQueryUnique("delete from Event where hashCode = ?", new Object[]{hash});
        }

    }

}
