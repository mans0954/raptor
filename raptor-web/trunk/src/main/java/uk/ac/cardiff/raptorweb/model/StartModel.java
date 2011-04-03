/**
 *
 */
package uk.ac.cardiff.raptorweb.model;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



import uk.ac.cardiff.model.wsmodel.Capabilities;

/**
 * @author philsmart
 *
 */
public class StartModel implements Serializable{

    	/** Generated Serial UID */
    	private static final long serialVersionUID = 6795818266913419538L;

	/** The Class logger */
	private final Logger log = LoggerFactory.getLogger(StartModel.class);

	private Capabilities attachedMUACapabilities;

	/** Holds the statistics used by the dashboard on the front page*/
	private StartStatistics startStatistics;

	private TimeRange statsRangeSelector;

	public enum TimeRange{TODAY,LASTWEEK,LASTMONTH,LASTYEAR}

	public StartModel(){
	    statsRangeSelector = TimeRange.TODAY;
	}


	public void setAttachedMUACapabilities(Capabilities attachedMUACapabilities) {
	    this.attachedMUACapabilities = attachedMUACapabilities;
	}

	public Capabilities getAttachedMUACapabilities() {
	    return attachedMUACapabilities;
	}


	/**
	 * Sets the statsRangeSelector as a String which is mapped to the correct
	 * enum type (used mainly by view components that do not deal with enums well)
	 *
	 * @param timeRange
	 */
	public void setStatsRangeSelectorString(String timeRange){
	    for (TimeRange time : TimeRange.values()){
		if (time.toString().equals(timeRange))
		    statsRangeSelector = time;
	    }
	}

	public TimeRange getStatsRangeSelector(){
	    return statsRangeSelector;
	}

	/**
	 * Gets the statsRangeSelector value as a string (used mainly by view components that do not
	 * deal with enums well)
	 * @return
	 */
	public String getStatsRangeSelectorString() {
	    return statsRangeSelector.toString();
	}

	public void setStartStatistics(StartStatistics startStatistics) {
	    this.startStatistics = startStatistics;
	}

	public StartStatistics getStartStatistics() {
	    return startStatistics;
	}




}
