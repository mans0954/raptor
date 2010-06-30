/**
 *
 */
package uk.ac.cardiff.raptormua.model;

import java.util.List;

import uk.ac.cardiff.raptormua.engine.sei.ServiceEndpointInterface;

/**
 * @author philsmart
 *
 */
public class UAEntry {

	private String serviceEndpoint;

	public void setServiceEndpoint(String serviceEndpoint) {
		this.serviceEndpoint = serviceEndpoint;
	}

	public String getServiceEndpoint() {
		return serviceEndpoint;
	}

	public List getAllAuthentications(){
		return ServiceEndpointInterface.getAuthentications(serviceEndpoint);

	    }
}
