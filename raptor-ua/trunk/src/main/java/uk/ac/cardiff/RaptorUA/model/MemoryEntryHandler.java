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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import uk.ac.cardiff.model.Event;
import uk.ac.cardiff.model.ShibbolethEntry;

/**
 * @author philsmart
 *
 */
public class MemoryEntryHandler implements EntryHandler {
    static Logger log = LoggerFactory.getLogger(EntryHandler.class);

    /* set of all entries stored by this EntryHanlder */
    Set<Event> entries;

    public MemoryEntryHandler() {
	entries = new LinkedHashSet<Event>();
    }

    public void addEntries(Set<Event> entries) {
	log.info("Adding " + entries.size() + " entries to the UA");
	for (Event entry : entries) {
	    this.entries.add(entry);
	}
	log.info("UA now has a total of " + this.entries.size() + " entries");
    }

    /**
     * @return the list of entries currently stored by the entry handler
     */
    public Set getEntries() {
	return entries;

    }

    public void setEntries(Set<Event> entries) {
	this.entries = entries;
    }

    /**
	 *
	 */
    public void removeAllEntries() {
	/*
	 * remove all entries, but do not reset last entry, as this is still used not to add previously parsed entries.
	 */
	entries.clear();

    }

    /*
     * <p> nothing to do in a memory handler after each transaction
     *
     * (non-Javadoc)
     *
     * @see uk.ac.cardiff.RaptorUA.model.EntryHandler#endTransaction()
     */
    @Override
    public void endTransaction() {

    }

    /*
     * (non-Javadoc)
     *
     * @see uk.ac.cardiff.RaptorUA.model.EntryHandler#initialise()
     */
    @Override
    public void initialise() {
	// TODO Auto-generated method stub

    }
}
