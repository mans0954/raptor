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

import uk.ac.cardiff.model.resource.ResourceMetadata;
import uk.ac.cardiff.raptor.store.dao.RaptorDataConnection;

/**
 * @author philsmart
 * 
 */
public class ResourceStorageEngine implements StoreEntriesTaskCallbackInterface {

    /** Class logger */
    private final Logger log = LoggerFactory.getLogger(ResourceStorageEngine.class);

    /** Responsible for storing all entries (e.g. events) */
    private RaptorDataConnection dataConnection;

    /** The ID of the currently executing transaction */
    private int currentTransactionId;

    /** Whether a transaction is currently in progress */
    private boolean transactionInProgress;

    /** Default Constructor */
    public ResourceStorageEngine() {

    }

    public void storageResultCallback(Object result) {
        log.debug("Storage task completed {}, for transaction id [{}]", result, currentTransactionId);
        transactionInProgress = false;

    }

    /**
     * Stores the <code>events</code> asynchronously through the configured
     * {@link uk.ac.cardiff.raptor.store.EventHandler}
     * 
     * @param transactionId the numerical Id of this transaction
     * @param events the events to store
     */
    public void performAsynchronousResourceStoragePipeline(int transactionId, List<ResourceMetadata> resources)
            throws TransactionInProgressException {
        if (transactionInProgress) {
            throw new TransactionInProgressException("Transaction " + currentTransactionId + " currently in processing");
        }
        log.info("Committing {} resources to the resource storage engine, with transaction id [{}]", resources.size(),
                transactionId);
        currentTransactionId = transactionId;
        transactionInProgress = true;

        ResourceClassificationBackgroundService backgroundService =
                new ResourceClassificationBackgroundService(dataConnection);
        backgroundService.saveResourceMetadataAndApplyAsync(resources);

    }

    /**
     * @param dataConnection the dataConnection to set
     */
    public void setDataConnection(RaptorDataConnection dataConnection) {
        this.dataConnection = dataConnection;
    }

    /**
     * @return the dataConnection
     */
    public RaptorDataConnection getDataConnection() {
        return dataConnection;
    }

}
