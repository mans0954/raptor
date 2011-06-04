package uk.ac.cardiff.model.resource;

import java.io.Serializable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class ResourceMetadata implements Serializable{
    
    /** Class logger. */
    private final Logger log = LoggerFactory.getLogger(ResourceMetadata.class);

    /** Generated Serial UID */
    private static final long serialVersionUID = -6007311260031942309L;

    /** Identifier for hibernate. */
    private long persistantId;
    
    /** The identifier of the resource.*/
    private String resourceId;
    
    /** Whether this resouceId represents a resource internal to the organisation.*/
    private boolean internal;
    
    /** Whether this resourceId represents a resource external to the organisation. */
    private boolean external;
    
    
    public String getInternalExternal(){        
        if (internal==true){
            return "Internal";
        }
        else if (external==true){
            return "External";
        }
        return "Unknown";
    }
    
    public void setInternalExternal(String option){
        if (option.equals("Internal")){
            internal = true;
            external = false;            
        }
        else if (option.equals("External")){
            internal = false;
            external = true;
        }
        else{
            internal = false;
            external = true;
        }
    }
    
    public int getResourceCategory() {
        if (internal==true){
            return 1;
        }
        else if (external==true){
            return 2;
        }
        return 0;
    }
    
    public void setResourceCategory(int resourceCategory){
        
    }
    
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append(this.getClass().getName());
        sb.append("[");
        sb.append(persistantId);
        sb.append(":");
        sb.append(resourceId);
        sb.append(":");
        sb.append(internal);
        sb.append(":");
        sb.append(external);
        sb.append("]");
        return sb.toString();
        
    }

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
