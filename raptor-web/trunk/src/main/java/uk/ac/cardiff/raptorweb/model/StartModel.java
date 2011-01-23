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

	static Logger log = LoggerFactory.getLogger(StartModel.class);
	
	private Capabilities attachedMUACapabilities;
	
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


	public void setStatsRangeSelector(TimeRange timeRange){
	    this.statsRangeSelector = timeRange;
	}

	public TimeRange getStatsRangeSelector() {
	    return statsRangeSelector;
	}

	public void setStartStatistics(StartStatistics startStatistics) {
	    this.startStatistics = startStatistics;
	}

	public StartStatistics getStartStatistics() {
	    return startStatistics;
	}




}
