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
package main.uk.ac.cf.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import main.uk.ac.cf.dao.internal.ICADataConnection;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;

import uk.ac.cardiff.model.Entry;

/**
 * @author philsmart
 *
 */
public class PersistantEntryHandler implements EntryHandler {

    /* class logger */
    static Logger log = LoggerFactory.getLogger(PersistantEntryHandler.class);

    /* information about entries, e.g. last entry */
    private EntryMetadata entryInformation;

    private ICADataConnection dataConnection;

    /* set of all entries stored by this EntryHandler */
    Set<Entry> entries;



    public PersistantEntryHandler(ICADataConnection dataConnection) {
	//entries = new ArrayList<Entry>();
	this.setDataConnection(dataConnection);
	entryInformation = (EntryMetadata)dataConnection.runQueryUnique("from EntryMetadata", null);
	log.debug("Have saved entryInformaiton: "+entryInformation);
	if (entryInformation==null)entryInformation = new EntryMetadata();
	log.debug("Entry Information "+entryInformation.getLatestEqualEntries());
	//convert to set from list, maybe expensive
	List<Entry> entriesAsList = dataConnection.runQuery("from Entry", null);
	entries = new LinkedHashSet<Entry>(entriesAsList);

    }

    /*
     * (non-Javadoc)
     *
     * @see main.uk.ac.cf.model.EntryHandler#addEntries(java.util.List)
     */
    @Override
    public void addEntries(Set<Entry> entries) {
//	Object currentEntries = dataConnection.runQueryUnique("select count(*) from Entry", null);
	log.debug("Current: " + entries.size() + " in: " + entries.size());

	for (Entry entry : entries) {
	    if (isNewerOrEqual(entry))
		entries.add(entry);

	    updateLastEntry(entry);

	}
	log.debug("Total No. of Entries " + entries.size() + " Latest Entry at: " + entryInformation.getLatestEntryTime());

    }

    public void addEntry(Entry entry){
	    //log.debug("Trying to add "+entry);
	    boolean isAfter = isAfter(entry);
	    boolean isEqual = isEqual(entry);
	    if (isAfter){
		//log.debug("Is After "+entry+"  with: "+getLatestEntryTime());
		entries.add(entry);
		updateLastEntry(entry);
	    }
	    else if (isEqual){
		Integer hashcode = entry.hashCode();
		//log.debug("Equal: Checking hashcode: "+hashcode+"  in set of "+entryInformation.getLatestEqualEntries().size()+" found: "+entryInformation.getLatestEqualEntries().contains(hashcode));
		boolean isAlreadyInLatest = entryInformation.getLatestEqualEntries().contains(hashcode);
		if (isAlreadyInLatest){
		    log.error("Duplicated entries found\n{}",entry);
		}
		if (!isAlreadyInLatest){
		    entries.add(entry);
		    updateLastEntry(entry);
		}
	    }


	}

    private void updateLastEntry(Entry entry) {
	DateTime entryTime = entry.getEventTime();
	if (entryInformation.getLatestEntryTime()==null)entryInformation.setLatestEntryTime(entryTime);
	if (entryTime.isAfter(getLatestEntryTime())){
	    setLatestEntryTime(entryTime);
	    entryInformation.getLatestEqualEntries().clear();
	    entryInformation.getLatestEqualEntries().add(new Integer(entry.hashCode()));
	}
	if (entryTime.isEqual(getLatestEntryTime())){
	    entryInformation.getLatestEqualEntries().add(new Integer(entry.hashCode()));
	}
    }

    /*
     * (non-Javadoc)
     *
     * @see main.uk.ac.cf.model.EntryHandler#endTransaction()
     */
    @Override
    public void endTransaction() {
	log.debug("Saving entries to persitant storage...");
	dataConnection.saveAll(entries);
	dataConnection.save(entryInformation);
	log.debug("Saving entries to persitant storage...done");


    }

    /*
     * (non-Javadoc)
     *
     * @see main.uk.ac.cf.model.EntryHandler#getEntries()
     */
    @Override
    public Set getEntries() {
	return entries;
    }


    /*
     * (non-Javadoc)
     *
     * @see main.uk.ac.cf.model.EntryHandler#isAfter(uk.ac.cardiff.model.Entry)
     */
    @Override
    public boolean isAfter(Entry authE) {
	 if (entryInformation.getLatestEntryTime()==null) return true;
	    return authE.getEventTime().isAfter(entryInformation.getLatestEntryTime());
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * main.uk.ac.cf.model.EntryHandler#isEqualTime(uk.ac.cardiff.model.Entry)
     */
    @Override
    public boolean isEqual(Entry authE) {
	if (entryInformation.getLatestEntryTime()==null) return false;
	    return authE.getEventTime().isEqual(entryInformation.getLatestEntryTime());
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * main.uk.ac.cf.model.EntryHandler#isNewerOrEqual(uk.ac.cardiff.model.Entry
     * )
     */
    @Override
    public boolean isNewerOrEqual(Entry authE) {
	if (entryInformation.getLatestEntryTime()==null) return true;
	if (!authE.getEventTime().isBefore(entryInformation.getLatestEntryTime())) return true;
	return false;
    }

    /*
     * (non-Javadoc)
     *
     * @see main.uk.ac.cf.model.EntryHandler#removeAllEntries()
     */
    @Override
    public void removeAllEntries() {

	dataConnection.deleteAllEntries(entries);
	entries.clear();
	//entryInformation.setLatestEntryTime(null);
    }


//    /**
//     * <p> Sets the latest time entry on the class, also saves it to the data store
//     * to achieve persistence </p>
//     *
//     * @param latestEntryTime
//     */
//    public void setLatestEntryTime(DateTime latestEntryTime) {
//	entryInformation.setLatestEntryTime(latestEntryTime);
//	//dataConnection.save(entryInformation);
//    }

    public void setDataConnection(ICADataConnection dataConnection) {
	this.dataConnection = dataConnection;
    }

    public ICADataConnection getDataConnection() {
	return dataConnection;
    }

    public void setEntryInformation(EntryMetadata entryInformation) {
	this.entryInformation = entryInformation;
    }

    public EntryMetadata getEntryInformation() {
	return entryInformation;
    }

    /* (non-Javadoc)
     * @see main.uk.ac.cf.model.EntryHandler#getLatestEntryTime()
     */
    @Override
    public DateTime getLatestEntryTime() {
	return entryInformation.getLatestEntryTime();
    }

    /* (non-Javadoc)
     * @see main.uk.ac.cf.model.EntryHandler#setLatestEntryTime(org.joda.time.DateTime)
     */
    @Override
    public void setLatestEntryTime(DateTime latestEntryTime) {
	entryInformation.setLatestEntryTime(latestEntryTime);

    }





}
