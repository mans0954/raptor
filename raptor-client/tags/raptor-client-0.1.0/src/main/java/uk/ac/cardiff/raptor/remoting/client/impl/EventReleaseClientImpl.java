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
package uk.ac.cardiff.raptor.remoting.client.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import uk.ac.cardiff.model.ServiceMetadata;
import uk.ac.cardiff.model.event.Event;
import uk.ac.cardiff.raptor.registry.Endpoint;
import uk.ac.cardiff.raptor.registry.EndpointRegistry;
import uk.ac.cardiff.raptor.remoting.client.EventReleaseClient;
import uk.ac.cardiff.raptor.remoting.client.EventReleaseEngine;
import uk.ac.cardiff.raptor.remoting.client.ReleaseFailureException;

public class EventReleaseClientImpl implements EventReleaseClient {

	/** Class logger. */
	private final Logger log = LoggerFactory.getLogger(EventReleaseClientImpl.class);

	/** Encapsulation of all endpoints this client can communication with */
	private EndpointRegistry endpointRegistry;

	/** The engine that performs event release to a client endpoint */
	private EventReleaseEngine eventReleaseEngine;

	/** Whether events should be released. Defaults to true. */
	private boolean enableEventRelease;

	public EventReleaseClientImpl() {
		eventReleaseEngine = new EventReleaseEngine();
		enableEventRelease = true;
	}

	@Override
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

	public List<Endpoint> getEndpoints() {
		return endpointRegistry.getEndpoints();
	}

	public void setEndpointRegistry(EndpointRegistry endpointRegistry) {
		this.endpointRegistry = endpointRegistry;
	}

	public EndpointRegistry getEndpointRegistry() {
		return endpointRegistry;
	}

	public void setEventReleaseEngine(EventReleaseEngine eventReleaseEngine) {
		this.eventReleaseEngine = eventReleaseEngine;
	}

	public EventReleaseEngine getEventReleaseEngine() {
		return eventReleaseEngine;
	}

	/**
	 * @param enableEventRelease
	 *            the enableEventRelease to set
	 */
	public void setEnableEventRelease(boolean enableEventRelease) {
		this.enableEventRelease = enableEventRelease;
	}

	/**
	 * @return the enableEventRelease
	 */
	public boolean isEnableEventRelease() {
		return enableEventRelease;
	}

	public boolean isEnabled(){
	    return isEnableEventRelease();
	}

}
