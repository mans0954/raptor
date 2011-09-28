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

package uk.ac.cardiff.raptor.remoting.client;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import uk.ac.cardiff.model.ServiceMetadata;
import uk.ac.cardiff.model.event.Event;

/**
 * The Class DefaultEventReleaseClient.
 */
public class DefaultEventReleaseClient extends AbstractEventReleaseClient {

    /** Class logger. */
    private final Logger log = LoggerFactory.getLogger(DefaultEventReleaseClient.class);

    /** The engine that releases events to a client endpoint. */
    private EventReleaseEngine eventReleaseEngine;

    /**
     * Instantiates a new default event release client.
     */
    public DefaultEventReleaseClient() {
        eventReleaseEngine = new EventReleaseEngine();
        enableEventRelease = true;
    }

    /*
     * (non-Javadoc)
     * 
     * @see uk.ac.cardiff.raptor.remoting.client.EventReleaseClient#release(java.util.List,
     * uk.ac.cardiff.model.ServiceMetadata)
     */
    public boolean release(List<Event> events, ServiceMetadata serviceMetadata) throws ReleaseFailureException {
        boolean success = false;
        if (enableEventRelease) {
            log.info("[Event Release Called]");
            success = eventReleaseEngine.release(endpointRegistry, events, serviceMetadata);
            endpointRegistry.storeReleaseInformationIfEnabled();
            log.info("[--Events released to all listening endpoints {}--]", success);
        }

        return success;
    }

    /**
     * Sets the event release engine.
     * 
     * @param eventReleaseEngine the new event release engine
     */
    public void setEventReleaseEngine(EventReleaseEngine eventReleaseEngine) {
        this.eventReleaseEngine = eventReleaseEngine;
    }

    /**
     * Gets the event release engine.
     * 
     * @return the event release engine
     */
    public EventReleaseEngine getEventReleaseEngine() {
        return eventReleaseEngine;
    }

}
