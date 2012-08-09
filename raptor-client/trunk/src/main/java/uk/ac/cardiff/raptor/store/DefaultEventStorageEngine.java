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

package uk.ac.cardiff.raptor.store;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import uk.ac.cardiff.model.event.Event;
import uk.ac.cardiff.model.wsmodel.Suggestion;
import uk.ac.cardiff.raptor.event.expansion.AttributeAssociationEngine;
import uk.ac.cardiff.raptor.event.expansion.connector.AttributeAssociationException;
import uk.ac.cardiff.raptor.store.dao.StorageException;

/**
 * @author philsmart
 * 
 */
public class DefaultEventStorageEngine implements EventStorageEngine, StoreEntriesTaskCallbackInterface {

    /** Class logger */
    private final Logger log = LoggerFactory.getLogger(DefaultEventStorageEngine.class);

    /** Responsible for storing all entries (e.g. events) */
    private QueryableEventHandler eventHandler;

    /** Engine used to associate attributes to existing events in the MUA */
    private AttributeAssociationEngine attributeAssociationEngine;

    /** The ID of the currently executing transaction */
    private int currentTransactionId;

    /** Whether a transaction is currently in progress */
    private boolean transactionInProgress;

    /** Default Constructor */
    public DefaultEventStorageEngine() {

    }

    public void storageResultCallback(Object result) {
        log.debug("Storage task completed {}, for transaction id [{}]", result, currentTransactionId);
        transactionInProgress = false;

    }

    /** {@inheritDoc} */
    public void performAsynchronousEntryStoragePipeline(int transactionId, List<Event> events)
            throws TransactionInProgressException {
        if (transactionInProgress) {
            throw new TransactionInProgressException("Transaction " + currentTransactionId + " currently in processing");
        }
        log.info("Committing {} entries to the storage engine, with transaction id [{}]", events.size(), transactionId);
        currentTransactionId = transactionId;
        transactionInProgress = true;
        AsynchronousEntryStoragePipeline asyncEntryStorage =
                new AsynchronousEntryStoragePipeline(transactionId, eventHandler, attributeAssociationEngine);
        asyncEntryStorage.execute(events, this);

    }

    /** {@inheritDoc} */
    public void performSynchronousEntryStoragePipeline(int transactionId, List<Event> events)
            throws TransactionInProgressException {
        if (transactionInProgress) {
            throw new TransactionInProgressException("Transaction " + currentTransactionId + " currently in processing");
        }
        log.info("Committing {} entries to the storage engine, with transaction id [{}]", events.size(), transactionId);
        currentTransactionId = transactionId;
        transactionInProgress = true;
        try {
            attributeAssociationEngine.associateAttributes(events);
            eventHandler.addEvents(events);
            log.debug("Storage task completed true, for transaction id [{}]", currentTransactionId);
        } catch (StorageException e) {
            log.error("Could not store events for transaction id [{}], {}", transactionId);
        } catch (AttributeAssociationException e) {
            log.error("Could not store events for transaction id [{}], {}", transactionId);
        }
        transactionInProgress = false;
    }

    /** {@inheritDoc} */
    public List<Event> getEventsOnOrAfter(Class<? extends Event> eventType, DateTime earliestReleaseTime) {
        @SuppressWarnings("unchecked") List<Event> query =
                (List<Event>) eventHandler.query("from " + eventType.getCanonicalName() + " where eventTime >= ?",
                        new Object[] {earliestReleaseTime});
        return query;
    }

    /** {@inheritDoc} */
    public List<Event> getEventsOnOrAfter(Class<? extends Event> eventType, DateTime earliestReleaseTime,
            int maxNoResults) {
        @SuppressWarnings("unchecked") List<Event> query =
                (List<Event>) eventHandler.query("from " + eventType.getCanonicalName()
                        + " where eventTime >= ? order by eventTime asc", new Object[] {earliestReleaseTime},
                        maxNoResults);
        return query;
    }

    public void removeAllEntries() {
        eventHandler.removeAllEvents();
    }

    /**
     * Sets the configured event handler. Must also then initialise that entry handler
     * 
     * @param entryHandler the entryHandler to set
     */
    public void setEventHandler(QueryableEventHandler entryHandler) {
        this.eventHandler = entryHandler;
        entryHandler.initialise();
    }

    /**
     * @return the entryHandler
     */
    public QueryableEventHandler getEventHandler() {
        return eventHandler;
    }

    /**
     * @param attributeAssociationEngine the attributeAssociationEngine to set
     */
    public void setAttributeAssociationEngine(AttributeAssociationEngine attributeAssociationEngine) {
        this.attributeAssociationEngine = attributeAssociationEngine;
    }

    /**
     * @return the attributeAssociationEngine
     */
    public AttributeAssociationEngine getAttributeAssociationEngine() {
        return attributeAssociationEngine;
    }

    /**
     * {@inheritDoc}
     */
    public List<Suggestion> getPossibleValuesFor(List<String> possibleFieldNameValuesList) {
        ArrayList<Suggestion> suggestions = new ArrayList<Suggestion>();

        for (String fieldName : possibleFieldNameValuesList) {
            try {
                String query = "select " + fieldName + " from Event group by (" + fieldName + ")";
                List results = eventHandler.query(query, null);
                log.trace("Looking for possible values for field {} using query [{}]", fieldName, query);
                int noResults = 0;
                for (Object result : results) {
                    if (result instanceof String) {
                        Suggestion suggestion = new Suggestion();
                        suggestion.setBase(fieldName);
                        suggestion.setValue((String) result);
                        suggestions.add(suggestion);
                        noResults++;
                    }
                }
                log.debug("Field {} has {} suggestion values", fieldName, noResults);
            } catch (RuntimeException e) {
                log.warn(
                        "Caught a runtime exception. Error trying to find possible values for {}, probably nothing to worry about",
                        fieldName);
            }

        }

        return suggestions;
    }

}
