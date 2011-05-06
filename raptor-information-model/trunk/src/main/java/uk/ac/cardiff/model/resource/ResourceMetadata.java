package uk.ac.cardiff.model.resource;

public class ResourceMetadata {

    /** Identifier for hibernate. */
    private long persistantId;
    
    /** The identifier of the resource.*/
    private String resourceId;
    
    /** Whether this resouceId represents a resource internal to the organisation.*/
    private boolean internal;
    
    /** Whether this resourceId represents a resource external to the organisation. */
    private boolean external;

    /**
     * @param resourceId the resourceId to set
     */
    public void setResourceId(String resourceId) {
        this.resourceId = resourceId;
    }

    /**
     * @return the resourceId
     */
    public String getResourceId() {
        return resourceId;
    }

    /**
     * @param internal the internal to set
     */
    public void setInternal(boolean internal) {
        this.internal = internal;
    }

    /**
     * @return the internal
     */
    public boolean isInternal() {
        return internal;
    }

    /**
     * @param external the external to set
     */
    public void setExternal(boolean external) {
        this.external = external;
    }

    /**
     * @return the external
     */
    public boolean isExternal() {
        return external;
    }

    /**
     * @param persistantId the persistantId to set
     */
    public void setPersistantId(long persistantId) {
        this.persistantId = persistantId;
    }

    /**
     * @return the persistantId
     */
    public long getPersistantId() {
        return persistantId;
    }
    
    
}
