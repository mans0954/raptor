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
package uk.ac.cardiff.raptor.registry;

import java.util.Date;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import uk.ac.cardiff.raptor.attribute.filtering.AttrributeFilterEngine;
import uk.ac.cardiff.raptor.remoting.client.sei.ServiceEndpointClient;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import uk.ac.cardiff.model.ClientMetadata;
import uk.ac.cardiff.model.event.Event;
import uk.ac.cardiff.model.wsmodel.EventPushMessage;

/**
 * @author philsmart
 *
 */
public class EventReleaseEngine {

	private AttrributeFilterEngine attributeFilterEngine;
	private ServiceEndpointClient serviceEndpointInterface;

	/** Class logger */
	private final Logger log = LoggerFactory.getLogger(EventReleaseEngine.class);

	/**
	 * Performs the release of all events to all applicable endpoints defined in
	 * the endpoint registry. Endpoints are applicable to be sent to if they
	 * meet the push policy defined on them. Event attributes are also filtered
	 * according to the attribute filter policy defined per endpoint.
	 *
	 * @param authenticationModules
	 */
	public boolean release(EndpointRegistry endpointRegistry, List<Event> events, ClientMetadata clientMetadata) {
		boolean releasedtoAll = true;
		int releaseCount = 0;
		for (Endpoint endpoint : endpointRegistry.getEndpoints()) {
			boolean shouldRelease = (endpoint.getPushPolicy().getPushOnOrAfterNoEntries() <= events.size());
			log.debug("Endpoint {}, should release {}", endpoint.getServiceEndpoint(), shouldRelease);
			List<Event> filteredEntries = filterAttributes(endpoint, events);
			EventPushMessage pushMessage = constructEventPush(clientMetadata, filteredEntries);
			if (shouldRelease) {
				log.debug("Pushing {} entries to the Endpoint [{}]", filteredEntries.size(),endpoint.getServiceEndpoint());
				boolean releaseSuccess = getServiceEndpointInterface().sendEvents(pushMessage,endpoint);
				log.debug("Release to [{}] succeeded {}", endpoint.getServiceEndpoint(), releaseSuccess);
				if (releaseSuccess == false)
					releasedtoAll = false;
				else
					releaseCount++;
			} else {
				releasedtoAll = false;
			}

		}
		log.info("Released to {} listening Endpoints, out of a total of {}", releaseCount, endpointRegistry
				.getEndpoints().size());

		return releasedtoAll;

	}

	/**
	 * Filters the attributes from each event being pushed to the input
	 * endpoint. If no filter policy has been defined, no work is done, and the
	 * input allEvents is returned without modification
	 *
	 * @param endpoint
	 * @param allEvents
	 * @return
	 */
	private List<Event> filterAttributes(Endpoint endpoint, List<Event> allEvents) {
		if (endpoint.getAttributeFilterPolicy() == null)
			return allEvents;
		return attributeFilterEngine.filter(endpoint.getAttributeFilterPolicy(), allEvents);
	}

	/**
	 * Constructs an event push message, which encapsulates the events to send
	 *
	 * @param clientMetadata
	 * @param events
	 * @return
	 */
	private EventPushMessage constructEventPush(ClientMetadata clientMetadata, List<Event> events) {
		EventPushMessage pushMessage = new EventPushMessage();
		pushMessage.setClientMetadata(clientMetadata);
		pushMessage.setEvents(events);
		pushMessage.setTimeOfPush(new Date(System.currentTimeMillis()));
		return pushMessage;
	}

	public void setAttributeFilterEngine(AttrributeFilterEngine attributeFilterEngine) {
		this.attributeFilterEngine = attributeFilterEngine;
	}

	public AttrributeFilterEngine getAttributeFilterEngine() {
		return attributeFilterEngine;
	}

	public void setServiceEndpointInterface(ServiceEndpointClient serviceEndpointInterface) {
		this.serviceEndpointInterface = serviceEndpointInterface;
	}

	public ServiceEndpointClient getServiceEndpointInterface() {
		return serviceEndpointInterface;
	}

}
