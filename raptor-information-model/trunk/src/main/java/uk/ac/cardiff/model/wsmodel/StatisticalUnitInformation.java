/**
 *
 */
package uk.ac.cardiff.model.wsmodel;

import java.io.Serializable;
import java.util.List;


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

    /** Class representing all parameters for this statistical unit */
    private StatisticParameters statisticParameters;

    /** Simple list (representing names) of the configured pre-processors*/
    private List<ProcessorInformation> preprocessors;

    /** Simple list (representing names) of the configured post-processors*/
    private List<ProcessorInformation> postprocessors;



    public void setStatisticParameters(StatisticParameters statisticParameters) {
	this.statisticParameters = statisticParameters;
    }

    public StatisticParameters getStatisticParameters() {
	return statisticParameters;
    }


    /**
     * human consumable output method for array of preprocessors
     * @return
     */
    public String getPreProcessorsAsString(){
	StringBuilder output = new StringBuilder();
	if (preprocessors==null)
	    return output.toString();
	int count=0;
	for (ProcessorInformation preprocessor : preprocessors){
	    output.append(preprocessor.getBeanName());
	    if (count < preprocessors.size()-1)output.append(", ");
	    count++;
	}
	return output.toString();
    }

    /**
     * human consumable output method for array of postprocessors
     * @return
     */
    public String getPostProcessorsAsString(){
	StringBuilder output = new StringBuilder();
	if (preprocessors==null)
	    return output.toString();
	int count=0;
	for (ProcessorInformation postprocessor : postprocessors){
	    output.append(postprocessor.getBeanName());
	    if (count < postprocessors.size()-1)output.append(", ");
	    count++;
	}
	return output.toString();
    }

    /**
     * @param preprocessors the preprocessors to set
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
     * @param postprocessors the postprocessors to set
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
