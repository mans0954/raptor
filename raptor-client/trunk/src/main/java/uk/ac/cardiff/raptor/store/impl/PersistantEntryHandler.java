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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;

import uk.ac.cardiff.model.event.Event;
import uk.ac.cardiff.model.event.ShibbolethIdpAuthenticationEvent;
import uk.ac.cardiff.raptor.runtimeutils.ReflectionHelper;
import uk.ac.cardiff.raptor.store.AsynchronousEntryStorage;
import uk.ac.cardiff.raptor.store.EntryHandler;
import uk.ac.cardiff.raptor.store.StoreEntriesTask;
import uk.ac.cardiff.raptor.store.dao.RaptorDataConnection;
import uk.ac.cardiff.raptor.store.dao.StorageException;

/**
 * @author philsmart
 *
 */
public class PersistantEntryHandler implements EntryHandler {
	// TODO can use the spring initialisation callback methods to initials the
	// bean after all properties set etc, rather than initialising on the set
	// method of the engine class

	/** class logger */
	private final Logger log = LoggerFactory.getLogger(PersistantEntryHandler.class);

	/** data connection used to persist entries */
	private RaptorDataConnection dataConnection;

	/**
	 * set of all entries stored by this EntryHandler should never be used as
	 * memory overhead is too high for large databases
	 */
	private Set<Event> entries;

	/** Used to hold events temporarily before they are persisted, allows
	 * resilience if events can not be immediately stored, for example
	 * failure of the underlying persistent store */
	private Set<Event> storeQueue;

	public PersistantEntryHandler(RaptorDataConnection dataConnection) {
		this.setDataConnection(dataConnection);

	}

	/**
	 * Initialises the entry handler. In particular, loads all entries from the
	 * main datastore, through the <code>dataConnection</code> instance.
	 */
	public void initialise() {
		log.info("Persistant entry handler [{}] initialising", this);
		Integer rowCount = (Integer) dataConnection.runQueryUnique("select count(*) from Event", null);
		log.info("Persistent data store has {} entries", rowCount);
		log.info("Persistant entry handler [{}] started", this);
	}

	/**
	 * This method loads all <code>Entries</code> in the <code>entries</code>
	 * variable. This method is often not used as large datasets will require
	 * vast amounts of memory
	 */
	private void loadEntries() {
		log.info("Loading entries from main datastore");
		List<Event> entriesAsList = dataConnection.runQuery("from Event", null);
		log.info("MUA has loaded " + entriesAsList.size() + " entries from main datastore");
		entries = new LinkedHashSet<Event>(entriesAsList);
	}

	public List query(String query) {
		 log.trace("SQL query to entry handler [{}]",query);
		return dataConnection.runQuery(query, null);
	}

	public Object queryUnique(String query) {
		 log.trace("SQL query to entry handler [{}]",query);
		return dataConnection.runQueryUnique(query, null);
	}

	public Object queryUnique(String query, Object[] parameters) {
		return dataConnection.runQueryUnique(query, parameters);
	}

	/**
	 *
	 *
	 * @param entries the list of events that are to be stored
	 * @throws
	 */
	public void addEntries(List<Event> entries) throws StorageException{
		log.info("Persistent Entry Handler has {} entries, with {} new entries inputted", this.getNumberOfEntries(), entries.size());
		int duplicates = 0;
		List<Event> persist = new ArrayList<Event>();
		for (Event event : entries) {
			int hashcode = 0;
			try {
				hashcode = ((Integer) ReflectionHelper.getValueFromObject("hashCode", event)).intValue();
			} catch (Exception e) {
			    log.error("Could not get hashcode for event {}, event not stored");
			    continue;
			}
			//int numberOfDuplicates = ((Integer) dataConnection.runQueryUnique("select count(*) from " + event.getClass().getSimpleName()
			//		+ " where eventTime = '" + event.getEventTime() + "' and hashCode ='" + hashcode + "'", null)).intValue();
			Object[] parameters= new Object[]{hashcode};
			log.debug("Values: "+Arrays.toString(parameters));
			int numberOfDuplicates = ((Integer) dataConnection.runQueryUnique("select count(*) from " + event.getClass().getSimpleName()+" where eventTime = '" + event.getEventTime() + "'"+
					"and hashCode =?", parameters)).intValue();

			if (numberOfDuplicates == 0){
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

		log.info("Total No. of Entries after addition = {}, finding {} duplicates", this.getNumberOfEntries(), duplicates);
	}

	public void addEntry(Event entry) {
		entries.add(entry);

	}

	public void endTransaction() {
		log.debug("Saving transaction for MUA");
		dataConnection.saveAll(entries);
		log.debug("Saving transaction for MUA...Done");
	}

	public Set getEntries() {
		return entries;
	}

	public void removeAllEntries() {
		log.debug("Removing all entries from this entry handler");
		dataConnection.deleteAllEntries(entries);
		entries.clear();
	}

	public void setDataConnection(RaptorDataConnection dataConnection) {
		this.dataConnection = dataConnection;
	}

	public RaptorDataConnection getDataConnection() {
		return dataConnection;
	}

	public void setEntries(Set<Event> entries) {

	}

	public int getNumberOfEntries() {
		return (Integer) dataConnection.runQueryUnique("select count(*) from Event", null);
	}


}
