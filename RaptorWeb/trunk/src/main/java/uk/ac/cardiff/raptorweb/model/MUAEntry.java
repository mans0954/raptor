/**
 *
 */
package uk.ac.cardiff.raptorweb.model;

import java.io.Serializable;

/**
 * @author philsmart
 *
 */
public class MUAEntry implements Serializable{

	private String serviceEndpoint;
	private boolean isAttached;

	public void setServiceEndpoint(String serviceEndpoint) {
		this.serviceEndpoint = serviceEndpoint;
	}

	public String getServiceEndpoint() {
		return serviceEndpoint;
	}

	public void setIsAttached(boolean isAttached) {
		this.isAttached = isAttached;
	}

	public boolean getIsAttached() {
		return isAttached;
	}

}
