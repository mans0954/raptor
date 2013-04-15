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

package uk.ac.cardiff.raptorica.engine;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import uk.ac.cardiff.model.ServiceMetadata;
import uk.ac.cardiff.model.event.Event;
import uk.ac.cardiff.raptor.parse.BaseEventParser;
import uk.ac.cardiff.raptor.parse.DataAccessRegister;
import uk.ac.cardiff.raptor.remoting.client.EventReleaseClient;
import uk.ac.cardiff.raptor.remoting.client.ReleaseFailureException;

/**
 * The Class ICAEngine.
 * 
 * @author philsmart
 * 
 *         Responsible for ALL low level capture operations
 */
public class ICAEngine {

    /** Class Logger. */
    private final Logger log = LoggerFactory.getLogger(ICAEngine.class);

    /** The register that holds parsing agents and their configuration. */
    private DataAccessRegister dataAccessRegister;

    /** Client for sending events to a Raptor server component. For example, an MUA */
    private EventReleaseClient eventReleaseClient;

    /** Meatadata about this client. */
    private ServiceMetadata icaMetadata;

    /**
     * Instantiates a new iCA engine.
     */
    public ICAEngine() {

    }

    public void initialise() {
        log.info("ICA Capture Engine [{}] is running for {}", icaMetadata.getEntityId(), icaMetadata.getServiceName());
    }

    /**
     * Capture perform.
     * 
     * @throws Exception the exception
     */
    public void capturePerform() throws Exception {
        for (BaseEventParser parser : getDataAccessRegister().getEnabledParsingModules()) {
            log.info("Capturing from {}", parser);
            parser.parse();
        }
    }

    /**
     * Garbage collect.
     */
    public void garbageCollect() {
        dataAccessRegister.garbageCollect(eventReleaseClient.getEndpoints());

    }

    /**
     * Converts all information from all modules into a single list that is sent to the release engine.
     * 
     * @return true, if successful
     */
    public boolean release() {
        log.info("Event release on ICA called for {} parsers", getDataAccessRegister().getEnabledParsingModules()
                .size());
        List<Event> eventsToSend = new ArrayList<Event>();

        for (BaseEventParser parser : getDataAccessRegister().getEnabledParsingModules()) {
            eventsToSend.addAll(parser.getAuthentications());
        }

        log.trace("ICA Parser(s) have {} events to send", eventsToSend.size());
        boolean success = false;
        try {
            success = eventReleaseClient.release(eventsToSend, getIcaMetadata());
        } catch (ReleaseFailureException e) {
            log.error("Release failed ", e);
        }
        return success;
    }

    /**
     * Sets the ica metadata.
     * 
     * @param icaMetadata the new ica metadata
     */
    public void setIcaMetadata(ServiceMetadata icaMetadata) {
        this.icaMetadata = icaMetadata;
    }

    /**
     * Gets the ica metadata.
     * 
     * @return the ica metadata
     */
    public ServiceMetadata getIcaMetadata() {
        return icaMetadata;
    }

    /**
     * Sets the event release client.
     * 
     * @param eventReleaseClient the new event release client
     */
    public void setEventReleaseClient(EventReleaseClient eventReleaseClient) {
        this.eventReleaseClient = eventReleaseClient;
    }

    /**
     * Gets the event release client.
     * 
     * @return the event release client
     */
    public EventReleaseClient getEventReleaseClient() {
        return eventReleaseClient;
    }

    /**
     * Sets the data access register.
     * 
     * @param dataAccessRegister the new data access register
     */
    public void setDataAccessRegister(DataAccessRegister dataAccessRegister) {
        this.dataAccessRegister = dataAccessRegister;
    }

    /**
     * Gets the data access register.
     * 
     * @return the data access register
     */
    public DataAccessRegister getDataAccessRegister() {
        return dataAccessRegister;
    }

}
