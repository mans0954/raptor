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

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;

import uk.ac.cardiff.RaptorUA.dao.UADataConnection;
import uk.ac.cardiff.model.Entry;
import uk.ac.cardiff.model.ShibbolethEntry;

/**
 * @author philsmart
 *
 */
public class PersistantEntryHandler implements EntryHandler {

    /* class logger */
    static Logger log = LoggerFactory.getLogger(PersistantEntryHandler.class);

    /* data connection used to persist entries */
    private UADataConnection dataConnection;

    /* set of all entries stored by this EntryHandler */
    Set<Entry> entries;

    public PersistantEntryHandler(UADataConnection dataConnection) {
	this.setDataConnection(dataConnection);


    }

    /**
     * Initialises the entry handler. In particular, loads all entries from the main datastore, through the <code>dataConnection</code> instance.
     */
    public void initialise() {
	log.info("Persistant entry handler [{}] initialising", this);
	Integer rowCount = (Integer) dataConnection.runQueryUnique("select count(*) from Entry", null);
	log.info("Persistent data store has {} entries", rowCount);
	List<Entry> entriesAsList = dataConnection.runQuery("from Entry", null);
	log.info("UA has loaded " + entriesAsList.size() + " entries from DB backed cache");
	entries = new LinkedHashSet<Entry>(entriesAsList);
	log.info("Persistant entry handler [{}] started", this);
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

    public void addEntry(Entry entry) {
	entries.add(entry);

    }

    /*
     * (non-Javadoc)
     *
     * @see main.uk.ac.cf.model.EntryHandler#endTransaction()
     */
    @Override
    public void endTransaction() {
	log.debug("Saving transaction for UA");
	dataConnection.saveAll(entries);
	log.debug("Saving transaction for UA...Done");

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
	// entryInformation.setLatestEntryTime(null);
    }

    public void setDataConnection(UADataConnection dataConnection) {
	this.dataConnection = dataConnection;
    }

    public UADataConnection getDataConnection() {
	return dataConnection;
    }

    /*
     * (non-Javadoc)
     *
     * @see uk.ac.cardiff.RaptorUA.model.EntryHandler#setEntries(java.util.Set)
     */
    @Override
    public void setEntries(Set<Entry> entries) {
	// TODO Auto-generated method stub

    }

}
