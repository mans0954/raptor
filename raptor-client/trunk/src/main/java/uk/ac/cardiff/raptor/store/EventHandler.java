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

package uk.ac.cardiff.raptor.store;

import java.util.Collection;
import java.util.List;
import java.util.Set;

import org.joda.time.DateTime;

import uk.ac.cardiff.model.event.Event;
import uk.ac.cardiff.raptor.store.dao.StorageException;

public interface EventHandler {

    /**
     * Adds a list of Events to this event handler.
     * 
     * @param entries a <code>List</code> of events to add to this event handler.
     */
    public void addEvents(List<Event> events) throws StorageException;

    /**
     * Adds the <code>event</code> to this event handler.
     * 
     * @param event the single event to add
     */
    public boolean addEvent(Event event);

    /**
     * Returns the date and time of the latest event (chronologically) this event handler has stored.
     * 
     * @return the date and time of the latest event this event handler has stored.
     */
    public DateTime getLatestEventTime();

    /**
     * Returns a list of ALL events this event handler as stored.
     * 
     * @return the <code>List</code> of events stored by this event handler
     */
    public List<Event> getEvents();

    /**
     * Removes all events stored by the event handler.
     */
    public void removeAllEvents();

    /**
     * Initialises this event handler.
     */
    public void initialise();

    /**
     * Saves the Event <code>event</code>.
     * 
     * @param event the Collection of Events to store in batch
     * @throws StorageException
     */
    public void save(Event event) throws StorageException;

    /**
     * Saves (in batch) every object in the collection (Which must themselves be a subclass of {@link Event}.
     * 
     * @param object the Collection of Events to store in batch
     * @throws StorageException
     */
    public void saveAll(Collection<? extends Event> object) throws StorageException;

    /**
     * Returns the number of events this event handler has stored.
     * 
     * @return the number of events this event handler has stored
     */
    public long getNumberOfEvents();

    /**
     * Removes events from this event handler that are chronologically before the <code>earliestReleaseTime</code>. Also
     * removes events from this event hanlder that are equal to <code>earliestReleaseTime</code> and are contained in
     * the set <code>latestEqualEntries</code>
     * 
     * @param earliestReleaseTime the date and time used to prune the set of events this event handler holds.
     * @param latestEqualEvents a set of events that have equal dates and times to the <code>earliestReleaseTime</code>
     */
    public void removeEventsBefore(DateTime earliestReleaseTime, Set<Integer> latestEqualEvents);

}
