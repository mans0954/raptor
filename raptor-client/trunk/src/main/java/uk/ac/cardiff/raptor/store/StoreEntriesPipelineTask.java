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
import java.util.concurrent.Callable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import uk.ac.cardiff.model.event.Event;
import uk.ac.cardiff.raptor.event.expansion.AttributeAssociationEngine;
import uk.ac.cardiff.raptor.event.expansion.connector.AttributeAssociationException;
import uk.ac.cardiff.raptor.store.dao.StorageException;



/**
 * @author philsmart
 *
 */
public class StoreEntriesPipelineTask implements Callable<Boolean>{

	/** class logger */
	private final Logger log = LoggerFactory.getLogger(StoreEntriesPipelineTask.class);

	/** The entry handler used to store entries (e.g. events)*/
    private EntryHandler entryHandler;

    /** The callback interface that is called once the <code>call</code> method has completed*/
    private StoreEntriesTaskCallbackInterface storeCallback;

    /** Attribute association engine, used to attach additional attributes to events */
    private AttributeAssociationEngine attributeAssociationEngine;

    /** The events that need to be stored */
    private List<Event> events;

    public StoreEntriesPipelineTask(EntryHandler entryHandler, AttributeAssociationEngine attributeAssociationEngine,List<Event> events, StoreEntriesTaskCallbackInterface storeCallback){
        this.storeCallback = storeCallback;
        this.attributeAssociationEngine = attributeAssociationEngine;
        this.entryHandler = entryHandler;
        this.events = events;
    }

    public Boolean call() throws Exception {
    	try{

    	    associate();
    	    store();

    	}
    	catch(StorageException e){
    	    log.error("Failed to store events asynchronously");
    	    storeCallback.storageResultCallback(new Boolean("false"));
    	    return false;
    	}
        storeCallback.storageResultCallback(new Boolean("true"));
        return true;
    }

    private void store() throws StorageException{
        entryHandler.addEntries(events);
    }

    private void associate(){
        try {
            attributeAssociationEngine.associateAttributes(events);
        } catch (AttributeAssociationException e) {
            log.warn("{}",e.getMessage());
        }
    }

}
