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

package uk.ac.cardiff.raptor.store;

import java.util.List;

import org.joda.time.DateTime;

import uk.ac.cardiff.model.event.Event;
import uk.ac.cardiff.model.wsmodel.Suggestion;

public interface EventStorageEngine {

    /**
     * Stores the <code>events</code> asynchronously through the configured
     * {@link uk.ac.cardiff.raptor.store.EventHandler}
     * 
     * @param transactionId the numerical Id of this transaction
     * @param events the events to store
     */
    public void performAsynchronousEntryStoragePipeline(int transactionId, List<Event> events)
            throws TransactionInProgressException;

    /**
     * Stores the <code>events</code> synchronously through the configured
     * {@link uk.ac.cardiff.raptor.store.EventHandler}
     * 
     * @param transactionId the numerical Id of this transaction
     * @param events the events to store
     */
    public void performSynchronousEntryStoragePipeline(int transactionId, List<Event> events)
            throws TransactionInProgressException;

    /**
     * Returns events on or after the input time <code>earliestReleaseTime</code>.
     * 
     * @param earliestReleaseTime
     * @return
     */
    public List<Event> getEventsOnOrAfter(Class<? extends Event> eventType, DateTime earliestReleaseTime);

    /**
     * Returns events on or after the input time <code>earliestReleaseTime</code>, but with a maximum of
     * <code>maxNoResults</code> results (in chronological order).
     * 
     * @param earliestReleaseTime
     * @param maxNoResults
     * @return
     */
    public List<Event> getEventsOnOrAfter(Class<? extends Event> eventType, DateTime earliestReleaseTime,
            int maxNoResults);

    /**
     * Returns the possible values that each of the input field names can take
     * 
     * @param possibleFieldNameValuesList
     * @return
     */
    public List<Suggestion> getPossibleValuesFor(List<String> possibleFieldNameValuesList);

    // TODO, should we allow direct access the the event handler in this class.
    public QueryableEventHandler getEventHandler();

}
