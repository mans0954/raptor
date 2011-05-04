/**
 *
 */
package uk.ac.cardiff.model.wsmodel;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;


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

    /** Simple string values (representing names) of the configured pre-processors*/
    private List<String> preprocessors;
    
    /** Simple string values (representing names) of the configured post-processors*/
    private List<String> postprocessors;



    public void setStatisticParameters(StatisticParameters statisticParameters) {
	this.statisticParameters = statisticParameters;
    }

    public StatisticParameters getStatisticParameters() {
	return statisticParameters;
    }

    public void setPreprocessors(List<String> preprocessors) {
	this.preprocessors = preprocessors;
    }

    public List<String> getPreprocessors() {
	return preprocessors;
    }

    public void setPostprocessors(List<String> postprocessors) {
	this.postprocessors = postprocessors;
    }

    public List<String> getPostprocessors() {
	return postprocessors;
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
	for (String preprocessor : preprocessors){
	    output.append(preprocessor);
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
	for (String postprocessor : postprocessors){
	    output.append(postprocessor);
	    if (count < postprocessors.size()-1)output.append(", ");
	    count++;
	}
	return output.toString();
    }


}
