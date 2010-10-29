/**
 *
 */
package uk.ac.cardiff.raptormua.engine.statistics;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import uk.ac.cardiff.model.AuthenticationEntry;
import uk.ac.cardiff.model.Entry;
import uk.ac.cardiff.raptormua.exceptions.PreprocessorException;
import uk.ac.cardiff.raptormua.runtimeutils.EntryClone;

/**
 * @author philsmart
 * Holds a statistics unit or one statistics operation on one piece of data
 */
public class Statistic {

	static Logger log = Logger.getLogger(Statistic.class);

	private List<Entry> entries;
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

	/** add a preprocessing module to the statistical method */
	private StatisticsPreProcessing preprocessor;


	/**
	 * <p> Always creates a copy of the entries, as the statisical method is
	 * not safe, in that it may alter the state of the original data i.e. a
	 * preprocessing method </p>
	 *
	 * @param authEntries
	 */
	public void setEntries(List<Entry> authEntries) {
		ArrayList<Entry> entriesCopy = EntryClone.cloneEntries((ArrayList<Entry>) authEntries);
		if (preprocessor!=null)
			try {
				log.info("Invoking statistical preprocessor "+preprocessor.getClass());
				entriesCopy = (ArrayList<Entry>) preprocessor.preProcess(entriesCopy);
			} catch (PreprocessorException e) {
				log.error("Could not preprocess entries "+preprocessor.getClass());
			}
		this.entries = entriesCopy;
	}


	/**
	 * <p> </p>
	 * @param date
	 * @param isEndTime - if is endTime and only ddMMyyyy is given, then the endTime should be 23.59 as opposed to 00.00 as this is the end of the day
	 * @return
	 */
	private DateTime formatDate(String date, boolean isEndTime){
		/* this is not a nice hack, please tidy*/
		log.debug("Date format being parsed "+date+" with "+date.length()+" characters");
		if (date.length()==8){
			//assume ddMMyyy
			 String format = "ddMMyyyy";
			 DateTimeFormatter dtf = DateTimeFormat.forPattern(format);
			 DateTime dt = dtf.parseDateTime(date.substring(0, date.length()));
			 if (isEndTime){
				 dt = new DateTime(dt.getMillis()+86340000); //where 86340000 is 23.59 hours
			 }
			 log.debug("time set to "+dt.getDayOfMonth()+"th "+dt.getMonthOfYear()+" "+dt.getYear()+" "+dt.getHourOfDay()+":"+dt.getMinuteOfHour()+":"+dt.getSecondOfMinute()+" for "+this.getUnitName());
			 return dt;
		}
		else if(date.length()==15){
			//assume yyyyMMdd'T'HHmmss
			String format = "yyyyMMdd'T'HHmmss";
			 DateTimeFormatter dtf = DateTimeFormat.forPattern(format);
			 DateTime dt = dtf.parseDateTime(date.substring(0, date.length()));
			 log.debug("time set to "+dt.getDayOfMonth()+"th "+dt.getMonthOfYear()+" "+dt.getYear()+" "+dt.getHourOfDay()+":"+dt.getMinuteOfHour()+":"+dt.getSecondOfMinute()+" for "+this.getUnitName());
			return dt;
		}
		return new DateTime();
	}


	public List<Entry> getAuthEntries() {
		return entries;
	}

	public void setField(String field) {
		this.field = field;
	}

	public String getField() {
		return field;
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

	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}

	public String getUnitName() {
		return unitName;
	}

	public void setPreprocessor(StatisticsPreProcessing preprocessor) {
		this.preprocessor = preprocessor;
	}

	public StatisticsPreProcessing getPreprocessor() {
		return preprocessor;
	}

	public void setSeriesLabel(String seriesLabel) {
		this.seriesLabel = seriesLabel;
	}

	public String getSeriesLabel() {
		return seriesLabel;
	}


	public void setStartTime(String startTime) {
		this.startTime = formatDate(startTime, false);
	}

	public String getStartTime() {
		return startTime.toString();
	}

	public void setEndTime(String endTime) {
		this.endTime = formatDate(endTime,true);
	}

	public String getEndTime() {
		return endTime.toString();
	}


	/**
	 * @return
	 */
	public DateTime getStartTimeAsDate() {
		return startTime;
	}

	public DateTime getEndTimeAsDate() {
		return endTime;
	}



}
