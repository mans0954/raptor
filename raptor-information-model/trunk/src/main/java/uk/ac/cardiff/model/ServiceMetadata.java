/**
 *
 */
package uk.ac.cardiff.model;

import java.io.Serializable;

/**
 * @author philsmart
 *
 */
public class ServiceMetadata implements Serializable{

    /** Generated Serial UID */
    private static final long serialVersionUID = 7439134742456852675L;

    /** The name of the server this metadata is constructed for */
    private String description;

    /** The name of the organisation that has authoritative control over this client */
    private String organisationName;

    /** The email address of the individual responsible for maintaining this client */
    private String contactEmail;

    /** The name of the  service that this metadata is constructed for */
    private String serviceName;

    /** The unique identifier for this entity*/
    private String entityId;
 
    /**
     * @param organisationName the organisationName to set
     */
    public void setOrganisationName(String organisationName) {
        this.organisationName = organisationName;
    }

    /**
     * @return the organisationName
     */
    public String getOrganisationName() {
        return organisationName;
    }

    /**
     * @param contactEmail the contactEmail to set
     */
    public void setContactEmail(String contactEmail) {
        this.contactEmail = contactEmail;
    }

    /**
     * @return the contactEmail
     */
    public String getContactEmail() {
        return contactEmail;
    }

    /**
     * @param serviceName the serviceName to set
     */
    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    /**
     * @return the serviceName
     */
    public String getServiceName() {
        return serviceName;
    }

    /**
     * @param entityId the entityId to set
     */
    public void setEntityId(String entityId) {
        this.entityId = entityId;
    }

    /**
     * @return the entityId
     */
    public String getEntityId() {
        return entityId;
    }

    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }


}
