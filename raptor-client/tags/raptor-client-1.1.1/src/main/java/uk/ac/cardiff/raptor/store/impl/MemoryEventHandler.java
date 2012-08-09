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
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import uk.ac.cardiff.model.event.Event;
import uk.ac.cardiff.raptor.runtimeutils.ReflectionHelper;
import uk.ac.cardiff.raptor.store.EventHandler;
import uk.ac.cardiff.raptor.store.dao.StorageException;

/**
 * The Class MemoryEventHandler.
 */
public class MemoryEventHandler implements EventHandler {

    /** Class logger. */
    private final Logger log = LoggerFactory.getLogger(MemoryEventHandler.class);

    /** pointer to the last recorded entry, for incremental update. */
    private DateTime latestEntryTime;

    /** set of all entries stored by this EntryHandler. */
    private Set<Event> events;

    /**
     * Instantiates a new memory event handler.
     */
    public MemoryEventHandler() {
        events = new LinkedHashSet<Event>();
    }

    public void addEvents(List<Event> entries) {
        log.debug("Current: " + this.events.size() + " in: " + entries.size());
        int notAdded = 0;
        for (Event event : entries) {
            boolean didAdd = addEvent(event);
            if (didAdd == false)
                notAdded++;

        }
        log.debug("Total No. of Entries {}, Lastest Entry at: {}, with {} duplicates", new Object[] {
                this.events.size(), getLatestEventTime(), notAdded});
    }

    /**
     * Checks whether this event is already stored, if not, then it adds the <code>event</code> to the
     * <code>entries<code> set.
     * 
     * @param event the event to store
     * @return true, if successful
     */
    public boolean addEvent(Event event) {
        addEventIdIfNull(event);
        if (!events.contains(event)) {
            events.add(event);
            updateLastEntry(event);
            return true;
        } else {
            return false;
        }

    }

    /**
     * Stores the hashcode of the event as the <code>eventId</code> iff there is no existing eventId (defined as 0), and
     * the event has a valid hashcode.
     * 
     * @param event the event
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

    /**
     * Update last entry.
     * 
     * @param event the event
     */
    private void updateLastEntry(Event event) {
        DateTime entryTime = event.getEventTime();
        if (getLatestEventTime() == null)
            setLatestEntryTime(entryTime);
        if (entryTime.isAfter(getLatestEventTime())) {
            setLatestEntryTime(entryTime);

        }
    }

    public void removeEventsBefore(DateTime earliestReleaseTime, Set<Integer> latestEqualEntries) {
        log.debug("Removing events earlier than {}, or in the set of last equal events sent (from {} events)",
                earliestReleaseTime, latestEqualEntries.size());
        ArrayList<Event> toRemove = new ArrayList<Event>();
        for (Event event : events) {
            if (event.getEventTime().isBefore(earliestReleaseTime))
                toRemove.add(event);
            if (event.getEventTime().isEqual(earliestReleaseTime)) {
                if (latestEqualEntries.contains(event.getEventId())) {
                    toRemove.add(event);
                }
            }
        }
        events.removeAll(toRemove);

    }

    /**
     * Sets the latest entry time.
     * 
     * @param latestEntryTime the new latest entry time
     */
    public void setLatestEntryTime(DateTime latestEntryTime) {
        this.latestEntryTime = latestEntryTime;
    }

    public DateTime getLatestEventTime() {
        return latestEntryTime;
    }

    /**
     * pushes the latestEntryTime by 1 millisecond (see LogFileParser.java for explanation)
     */
    public void endTransaction() {
        latestEntryTime = new DateTime(latestEntryTime.getMillis() + 1);

    }

    /**
     * Get all the events stored by the event handler.
     * 
     * @return the list of entries currently stored by the entry handler
     */
    public List<Event> getEvents() {
        return new ArrayList<Event>(events);

    }

    /**
     * Removes all entries, but does not reset last entry, as this is still used so as not to add previously parsed
     * entries.
     */
    public void removeAllEvents() {
        events.clear();
    }

    /**
     * This is a no-op method for all in-memory entry handlers.
     */
    public void initialise() {

    }

    public long getNumberOfEvents() {
        return events.size();
    }

    public void save(Event event) throws StorageException {
        // no-op

    }

    public void saveAll(Collection<? extends Event> object) throws StorageException {
        // no-op
    }

}
