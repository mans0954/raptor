
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
