/**
 *
 */
package uk.ac.cardiff.model;

import java.io.Serializable;

/**
 * @author philsmart
 *
 */
public class ClientMetadata implements Serializable{

    /** Generated Serial UID */
    private static final long serialVersionUID = 4285002953433700443L;
    
    /** The name of the client this metadata is constructed for */
    private String clientName;
    
    /** The name of the organisation that has authoritative control over this client */
    private String organisationName;
    
    /** The email address of the individual responsible for maintaining this client */
    private String contactEmail;
    
    /** The name of the  service that this metadata is constructed for */
    private String serviceName;


    /**
     * Sets the organisation name that has authority over this client
     * @param organisationName
     */
    public void setOrganisationName(String organisationName) {
	this.organisationName = organisationName;
    }

    /**
     * Gets the organisation name set for this client
     * @return
     */
    public String getOrganisationName() {
	return organisationName;
    }

    /**
     * Sets the contact email address for the individual responsible for this client
     * @param contactEmail
     */
    public void setContactEmail(String contactEmail) {
	this.contactEmail = contactEmail;
    }

    /**
     * Gets the contact email address for the individual responsible for this client
     * @return
     */
    public String getContactEmail() {
	return contactEmail;
    }

    /**
     * Sets the name of this client
     * @param clientName
     */
    public void setClientName(String clientName) {
	this.clientName = clientName;
    }

    /**
     * Gets the name of this client
     * @return
     */
    public String getClientName() {
	return clientName;
    }

    /**
     * Sets the service name of this client
     * @param serviceName
     */
    public void setServiceName(String serviceName) {
	this.serviceName = serviceName;
    }

    /**
     * Gets the service name of this client
     * @return
     */
    public String getServiceName() {
	return serviceName;
    }


}
