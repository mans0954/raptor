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

import uk.ac.cardiff.model.StatisticParameters;

/**
 * This class only represents information, the dattypes are strings, and the strings maybe formatted As a result, this class is only for human consumption (or
 * presentation to the user)
 *
 * @author philsmart
 *
 */
public class StatisticalUnitInformation implements Serializable {

    /* generated serial UID for this class */
    private static final long serialVersionUID = 4580271084108294958L;

    private StatisticParameters statisticParameters;

    /* both these are simple string values (representing names) for the time being*/
    private List<String> preprocessors;
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

}
