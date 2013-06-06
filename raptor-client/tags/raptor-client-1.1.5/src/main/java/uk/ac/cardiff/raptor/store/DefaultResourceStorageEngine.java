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
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import uk.ac.cardiff.model.resource.ResourceMetadata;
import uk.ac.cardiff.raptor.store.dao.RaptorDataConnection;

/**
 * @author philsmart
 * 
 */
public class DefaultResourceStorageEngine implements ResourceStorageEngine,
        SaveAndApplyResourceClassificationCallbackInterface<StorageResult> {

    /** Class logger */
    private final Logger log = LoggerFactory.getLogger(DefaultResourceStorageEngine.class);

    /** Responsible for storing all information. */
    private RaptorDataConnection dataConnection;

    /** The single thread executor service to queue and execute resource storage tasks. */
    private final ExecutorService resourceClassificationService;

    /** Default Constructor. Construct a single threaded executor service <code>resourceClassificationService</code> */
    public DefaultResourceStorageEngine() {
        resourceClassificationService = Executors.newSingleThreadExecutor();
    }

    public void callback(StorageResult result) {
        log.debug("Storage task completed {}, for transaction id [{}]", result.isSuccess(), result.getTransactionId());

    }

    /** {@inheritDoc} */
    public void performAsynchronousResourceStoragePipeline(List<ResourceMetadata> resources) {

        int transactionId = (int) (Math.random() * 1000000);

        log.info("Committing {} resources to the resource storage engine, with transaction id [{}]", resources.size(),
                transactionId);

        SaveAndApplyResourceClassificationTask storeEntryTask =
                new SaveAndApplyResourceClassificationTask(transactionId, dataConnection, resources, this);
        resourceClassificationService.submit(storeEntryTask);

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
