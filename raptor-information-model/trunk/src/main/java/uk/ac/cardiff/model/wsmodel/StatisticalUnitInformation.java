/**
 *
 */
package uk.ac.cardiff.model.wsmodel;

import java.io.Serializable;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This class only represents information, the dattypes are strings, and the strings maybe formatted As a result, this class is only for human consumption (or
 * presentation to the user)
 * 
 * @author philsmart
 * 
 */
public class StatisticalUnitInformation implements Serializable {

    /** generated serial UID for this class */
    private static final long serialVersionUID = 4580271084108294958L;

    /** class logger */
    private final Logger log = LoggerFactory.getLogger(StatisticalUnitInformation.class);

    /** Class representing all parameters for this statistical unit */
    private StatisticParameters statisticParameters;

    /** List of the configured pre-processors */
    private List<ProcessorInformation> preprocessors;

    /** List of the configured post-processors */
    private List<ProcessorInformation> postprocessors;

    public void setStatisticParameters(StatisticParameters statisticParameters) {
        this.statisticParameters = statisticParameters;
    }

    public StatisticParameters getStatisticParameters() {
        return statisticParameters;
    }

    /**
     * @param preprocessors
     *            the preprocessors to set
     */
    public void setPreprocessors(List<ProcessorInformation> preprocessors) {
        this.preprocessors = preprocessors;
    }

    /**
     * @return the preprocessors
     */
    public List<ProcessorInformation> getPreprocessors() {
        return preprocessors;
    }

    /**
     * @param postprocessors
     *            the postprocessors to set
     */
    public void setPostprocessors(List<ProcessorInformation> postprocessors) {
        this.postprocessors = postprocessors;
    }

    /**
     * @return the postprocessors
     */
    public List<ProcessorInformation> getPostprocessors() {
        return postprocessors;
    }

}
