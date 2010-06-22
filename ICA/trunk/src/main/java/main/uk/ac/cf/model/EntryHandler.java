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
 * {@literal} this class manages interaction with the internal model of the ICA
 *
 */

package main.uk.ac.cf.model;

import java.util.ArrayList;
import java.util.List;

import main.uk.ac.cf.dao.external.file.LogFileParser;

import org.apache.log4j.Logger;
import org.joda.time.DateTime;


public class EntryHandler {
	static Logger log = Logger.getLogger(EntryHandler.class);

	/* pointer to the last recorded entry, for incremental update*/
	private DateTime latestEntryTime;

	/* list of all entries stored by this EntryHanlder */
	List<Entry> entries;

	public EntryHandler(){
		entries = new ArrayList<Entry>();
	}

	public void addEntries(List<Entry> entries){
		log.debug("Current: "+this.entries.size()+" in: "+entries.size());

		for (Entry entry: entries){
			/* this check is done again, just in case it was not properly handled by the parsing agent*/
			if (isNewerOrEqual(entry))this.entries.add(entry);
		//	else {log.debug("entry: "+entry.getEventTime()+" latest: "+latestEntryTime+" isafter: "+entry.getEventTime().isAfter(latestEntryTime));}
			updateLastEntry(entry);

		}
		log.debug("Total No. of Entries "+this.entries.size()+" Latest Entry at: "+getLatestEntryTime());
	}

	private void updateLastEntry(Entry entry){
		DateTime entryTime = entry.getEventTime();
		if (getLatestEntryTime()==null)setLatestEntryTime(entryTime);
		if (entryTime.isAfter(getLatestEntryTime()))setLatestEntryTime(entryTime);
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
	public boolean isNewerOrEqual(Entry authE) {
		// TODO Auto-generated method stub
		if (latestEntryTime==null) return true;
		if (!authE.getEventTime().isBefore(latestEntryTime)) return true;
		return false;
	}

	/**
	 * pushes the latestEntryTime by 1 millisecond (see LogFileParser.java for explanation)
	 */
	public void endTransaction() {
		latestEntryTime = new DateTime(latestEntryTime.getMillis()+1);

	}

	/**
	 * @return the list of authentication or usage entries currently stored by the entry handler
	 */
	public List getEntries() {
	    return entries;

	}


}
