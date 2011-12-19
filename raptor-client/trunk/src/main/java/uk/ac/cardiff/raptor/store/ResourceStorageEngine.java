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

import uk.ac.cardiff.model.resource.ResourceMetadata;

public interface ResourceStorageEngine {

    /**
     * Stores the <code>events</code> asynchronously through the configured
     * {@link uk.ac.cardiff.raptor.store.EventHandler}
     * 
     * @param transactionId the numerical Id of this transaction
     * @param events the events to store
     */
    public void performAsynchronousResourceStoragePipeline(List<ResourceMetadata> resources);

}
