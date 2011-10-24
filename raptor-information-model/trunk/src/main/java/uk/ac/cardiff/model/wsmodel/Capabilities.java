/**
 *
 */
package uk.ac.cardiff.model.wsmodel;

import java.io.Serializable;
import java.util.List;

import uk.ac.cardiff.model.ServiceMetadata;
import uk.ac.cardiff.model.resource.ResourceMetadata;

/**
 * The Class Capabilities.
 * 
 * @author philsmart
 */
public class Capabilities implements Serializable {

    /** Generated Serial UID. */
    private static final long serialVersionUID = -4008642148652388534L;

    /** The list of attached MUAs. */
    private List<String> attached;

    /** The list of statistical services the attached MUA has configured. */
    private List<StatisticalUnitInformation> statisticalServices;

    /** Any error message that occurs during processing. */
    private String errorMessage;

    /** If an error occured during processing. */
    private boolean error;

    /** The metadata of the attached Service e.g. the attached MUA. */
    private ServiceMetadata metadata;

    /** The suggestion values used by the UI to display in suggestion boxes. */
    private SuggestionValues suggestionValues;

    /** The resource metadata. */
    private List<ResourceMetadata> resourceMetadata;

    /** The number of events store in the attached MUA. */
    private long numberOfEntries;

    /**
     * Sets the statistical services.
     * 
     * @param statisticalServices
     *            the new statistical services
     */
    public void setStatisticalServices(List<StatisticalUnitInformation> statisticalServices) {
        this.statisticalServices = statisticalServices;
    }

    /**
     * Returns all configured statistical units.
     * 
     * @return the statistical services
     */
    public List<StatisticalUnitInformation> getStatisticalServices() {
        return statisticalServices;
    }

    /**
     * Sets the attached.
     * 
     * @param attached
     *            the new attached
     */
    public void setAttached(List<String> attached) {
        this.attached = attached;
    }

    /**
     * Gets the attached.
     * 
     * @return the attached
     */
    public List<String> getAttached() {
        return attached;
    }

    /**
     * Sets the error message.
     * 
     * @param errorMessage
     *            the errorMessage to set
     */
    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    /**
     * Gets the error message.
     * 
     * @return the errorMessage
     */
    public String getErrorMessage() {
        return errorMessage;
    }

    /**
     * Sets the error.
     * 
     * @param error
     *            the error to set
     */
    public void setError(boolean error) {
        this.error = error;
    }

    /**
     * Checks if is error.
     * 
     * @return the error
     */
    public boolean isError() {
        return error;
    }

    /**
     * Sets the suggestion values.
     * 
     * @param suggestionValues
     *            the new suggestion values
     */
    public void setSuggestionValues(SuggestionValues suggestionValues) {
        this.suggestionValues = suggestionValues;
    }

    /**
     * Gets the suggestion values.
     * 
     * @return the suggestion values
     */
    public SuggestionValues getSuggestionValues() {
        return suggestionValues;
    }

    /**
     * Sets the metadata.
     * 
     * @param metadata
     *            the new metadata
     */
    public void setMetadata(ServiceMetadata metadata) {
        this.metadata = metadata;
    }

    /**
     * Gets the metadata.
     * 
     * @return the metadata
     */
    public ServiceMetadata getMetadata() {
        return metadata;
    }

    /**
     * Sets the number of authentications stored.
     * 
     * @param numberOfEntries
     *            the new number of authentications stored
     */
    public void setNumberOfAuthenticationsStored(long numberOfEntries) {
        this.numberOfEntries = numberOfEntries;

    }

    /**
     * Gets the number of authentications stored.
     * 
     * @return the number of authentications stored
     */
    public long getNumberOfAuthenticationsStored() {
        return numberOfEntries;
    }

    /**
     * Sets the resource metadata.
     * 
     * @param resourceMetadata
     *            the resourceMetadata to set
     */
    public void setResourceMetadata(List<ResourceMetadata> resourceMetadata) {
        this.resourceMetadata = resourceMetadata;
    }

    /**
     * Gets the resource metadata.
     * 
     * @return the resourceMetadata
     */
    public List<ResourceMetadata> getResourceMetadata() {
        return resourceMetadata;
    }

}
