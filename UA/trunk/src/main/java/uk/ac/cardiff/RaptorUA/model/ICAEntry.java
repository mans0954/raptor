/**
 *
 */
package uk.ac.cardiff.RaptorUA.model;

import java.util.List;
import java.util.Set;

import uk.ac.cardiff.RaptorUA.engine.sei.ServiceEndpointInterface;

/**
 * @author philsmart
 *
 */
public class ICAEntry {

    private String serviceEndpoint;

    public void setServiceEndpoint(String serviceEndpoint) {
	this.serviceEndpoint = serviceEndpoint;
    }

    public String getServiceEndpoint() {
	return serviceEndpoint;
    }

    public Set getAllAuthentications(){
	return ServiceEndpointInterface.getAuthentications(serviceEndpoint);

    }

    public Set getAllUsages(){

	return null;
    }

}
