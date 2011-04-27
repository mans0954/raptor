/**
 *
 */
package uk.ac.cardiff.raptor.event.expansion;

/**
 * @author philsmart
 *
 */
public class AttributeLookup {

    /** The name of the attribute in the source data store */
    private String sourceAttributeName;

    /** The name of the attribute in the internal model that this attribute maps to*/
    private String internalAttributeName;

    /**
     * @param sourceAttributeName the sourceAttributeName to set
     */
    public void setSourceAttributeName(String sourceAttributeName) {
        this.sourceAttributeName = sourceAttributeName;
    }

    /**
     * @return the sourceAttributeName
     */
    public String getSourceAttributeName() {
        return sourceAttributeName;
    }

    /**
     * @param internalAttributeName the internalAttributeName to set
     */
    public void setInternalAttributeName(String internalAttributeName) {
        this.internalAttributeName = internalAttributeName;
    }

    /**
     * @return the internalAttributeName
     */
    public String getInternalAttributeName() {
        return internalAttributeName;
    }


}
