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

	public String toString(){
	    StringBuilder result = new StringBuilder();
	    result.append(this.getClass().getName());
	    result.append("Object{");
	    result.append(serviceEndpoint);
	    result.append("}");
	    return result.toString();
	}

}
