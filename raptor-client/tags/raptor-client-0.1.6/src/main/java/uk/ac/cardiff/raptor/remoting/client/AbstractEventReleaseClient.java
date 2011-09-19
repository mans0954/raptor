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

import uk.ac.cardiff.raptor.registry.Endpoint;
import uk.ac.cardiff.raptor.registry.EndpointRegistry;

public abstract class AbstractEventReleaseClient implements EventReleaseClient {

    /** Whether events should be released. Defaults to true. */
    protected boolean enableEventRelease;

    /** Encapsulation of all endpoints this client can communication with. */
    protected EndpointRegistry endpointRegistry;

    public List<Endpoint> getEndpoints() {
        return endpointRegistry.getEndpoints();
    }

    public boolean isEnabled() {
        return enableEventRelease;
    }

    /**
     * Sets the enable event release.
     * 
     * @param enableEventRelease the enableEventRelease to set
     */
    public void setEnableEventRelease(boolean enableEventRelease) {
        this.enableEventRelease = enableEventRelease;
    }

    /**
     * Checks if is enable event release.
     * 
     * @return the enableEventRelease
     */
    public boolean isEnableEventRelease() {
        return enableEventRelease;
    }

    /**
     * Sets the endpoint registry.
     * 
     * @param endpointRegistry the new endpoint registry
     */
    public void setEndpointRegistry(EndpointRegistry endpointRegistry) {
        this.endpointRegistry = endpointRegistry;
    }

    /**
     * Gets the endpoint registry.
     * 
     * @return the endpoint registry
     */
    public EndpointRegistry getEndpointRegistry() {
        return endpointRegistry;
    }

}
