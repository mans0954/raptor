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
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import uk.ac.cardiff.model.event.Event;
import uk.ac.cardiff.model.event.ShibbolethIdpAuthenticationEvent;
import uk.ac.cardiff.raptor.runtimeutils.ReflectionHelper;
import uk.ac.cardiff.raptor.store.EntryHandler;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LogFileMemoryEntryHandler implements EntryHandler {

	/** Class logger */
	private final Logger log = LoggerFactory.getLogger(MemoryEntryHandler.class);

	/** Pointer to the last recorded entry, for incremental update. */
	private DateTime latestEntryTime;

	/** Set of all entries stored by this EntryHandler. */
	private Set<Event> entries;

	/**
	 * Stores the set of latest unique entries. That is, those with the latest
	 * and same DateTime, but different state (attribute values). This set is
	 * checked when adding new entries, and is not removed when records are
	 * removed.
	 */
	private Set<Event> latestEqualEntries;

	public LogFileMemoryEntryHandler() {
		entries = new LinkedHashSet<Event>();
		latestEqualEntries = new LinkedHashSet<Event>();
	}

	public void addEntries(List<Event> entries) {
		log.debug("Current: " + this.entries.size() + " in: " + entries.size());

		for (Event event : entries) {
			addEntry(event);

		}
		log.debug("Total No. of Entries " + this.entries.size() + " Latest Entry at: " + getLatestEntryTime());
	}

	/**
	 * First copy the hash of this entry into the <code>eventId</code> column if
	 * its empty. Then, add to the entry set iff its a newer entry. If equal,
	 * first check the <code>latestEqualEntries</code> set, if contained in this
	 * set, then it has already been added, if not add.
	 * 
	 * @return true if this event was added to the entry handler, false
	 *         otherwise
	 */
	public boolean addEntry(Event event) {
		addEventIdIfNull(event);
		boolean isAfter = isAfter(event);
		boolean isEqual = isEqual(event);
		if (isAfter) {
			entries.add(event);
			updateLastEntry(event);
			return true;
		} else if (isEqual) {
			boolean isAlreadyInLatest = latestEqualEntries.contains(event);
			if (isAlreadyInLatest) {
				log.warn("Duplicated [{}]", event);
				return false;
			}
			if (!isAlreadyInLatest) {
				entries.add(event);
				updateLastEntry(event);
				return true;
			}
		}
		return false;

	}

	/**
	 * Stores the hashcode of the event as the <code>eventId</code> iff there is
	 * no existing eventId (defined as 0), and the event has a valid hashcode.
	 * 
	 * @param event
	 */
	private void addEventIdIfNull(Event event) {
		if (event.getEventId() != 0) {
			return;
		}
		int hashCode = ReflectionHelper.getHashCodeFromEventOrNull(event);
		if (hashCode != 0) {
			event.setEventId(hashCode);
		}
	}

	public void removeEventsBefore(DateTime earliestReleaseTime, Set<Integer> latestEqualEntries) {
		log.debug("Removing events earlier than {}, or in the set of last equal events sent (from {} events)", earliestReleaseTime, latestEqualEntries.size());
		ArrayList<Event> toRemove = new ArrayList<Event>();
		for (Event event : entries) {
			if (event.getEventTime().isBefore(earliestReleaseTime))
				toRemove.add(event);
			if (event.getEventTime().isEqual(earliestReleaseTime)) {
				if (latestEqualEntries.contains(event.getEventId())) {
					toRemove.add(event);
				}
			}
		}
		entries.removeAll(toRemove);

	}

	private void updateLastEntry(Event entry) {
		DateTime entryTime = entry.getEventTime();
		if (getLatestEntryTime() == null)
			setLatestEntryTime(entryTime);
		if (entryTime.isAfter(getLatestEntryTime())) {
			setLatestEntryTime(entryTime);
			latestEqualEntries.clear();
			latestEqualEntries.add(entry);
		}
		if (entryTime.isEqual(getLatestEntryTime())) {
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
	public boolean isNewerOrEqual(Event event) {
		if (latestEntryTime == null)
			return true;
		if (!event.getEventTime().isBefore(latestEntryTime))
			return true;
		return false;
	}

	public boolean isEqual(Event event) {
		if (latestEntryTime == null)
			return false;
		return event.getEventTime().isEqual(latestEntryTime);
	}

	public boolean isAfter(Event event) {
		if (latestEntryTime == null)
			return true;
		return event.getEventTime().isAfter(latestEntryTime);
	}

	/**
	 * pushes the latestEntryTime by 1 millisecond (see LogFileParser.java for
	 * explanation)
	 */
	public void endTransaction() {
		latestEntryTime = new DateTime(latestEntryTime.getMillis() + 1);

	}

	/**
	 * @return the list of entries currently stored by the entry handler
	 */
	public List<Event> getEntries() {
		return new ArrayList<Event>(entries);

	}

	/**
	 * Removes all entries, but does not reset last entry, as this is still used
	 * so as not to add previously parsed entries.
	 */
	public void removeAllEntries() {
		entries.clear();
	}

	/**
	 * This is a no-op method for the memory entry handler
	 */
	public void initialise() {

	}

	public int getNumberOfEntries() {
		return entries.size();
	}

	/**
	 * This is a no-op method for all in-memory entry handlers
	 */
	public List query(String query) {
		return null;
	}

	/**
	 * This is a no-op method for all in-memory entry handlers
	 */
	public Object queryUnique(String query) {
		return null;
	}

	/**
	 * This is a no-op method for all in-memory entry handlers
	 */
	public Object queryUnique(String query, Object[] parameters) {
		return null;
	}

	public void setEntries(Set<Event> entries) {
		this.entries = entries;

	}

	/**
	 * This is a no-op method for all in-memory entry handlers
	 */
	public List query(String query, Object[] parameters) {
		return null;
	}

	/**
	 * This is a no-op method for all in-memory entry handlers
	 */
	public List query(String query, Object[] parameters, int maxNoResults) {
		return null;
	}

}
