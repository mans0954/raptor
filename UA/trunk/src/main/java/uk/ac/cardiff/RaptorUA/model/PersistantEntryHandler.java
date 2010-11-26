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
package uk.ac.cardiff.RaptorUA.model;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.springframework.jdbc.core.JdbcTemplate;

import uk.ac.cardiff.RaptorUA.dao.UADataConnection;
import uk.ac.cardiff.model.Entry;

/**
 * @author philsmart
 *
 */
public class PersistantEntryHandler implements EntryHandler {

    /* class logger */
    static Logger log = Logger.getLogger(PersistantEntryHandler.class);


    /* data connection used to persist entries */
    private UADataConnection dataConnection;

    /* set of all entries stored by this EntryHandler */
    Set<Entry> entries;



    public PersistantEntryHandler(UADataConnection dataConnection) {
	this.setDataConnection(dataConnection);

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
	log.debug("Current: " + this.getEntries().size() + " in: " + entries.size());

	for (Entry entry : entries) {
		this.getEntries().add(entry);
	}
	log.debug("Total No. of Entries " + this.getEntries().size());

    }

    public void addEntry(Entry entry){
	    entries.add(entry);

    }

    /*
     * (non-Javadoc)
     *
     * @see main.uk.ac.cf.model.EntryHandler#endTransaction()
     */
    @Override
    public void endTransaction() {
	dataConnection.saveAll(entries);

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
     * @see main.uk.ac.cf.model.EntryHandler#removeAllEntries()
     */
    @Override
    public void removeAllEntries() {

	dataConnection.deleteAllEntries(entries);
	entries.clear();
	//entryInformation.setLatestEntryTime(null);
    }


    public void setDataConnection(UADataConnection dataConnection) {
	this.dataConnection = dataConnection;
    }

    public UADataConnection getDataConnection() {
	return dataConnection;
    }

    /* (non-Javadoc)
     * @see uk.ac.cardiff.RaptorUA.model.EntryHandler#setEntries(java.util.Set)
     */
    @Override
    public void setEntries(Set<Entry> entries) {
	// TODO Auto-generated method stub

    }





}
