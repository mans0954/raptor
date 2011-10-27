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
import uk.ac.cardiff.raptor.store.IncrementalEventHandler;
import uk.ac.cardiff.raptor.store.dao.StorageException;

public class LogFileIncrementalMemoryEventHandler implements IncrementalEventHandler {

    /** Class logger */
    private final Logger log = LoggerFactory.getLogger(MemoryEventHandler.class);

    /** Pointer to the last recorded entry, for incremental update. */
    private DateTime latestEventTime;

    /** Set of all entries stored by this EntryHandler. */
    private Set<Event> events;

    /**
     * Stores the set of latest unique entries. That is, those with the latest and same Date and Time, but different
     * state (attribute values). This set is checked when adding new entries, and is not removed when records are
     * removed.
     */
    private Set<Event> latestEqualEntries;

    public LogFileIncrementalMemoryEventHandler() {
        events = new LinkedHashSet<Event>();
        latestEqualEntries = new LinkedHashSet<Event>();
    }

    public void addEvents(List<Event> events) throws StorageException {
        log.debug("Current: " + this.events.size() + " in: " + events.size());
        for (Event event : events) {
            addEvent(event);
        }
        log.debug("Total No. of Entries " + this.events.size() + " Latest Entry at: " + getLatestEventTime());
    }

    /**
     * First copy the hash of this entry into the <code>eventId</code> column if its empty. Then, add to the entry set
     * iff its a newer entry. If equal, first check the <code>latestEqualEntries</code> set, if contained in this set,
     * then it has already been added, if not add.
     * 
     * @return true if this event was added to the entry handler, false otherwise
     */
    public boolean addEvent(Event event) {
        addEventIdIfNull(event);
        boolean isAfter = isAfter(event);
        boolean isEqual = isEqual(event);
        if (isAfter) {
            events.add(event);
            updateLastEvent(event);
            return true;
        } else if (isEqual) {
            boolean isAlreadyInLatest = latestEqualEntries.contains(event);
            if (isAlreadyInLatest) {
                log.trace("Duplicated, probably same event [{}]", event);
                return false;
            }
            if (!isAlreadyInLatest) {
                events.add(event);
                updateLastEvent(event);
                return true;
            }
        }
        return false;

    }

    /**
     * Clears the set of <code>entries</code>, the set of <code>latestEqualEntries</code> and dereferences the
     * <code>latestEntryTime</code>
     */
    public void reset() {
        events.clear();
        latestEqualEntries.clear();
        latestEventTime = null;
    }

    /**
     * Stores the hashcode of the event as the <code>eventId</code> iff there is no existing eventId (defined as 0), and
     * the event has a valid hashcode.
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

    private void updateLastEvent(Event entry) {
        DateTime entryTime = entry.getEventTime();
        if (getLatestEventTime() == null)
            setLatestEventTime(entryTime);
        if (entryTime.isAfter(getLatestEventTime())) {
            setLatestEventTime(entryTime);
            latestEqualEntries.clear();
            latestEqualEntries.add(entry);
        }
        if (entryTime.isEqual(getLatestEventTime())) {
            latestEqualEntries.add(entry);
        }
    }

    /**
     * @param event the event to check if its after or equal to the <code>latestEventTime</code> or not.
     * @return true if the recorded date and time attribute of <code>event</code> is equal to or newer than the date and
     *         time recorded by <code>latestEventTime</code>
     */
    public boolean isAfterOrEqual(Event event) {
        if (latestEventTime == null)
            return true;
        if (!event.getEventTime().isBefore(latestEventTime))
            return true;
        return false;
    }

    /**
     * @param event the event to check if its equal to the <code>latestEventTime</code> or not.
     * @return true if the recorded date and time attribute of <code>event</code> is equal to or newer than the date and
     *         time recorded by <code>latestEventTime</code>
     */
    public boolean isEqual(Event event) {
        if (latestEventTime == null)
            return false;
        return event.getEventTime().isEqual(latestEventTime);
    }

    /**
     * @param event the event to check if its after to the <code>latestEventTime</code> or not.
     * @return true if the recorded date and time attribute of <code>event</code> is after the date and time recorded by
     *         <code>latestEventTime</code>
     */
    public boolean isAfter(Event event) {
        if (latestEventTime == null)
            return true;
        return event.getEventTime().isAfter(latestEventTime);
    }

    /**
     * pushes the latestEntryTime by 1 millisecond (see LogFileParser.java for explanation)
     */
    public void endTransaction() {
        latestEventTime = new DateTime(latestEventTime.getMillis() + 1);

    }

    /**
     * @return the list of entries currently stored by the entry handler
     */
    public List<Event> getEvents() {
        return new ArrayList<Event>(events);

    }

    /**
     * Removes all events, but does not reset last entry, as this is still used so as not to add previously parsed
     * entries.
     */
    public void removeAllEvents() {
        events.clear();
    }

    /**
     * This is a no-op method for the memory entry handler
     */
    public void initialise() {

    }

    public long getNumberOfEvents() {
        return events.size();
    }

    public DateTime getLatestEventTime() {
        return latestEventTime;
    }

    /**
     * @param latestEventTime the latestEventTime to set
     */
    public void setLatestEventTime(DateTime latestEventTime) {
        this.latestEventTime = latestEventTime;
    }

    public void save(Event event) throws StorageException {
        // no-op
    }

    public void saveAll(Collection<? extends Event> object) throws StorageException {
        // no-op
    }

}
