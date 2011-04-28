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
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import uk.ac.cardiff.model.event.Event;
import uk.ac.cardiff.raptor.event.expansion.AttributeAssociationEngine;


public class AsynchronousEntryStoragePipeline {

	/** class logger */
	private final Logger log = LoggerFactory.getLogger(AsynchronousEntryStoragePipeline.class);

	/** An ID used to track the progress of any transaction */
	private int transactionId;

	/** entry handler used to store entries (e.g. events)*/
    private EntryHandler entryHandler;

    /** Attribute association engine, used to attach additional attributes to events */
    private AttributeAssociationEngine attributeAssociationEngine;

	public AsynchronousEntryStoragePipeline(int transactionId, EntryHandler entryHandler, AttributeAssociationEngine attributeAssociationEngine){
	    this.entryHandler = entryHandler;
	    this.attributeAssociationEngine = attributeAssociationEngine;
	    this.transactionId=transactionId;
	}

	/**
         * Starts and shuts down the <code>storeEntryTask</code> immediately, so that when it completes
         * it can be re-used.
         *
	 * @param events
	 */
	public void execute(List<Event> events, StoreEntriesTaskCallbackInterface callback){
	    StoreEntriesPipelineTask storeEntryTask = new StoreEntriesPipelineTask(entryHandler, attributeAssociationEngine, events,callback);
            ExecutorService es = Executors.newSingleThreadExecutor();
            es.submit(storeEntryTask);
            es.shutdown();
	}


    /**
     * @param transactionId the transactionId to set
     */
    public void setTransactionId(int transactionId) {
        this.transactionId = transactionId;
    }

    /**
     * @return the transactionId
     */
    public int getTransactionId() {
        return transactionId;
    }

}
