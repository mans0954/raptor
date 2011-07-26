/**
 *
 */
package uk.ac.cardiff.model.event.auxiliary;

/**
 * @author philsmart
 *
 */
public class EventMetadata {

    /** The entityId of the service the associated {@link uk.ac.cardiff.model.event.Event}
     * applies to
     */
    private String entityId;

    /** The hostname of the service the {@link uk.ac.cardiff.model.event.Event} belongs to.*/
    private String serviceName;

    /** The name of the organisation that generated the associated {@link uk.ac.cardiff.model.event.Event}.*/
    private String organisationName;

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

}
