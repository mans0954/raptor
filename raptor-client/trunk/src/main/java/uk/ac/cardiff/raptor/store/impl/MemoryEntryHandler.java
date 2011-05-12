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
 *
 *
 * @author philsmart
 * {@literal} this class manages interaction with the internal model of the ICA. The class
 * is responsible for making sure duplicate entries, and entries older than the latest
 * are not added to the entry set
 *
 */

package uk.ac.cardiff.raptor.store.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import uk.ac.cardiff.model.event.Event;
import uk.ac.cardiff.raptor.runtimeutils.ReflectionHelper;
import uk.ac.cardiff.raptor.store.EntryHandler;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class MemoryEntryHandler implements EntryHandler{

        /** Class logger */
	private final Logger log = LoggerFactory.getLogger(MemoryEntryHandler.class);

	/** pointer to the last recorded entry, for incremental update*/
	private DateTime latestEntryTime;

	/** record of the last entry that was sent over SOAP*/
	private DateTime lastPublishedEntryTime;

	/** set of all entries stored by this EntryHandler */
	private Set<Event> entries;


	public MemoryEntryHandler(){
		entries = new LinkedHashSet<Event>();
	}

	public void addEntries(List<Event> entries){
		log.debug("Current: "+this.entries.size()+" in: "+entries.size());
		int notAdded = 0;
		for (Event event: entries){
		    boolean didAdd = addEntry(event);
		    if (didAdd==false)
		        notAdded++;

		}
		log.debug("Total No. of Entries {}, Lastest Entry at: {}, with {} duplicates",new Object[]{this.entries.size(),getLatestEntryTime(),notAdded});
	}



	/**
	 * Checks whether this event is already stored, if not, then it
	 * adds the <code>event</code> to the <code>entries<code> set.
	 *
	 * @param event the event to store
	 */
	public boolean addEntry(Event event){
	    if (!entries.contains(event)){
	        entries.add(event);
	        updateLastEntry(event);
	        return true;
	    }
	    else{
	        return false;
	    }

	}

	private void updateLastEntry(Event event){
		DateTime entryTime = event.getEventTime();
		if (getLatestEntryTime()==null)setLatestEntryTime(entryTime);
		if (entryTime.isAfter(getLatestEntryTime())){
		    setLatestEntryTime(entryTime);

		}
	}

	public void removeEventsBefore(DateTime earliestReleaseTime, Set<Integer> latestEqualEntries) {
            log.debug("Removing events earlier than {}, or in the set of last equal events sent (from {} events)",earliestReleaseTime,
                    latestEqualEntries.size());
            ArrayList<Event> toRemove = new ArrayList<Event>();
            for (Event event : entries){
                    if (event.getEventTime().isBefore(earliestReleaseTime))
                                    toRemove.add(event);
                    if (event.getEventTime().isEqual(earliestReleaseTime)){
                        int hashcode = 0;
                        try {
                                hashcode = ((Integer) ReflectionHelper.getValueFromObject("hashCode", event)).intValue();
                        } catch (Exception e) {
                            log.error("Could not get hashcode for event {}, event not stored", event);
                        }
                        if (latestEqualEntries.contains(hashcode)){
                            toRemove.add(event);
                        }
                    }
            }
            entries.removeAll(toRemove);

}


	public void setLatestEntryTime(DateTime latestEntryTime) {
		this.latestEntryTime = latestEntryTime;
	}

	public DateTime getLatestEntryTime() {
		return latestEntryTime;
	}



	/**
	 * pushes the latestEntryTime by 1 millisecond (see LogFileParser.java for explanation)
	 */
	public void endTransaction() {
		latestEntryTime = new DateTime(latestEntryTime.getMillis()+1);

	}

	/**
	 * @return the list of entries currently stored by the entry handler
	 */
	public List<Event> getEntries() {
	    return new ArrayList<Event>(entries);

	}

	/**
	 * Removes all entries, but does not reset last entry, as this is still
         * used so as not to add previously parsed entries.
	 */
	public void removeAllEntries() {
	    entries.clear();
	}

	public void setLastPublishedEntryTime(DateTime lastPublishedEntryTime) {
	    this.lastPublishedEntryTime = lastPublishedEntryTime;
	}

	public DateTime getLastPublishedEntryTime() {
	    return lastPublishedEntryTime;
	}

	/**
	 *  This is a no-op method for all in-memory entry handlers
	 */
	public void initialise() {

	}


    public int getNumberOfEntries() {
        return entries.size();
    }

    /**
     *  This is a no-op method for all in-memory entry handlers
     */
    public List query(String query) {
        return null;
    }

    /**
     * T This is a no-op method for all in-memory entry handlers
     */
    public Object queryUnique(String query) {
        return null;
    }

    /**
     *  This is a no-op method for all in-memory entry handlers
     */
    public Object queryUnique(String query, Object[] parameters) {
        return null;
    }

    public void setEntries(Set<Event> entries) {
       this.entries = entries;

    }

    /**
     *  This is a no-op method for all in-memory entry handlers
     */
	public List query(String query, Object[] parameters) {
		return null;
	}

	/**
	 *  This is a no-op method for all in-memory entry handlers
	 */
    public List query(String query, Object[] parameters, int maxNoResults) {
        return null;
    }











}
