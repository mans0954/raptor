/**
 *
 */
package uk.ac.cardiff.model;

/**
 * @author philsmart
 *
 */
public class ClientMetadata {

    /** The service name of the client this metadata is constructed for */
    private String clientName;
    /** The name of the organisation that has authoritative control over this client */
    private String organisationName;
    /** The email address of the individual responsible for maintaining this client */
    private String contactEmail;


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
     * Sets the service name of this client
     * @param clientName
     */
    public void setClientName(String clientName) {
	this.clientName = clientName;
    }

    /**
     * Gets the service name of this client
     * @return
     */
    public String getClientName() {
	return clientName;
    }


}
