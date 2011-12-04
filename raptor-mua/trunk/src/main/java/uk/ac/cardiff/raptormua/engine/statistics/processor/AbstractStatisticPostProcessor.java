/**
 * 
 */

package uk.ac.cardiff.raptormua.engine.statistics.processor;

import java.util.List;

import uk.ac.cardiff.model.wsmodel.MethodParameter;
import uk.ac.cardiff.raptormua.engine.statistics.StatisticPostProcessor;

/**
 * @author philsmart
 * 
 */
public abstract class AbstractStatisticPostProcessor implements StatisticPostProcessor {

    private String friendlyName;

    private String processorId;

    /**
     * Throw an {@link PostprocessorException} if something about the setup of this postprocessor has gone wrong, and
     * hence the postprocessor should not be attached to the statistic.
     */
    public abstract void registerAndSetMethodParameters(List<MethodParameter> methodParameters)
            throws PostprocessorException;

    /**
     * @param processorId the processorId to set
     */
    public void setProcessorId(String processorId) {
        this.processorId = processorId;
    }

    /**
     * @return the processorId
     */
    public String getProcessorId() {
        return processorId;
    }

    /**
     * @param friendlyName the friendlyName to set
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
