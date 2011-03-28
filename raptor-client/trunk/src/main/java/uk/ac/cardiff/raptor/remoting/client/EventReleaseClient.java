/**
 *
 */
package uk.ac.cardiff.raptor.remoting.client;

import java.util.List;

import uk.ac.cardiff.model.ClientMetadata;
import uk.ac.cardiff.model.event.Event;
import uk.ac.cardiff.raptor.exceptions.ReleaseFailureException;

/**
 * Sends event records using the CXF SOAP libraries
 * @author philsmart
 *
 */
public interface EventReleaseClient {
	
	public boolean release(List<Event> events, ClientMetadata clientMetadata) throws ReleaseFailureException;

}
