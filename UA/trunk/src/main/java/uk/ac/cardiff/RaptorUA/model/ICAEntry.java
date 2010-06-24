/**
 *
 */
package uk.ac.cardiff.RaptorUA.model;

import java.util.List;

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

    public List getAllAuthentications(){
	return ServiceEndpointInterface.getAuthentications(serviceEndpoint);

    }

    public List getAllUsages(){

	return null;
    }

}
