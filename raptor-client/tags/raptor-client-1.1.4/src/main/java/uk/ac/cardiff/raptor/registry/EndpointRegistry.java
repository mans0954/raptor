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

package uk.ac.cardiff.raptor.registry;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import uk.ac.cardiff.raptor.store.dao.RaptorDataConnection;

/**
 * handles the list of attached endpoints as injected by Spring.
 */
public class EndpointRegistry {

    /** Class logger. */
    private final Logger log = LoggerFactory.getLogger(EndpointRegistry.class);

    /** List of endpoints for invoking methods on. */
    private List<Endpoint> endpoints;

    /** Whether release information should be persisted. */
    private boolean persistReleaseInformation;

    /** The data connection class to use for persisting release information. */
    private RaptorDataConnection dataConnection;

    /**
     * Instantiates a new endpoint registry.
     */
    public EndpointRegistry() {
        setEndpoints(new ArrayList<Endpoint>());
    }

    /**
     * Sets the endpoints and loads {@link ReleaseInformation} for those endpoints where
     * <code>persistReleaseInformation</code> is true.
     * 
     * @param endpoints the new endpoints
     */
    public void setEndpoints(final List<Endpoint> endpoints) {
        for (Endpoint endpoint : endpoints) {
            log.info("Registering Service Endpoint: {}, persisting event release information {}",
                    endpoint.getServiceEndpoint(), isPersistReleaseInformation());
            if (isPersistReleaseInformation()) {
                ReleaseInformation releaseInformation = loadReleaseInformation(endpoint);
                if (releaseInformation != null) {
                    endpoint.setReleaseInformation(releaseInformation);
                }
            }

        }
        this.endpoints = endpoints;
    }

    /**
     * Gets the endpoints.
     * 
     * @return the endpoints
     */
    public List<Endpoint> getEndpoints() {
        return endpoints;
    }

    /**
     * Persists release information through the configured data connection if enabled.
     */
    public void storeReleaseInformationIfEnabled() {
        if (!isPersistReleaseInformation()) {
            return;
        }
        for (Endpoint entry : endpoints) {
            log.debug(
                    "Saving release information for the endpoint [{}], has persistentId {} and {} latestEqualEntrie(s)",
                    new Object[] {entry.getServiceEndpoint(), entry.getReleaseInformation().getPersistantId(),
                            entry.getReleaseInformation().getLatestEqualEntries().size()});
            dataConnection.save(entry.getReleaseInformation());
        }

    }

    /**
     * Loads release information for this endpoint from the configured data connection.
     * 
     * @param endpoint the endpoint to load release information for
     * @return the release information
     */
    public ReleaseInformation loadReleaseInformation(final Endpoint endpoint) {
        List<?> releaseInfoResults =
                dataConnection.runQuery("from ReleaseInformation where serviceEndpoint=?",
                        new Object[] {endpoint.getServiceEndpoint()});
        if (releaseInfoResults == null) {
            log.warn("Loading error...no release information found, new release information used");
        }
        if (releaseInfoResults.size() > 1) {
            log.error("Loading error...ambiguity in the persisted release information, too many results");
        }
        if (releaseInfoResults.size() == 1) {
            ReleaseInformation releaseInformaiton = (ReleaseInformation) releaseInfoResults.get(0);
            log.info(
                    "Loaded ReleaseInformation for {}, with latest event released time of {}, and {} latest equal entrie(s)",
                    new Object[] {releaseInformaiton.getServiceEndpoint(),
                            releaseInformaiton.getLastReleasedEventTime(),
                            releaseInformaiton.getLatestEqualEntries().size()});
            if (releaseInformaiton.getLatestEqualEntries().size() > 0) {
                log.debug("Latest Equal Entry Is {}", releaseInformaiton.getLatestEqualEntries().iterator().next());
            }
            return releaseInformaiton;
        }
        return null;

    }

    /**
     * Sets the persist release information.
     * 
     * @param persistReleaseInformation the persistReleaseInformation to set
     */
    public void setPersistReleaseInformation(boolean persistReleaseInformation) {
        this.persistReleaseInformation = persistReleaseInformation;
    }

    /**
     * Checks if is persist release information.
     * 
     * @return the persistReleaseInformation
     */
    public boolean isPersistReleaseInformation() {
        return persistReleaseInformation;
    }

    /**
     * Sets the data connection.
     * 
     * @param dataConnection the dataConnection to set
     */
    public void setDataConnection(RaptorDataConnection dataConnection) {
        this.dataConnection = dataConnection;
    }

    /**
     * Gets the data connection.
     * 
     * @return the dataConnection
     */
    public RaptorDataConnection getDataConnection() {
        return dataConnection;
    }

}
