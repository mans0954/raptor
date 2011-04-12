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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import uk.ac.cardiff.model.event.Event;

/**
 * @author philsmart
 *
 */
public class StorageEngine {

    /** Class logger*/
    private final Logger log = LoggerFactory.getLogger(StorageEngine.class);

    /** Responsible for storing all entries (e.g. events) */
    private EntryHandler entryHandler;


    /** Default Constructor*/
    public StorageEngine(){

    }

    /**
     * @param events
     */
    public void addEntriesAsynchronous(int transactionId, List<Event> events){
        AsynchronousEntryStorage asyncEntryStorage = new AsynchronousEntryStorage(transactionId);
        asyncEntryStorage.store(entryHandler, events);
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




}
