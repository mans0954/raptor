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
package uk.ac.cardiff.raptormua.model;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import uk.ac.cardiff.model.event.Event;
import uk.ac.cardiff.model.event.ShibbolethIdpAuthenticationEvent;
import uk.ac.cardiff.raptormua.dao.MUADataConnection;
import uk.ac.cardiff.raptormua.runtimeutils.ReflectionHelper;

/**
 * @author philsmart
 *
 */
public class PersistantEntryHandler implements EntryHandler {
	// TODO can use the spring initialisation callback methods to initials the
	// bean after all properties set etc, rather than initialising on the set
	// method of the engine class

	/* class logger */
	private final Logger log = LoggerFactory.getLogger(PersistantEntryHandler.class);

	/* data connection used to persist entries */
	private MUADataConnection dataConnection;

	/*
	 * set of all entries stored by this EntryHandler should never be used as
	 * memory overhead is too high for large databases
	 */
	Set<Event> entries;

	public PersistantEntryHandler(MUADataConnection dataConnection) {
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

	/*
	 * (non-Javadoc)
	 *
	 * @see uk.ac.cardiff.raptormua.model.EntryHandler#query(java.lang.String)
	 */
	@Override
	public List query(String query) {
		// log.debug("SQL query to entry handler [{}]",query);
		return dataConnection.runQuery(query, null);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * uk.ac.cardiff.raptormua.model.EntryHandler#queryUnique(java.lang.String)
	 */
	@Override
	public Object queryUnique(String query) {
		// log.debug("SQL query to entry handler [{}]",query);
		return dataConnection.runQueryUnique(query, null);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * uk.ac.cardiff.raptormua.model.EntryHandler#queryUnique(java.lang.String)
	 */
	@Override
	public Object queryUnique(String query, Object[] parameters) {
		return dataConnection.runQueryUnique(query, parameters);
	}

	/*
	 * Checks for duplicates by hashCode as each entry is added one by one.
	 *
	 * @see main.uk.ac.cf.model.EntryHandler#addEntries(java.util.List)
	 */
	@Override
	public void addEntries(List<Event> entries) {
		log.info("Persistent Entry Handler has {} entries, with {} new entries inputted", this.getNumberOfEntries(),
				entries.size());
		int duplicates = 0;
		for (Event entry : entries) {
			int hashcode = 0;
			try {
				hashcode = ((Integer) ReflectionHelper.getValueFromObject("hashCode", entry)).intValue();
			} catch (Exception e) {
			}
			int numberOfDuplicates = ((Integer) dataConnection.runQueryUnique("select count(*) from "
					+ entry.getClass().getSimpleName() + " where eventTime = '" + entry.getEventTime()
					+ "' and hashCode ='" + hashcode + "'", null)).intValue();
			if (numberOfDuplicates == 0)
				dataConnection.save(entry);
			else
				duplicates++;
		}

		log.info("Total No. of Entries after addition = {}, finding {} duplicates", this.getNumberOfEntries(),
				duplicates);
	}

	public void addEntry(Event entry) {
		entries.add(entry);

	}

	/*
	 * (non-Javadoc)
	 *
	 * @see main.uk.ac.cf.model.EntryHandler#endTransaction()
	 */
	@Override
	public void endTransaction() {
		log.debug("Saving transaction for MUA");
		dataConnection.saveAll(entries);
		log.debug("Saving transaction for MUA...Done");
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
		log.debug("Removing all entries from this entry handler");
		dataConnection.deleteAllEntries(entries);
		// entries should never be used, but clear in case
		entries.clear();
	}

	public void setDataConnection(MUADataConnection dataConnection) {
		this.dataConnection = dataConnection;
	}

	public MUADataConnection getDataConnection() {
		return dataConnection;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see uk.ac.cardiff.RaptorUA.model.EntryHandler#setEntries(java.util.Set)
	 */
	@Override
	public void setEntries(Set<Event> entries) {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 *
	 * @see uk.ac.cardiff.raptormua.model.EntryHandler#getNumberOfEntries()
	 */
	@Override
	public int getNumberOfEntries() {
		return (Integer) dataConnection.runQueryUnique("select count(*) from Event", null);
	}

}
