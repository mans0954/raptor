package uk.ac.cardiff.raptor.remoting.client.impl;

import java.util.List;

import uk.ac.cardiff.model.ClientMetadata;
import uk.ac.cardiff.model.Event;
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
