/**
 *
 */
package uk.ac.cardiff.raptormua.model;

import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;
import org.joda.time.DateTime;

import uk.ac.cardiff.model.Entry;
import uk.ac.cardiff.raptormua.engine.statistics.Statistic;
import uk.ac.cardiff.raptormua.engine.statistics.StatisticsPostProcessor;
import uk.ac.cardiff.raptormua.engine.statistics.StatisticsPreProcessor;

/**
 * @author philsmart
 *
 */
public class StatisticParameters {

    static Logger log = Logger.getLogger(StatisticParameters.class);

	private String field;
	private String unitName;
	private String methodName;
	private List<String> methodParams;

	/* The textual description of the series, as attached to the x-axis*/
	private String seriesLabel;

	/*the start time from which to produce the starts, defining the temporal extent
	 * If a starttime and endtime is not given, the entire temporal extent of the data series will be used
	 */
	protected DateTime startTime;

	/*the end time from which to produce the starts, defining the temporal extent */
	protected DateTime endTime;

	public void setField(String field) {
	    this.field = field;
	}

	public String getField() {
	    return field;
	}

	public void setUnitName(String unitName) {
	    this.unitName = unitName;
	}

	public String getUnitName() {
	    return unitName;
	}

	public void setMethodName(String methodName) {
	    this.methodName = methodName;
	}

	public String getMethodName() {
	    return methodName;
	}

	public void setMethodParams(List<String> methodParams) {
	    this.methodParams = methodParams;
	}

	public List<String> getMethodParams() {
	    return methodParams;
	}

	public void setSeriesLabel(String seriesLabel) {
	    this.seriesLabel = seriesLabel;
	}

	public String getSeriesLabel() {
	    return seriesLabel;
	}


}
