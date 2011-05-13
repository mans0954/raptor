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

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import uk.ac.cardiff.model.event.Event;
import uk.ac.cardiff.model.resource.ResourceMetadata;
import uk.ac.cardiff.raptor.event.expansion.AttributeAssociationEngine;

/**
 * @author philsmart
 *
 */
public class StorageEngine  implements StoreEntriesTaskCallbackInterface{

    /** Class logger*/
    private final Logger log = LoggerFactory.getLogger(StorageEngine.class);

    /** Responsible for storing all entries (e.g. events) */
    private EntryHandler entryHandler;


    /** Engine used to associate attributes to existing events in the MUA */
    private AttributeAssociationEngine attributeAssociationEngine;

    /** The ID of the currently executing transaction */
    private int currentTransactionId;


    /** Whether a transaction is currently in progress*/
    private boolean transactionInProgress;


    /** Default Constructor*/
    public StorageEngine(){

    }

    public void storageResultCallback(Object result) {
        log.debug("Storage task completed {}, for transaction id [{}]",result, currentTransactionId);
        transactionInProgress=false;

    }

    /**
     * @param events
     */
    public void performAsynchronousEntryStoragePipeline(int transactionId, List<Event> events) throws TransactionInProgressException{
        if (transactionInProgress){
            throw new TransactionInProgressException("Transaction "+currentTransactionId+" currently in processing");
        }
        log.info("Committing {} entries to the storage engine, with transaction id [{}]", events.size(),transactionId);
        this.currentTransactionId = transactionId;
        transactionInProgress=true;
        AsynchronousEntryStoragePipeline asyncEntryStorage = new AsynchronousEntryStoragePipeline(transactionId, entryHandler,attributeAssociationEngine);
        asyncEntryStorage.execute(events,this);


    }

    /**
     * Returns events on or after the input time <code>earliestReleaseTime</code>.
     *
     * @param earliestReleaseTime
     * @return
     */
    public List<Event> getEventsOnOrAfter(DateTime earliestReleaseTime) {
        List<Event> query = (List<Event>) entryHandler.query("from Event where eventTime >= ?",new Object[]{earliestReleaseTime.toDate()});
        return query;
    }

    /**
     * Returns events on or after the input time <code>earliestReleaseTime</code>, but with a maximum of
     * <code>maxNoResults</code> results (in chronological order).
     *
     * @param earliestReleaseTime
     * @param maxNoResults
     * @return
     */
    public List<Event> getEventsOnOrAfter(DateTime earliestReleaseTime, int maxNoResults) {
        List<Event> query = (List<Event>) entryHandler.query("from Event where eventTime >= ? order by eventTime asc",new Object[]{earliestReleaseTime.toDate()},maxNoResults);
        return query;
    }


    /**
     *
     */
    public void removeAllEntries() {
        entryHandler.removeAllEntries();
    }



    /**
     * Sets the configured entry handler. Must also then initialise that entry
     * handler
     *
     * @param entryHandler the entryHandler to set
     */
    public void setEntryHandler(EntryHandler entryHandler) {
        this.entryHandler = entryHandler;
        entryHandler.initialise();
    }


    /**
     * @return the entryHandler
     */
    public EntryHandler getEntryHandler() {
        return entryHandler;
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









}
