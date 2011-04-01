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

import uk.ac.cardiff.model.ClientMetadata;
import uk.ac.cardiff.model.event.Event;
import uk.ac.cardiff.raptor.exceptions.ReleaseFailureException;
import uk.ac.cardiff.raptor.registry.EndpointRegistry;
import uk.ac.cardiff.raptor.registry.EventReleaseEngine;
import uk.ac.cardiff.raptor.remoting.client.EventReleaseClient;


public class EventReleaseClientImpl implements EventReleaseClient{
	
	private EndpointRegistry endpointRegistry;
	private EventReleaseEngine eventReleaseEngine;
	
	public EventReleaseClientImpl(){
		eventReleaseEngine = new EventReleaseEngine();
	}


	@Override
	public boolean release(List<Event> events, ClientMetadata clientMetadata) throws ReleaseFailureException{
		boolean success = eventReleaseEngine.release(endpointRegistry, events, clientMetadata);		
		return success;
		
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


}
