package uk.ac.cardiff.model.wsmodel;

import java.io.Serializable;
import java.util.List;

/**
 * Used to encapsulate processor information for sending between MUA and Web. Related closely to the MUA only {@link ProcessorTemplate}.
 * 
 */
public class ProcessorInformation implements Serializable {

    /** Generated Serial UID */
    private static final long serialVersionUID = 3004339123572624352L;

    /** canoncial name of the class type of this processor. Not a class type due to ageis binding. **/
    private String processorClass;

    private String friendlyName;

    private List<MethodParameter> methodParameters;

    /**
     * @param methodParameters
     *            the methodParameters to set
     */
    public void setMethodParameters(List<MethodParameter> methodParameters) {
        this.methodParameters = methodParameters;
    }

    /**
     * @return the methodParameters
     */
    public List<MethodParameter> getMethodParameters() {
        return methodParameters;
    }

    /**
     * @param processorClass
     *            the processorClass to set
     */
    public void setProcessorClass(String processorClass) {
        this.processorClass = processorClass;
    }

    /**
     * @return the processorClass
     */
    public String getProcessorClass() {
        return processorClass;
    }

    /**
     * @param friendlyName
     *            the friendlyName to set
     */
    public void setFriendlyName(String friendlyName) {
        this.friendlyName = friendlyName;
    }

    /**
     * @return the friendlyName
     */
    public String getFriendlyName() {
        return friendlyName;
    }

}
