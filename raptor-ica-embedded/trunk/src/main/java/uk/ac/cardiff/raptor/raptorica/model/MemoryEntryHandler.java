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

package uk.ac.cardiff.raptor.raptorica.model;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import uk.ac.cardiff.model.event.Event;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class MemoryEntryHandler implements EntryHandler{
	static Logger log = LoggerFactory.getLogger(MemoryEntryHandler.class);

	/* pointer to the last recorded entry, for incremental update*/
	private DateTime latestEntryTime;

	/* record of the last entry that was sent over SOAP*/
	private DateTime lastPublishedEntryTime;

	/* set of all entries stored by this EntryHandler */
	Set<Event> entries;

	/* stores the set of latest unique entries. That is, those with the latest and same
	 *  DateTime, but different state (attribute values). This set is check when adding new
	 *  entries, and is not removed when records are removed.
	 */
	Set<Event> latestEqualEntries;

	public MemoryEntryHandler(){
		entries = new LinkedHashSet<Event>();
		latestEqualEntries = new LinkedHashSet<Event>();
	}

	public void addEntries(Set<Event> entries){
		log.debug("Current: "+this.entries.size()+" in: "+entries.size());

		for (Event entry: entries){
			if (isNewerOrEqual(entry))this.entries.add(entry);
		//	else {log.debug("entry: "+entry.getEventTime()+" latest: "+latestEntryTime+" isafter: "+entry.getEventTime().isAfter(latestEntryTime));}
			updateLastEntry(entry);

		}
		log.debug("Total No. of Entries "+this.entries.size()+" Latest Entry at: "+getLatestEntryTime());
	}


	/**
	 * If newer entry, just add to the set.
	 * If equal, first check the <code>latestEqualEntries</code> set,
	 * if contained in this set, then it has already been added. If not
	 * add. Importantly, we do not just add to the entries set (which would
	 * not allow duplicates) because the set is cleared when information is sent
	 * to the UA.
	 */
	public void addEntry(Event entry){
	    boolean isAfter = isAfter(entry);
	    boolean isEqual = isEqual(entry);
	    if (isAfter){
		entries.add(entry);
		updateLastEntry(entry);
	    }
	    else if (isEqual){
		boolean isAlreadyInLatest = latestEqualEntries.contains(entry);
		if (isAlreadyInLatest){
		    log.error("Duplicated entries found\n{}",entry);
		}
		if (!isAlreadyInLatest){
		    entries.add(entry);
		    updateLastEntry(entry);
		}
	    }
	  //  log.debug("Entry was before {}",entry);

	}

	private void updateLastEntry(Event entry){
		DateTime entryTime = entry.getEventTime();
		if (getLatestEntryTime()==null)setLatestEntryTime(entryTime);
		if (entryTime.isAfter(getLatestEntryTime())){
		    setLatestEntryTime(entryTime);
		    latestEqualEntries.clear();
		    latestEqualEntries.add(entry);
		}
		if (entryTime.isEqual(getLatestEntryTime())){
		    latestEqualEntries.add(entry);
		}
	}

	public void setLatestEntryTime(DateTime latestEntryTime) {
		this.latestEntryTime = latestEntryTime;
	}

	public DateTime getLatestEntryTime() {
		return latestEntryTime;
	}

	/**
	 * @param authE
	 * @return
	 */
	public boolean isNewerOrEqual(Event authE) {

		if (latestEntryTime==null) return true;
		if (!authE.getEventTime().isBefore(latestEntryTime)) return true;
		return false;
	}

	/* (non-Javadoc)
	 * @see main.uk.ac.cf.model.EntryHandler#isEqualTime(uk.ac.cardiff.model.Entry)
	 */
	@Override
	public boolean isEqual(Event authE) {
	    if (latestEntryTime==null) return false;
	    return authE.getEventTime().isEqual(latestEntryTime);
	}

	/* (non-Javadoc)
	 * @see main.uk.ac.cf.model.EntryHandler#isNewer(uk.ac.cardiff.model.Entry)
	 */
	@Override
	public boolean isAfter(Event authE) {
	    if (latestEntryTime==null) return true;
	    return authE.getEventTime().isAfter(latestEntryTime);
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
	public Set getEntries() {
	    return entries;

	}

	/**
	 *
	 */
	public void removeAllEntries() {
	    /* remove all entries, but do not reset last entry, as this is still
	     * used not to add previously parsed entries.
	     */
	    entries.clear();


	}

	public void setLastPublishedEntryTime(DateTime lastPublishedEntryTime) {
	    this.lastPublishedEntryTime = lastPublishedEntryTime;
	}

	public DateTime getLastPublishedEntryTime() {
	    return lastPublishedEntryTime;
	}

	/* (non-Javadoc)
	 * @see main.uk.ac.cf.model.EntryHandler#initialise()
	 */
	@Override
	public void initialise() {
	    // TODO Auto-generated method stub

	}











}
