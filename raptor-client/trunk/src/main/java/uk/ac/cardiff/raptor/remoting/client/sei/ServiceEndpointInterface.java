package uk.ac.cardiff.raptor.remoting.client.sei;

import uk.ac.cardiff.model.wsmodel.EventPushMessage;

public interface ServiceEndpointInterface {
	
	public boolean sendEvents(EventPushMessage pushed, String endpoint);

}
