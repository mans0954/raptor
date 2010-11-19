/**
 * Copyright (C) 2010 Cardiff University, Wales <smartp@cf.ac.uk>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
/**
 *
 */
package uk.ac.cardiff.raptormua.engine.statistics;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.LinkedHashSet;

import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import uk.ac.cardiff.model.Entry;
import uk.ac.cardiff.model.Graph.AggregatorGraphModel;
import uk.ac.cardiff.raptormua.engine.statistics.records.Bucket;
import uk.ac.cardiff.raptormua.engine.statistics.records.Group;
import uk.ac.cardiff.raptormua.engine.statistics.records.Observation;
import uk.ac.cardiff.raptormua.exceptions.PostprocessorException;
import uk.ac.cardiff.raptormua.exceptions.PreprocessorException;
import uk.ac.cardiff.raptormua.runtimeutils.EntryClone;

/**
 * @author philsmart
 * Holds a statistics unit or one statistics operation on one piece of data
 */
public class Statistic {

	static Logger log = Logger.getLogger(Statistic.class);

	private Set<Entry> entries;
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

	/* add a preprocessing module to the statistical method */
	private StatisticsPreProcessor preprocessor;

	private List<StatisticsPostProcessor> postprocessor;

	/*each statistical method produces objects which are stored in this array variable ready for postprocessing
	 * or construction of an <code>AggregatorGraphModel</code>
	 */
	protected Observation[] observations;

	/**
	 * <p> Always creates a copy of the entries, as the statisical method is
	 * not safe, in that it may alter the state of the original data i.e. a
	 * preprocessing method </p>
	 *
	 * @param authEntries
	 */
	public void setEntries(Set<Entry> authEntries) {
	    	Set<Entry> entriesCopy = EntryClone.cloneEntries(((LinkedHashSet<Entry>) authEntries));
		if (preprocessor!=null)
			try {
				log.info("Invoking statistical preprocessor "+preprocessor.getClass());
				entriesCopy = (Set<Entry>) preprocessor.preProcess(entriesCopy);
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

	/**
	 * <p> construct a graph model from the data observations and groupings stored in the
	 * buckets </p>
	 * @return
	 */
	public AggregatorGraphModel constructGraphModel(){
		AggregatorGraphModel gmodel = new AggregatorGraphModel();

		log.debug("Observations type "+observations);

		if (observations instanceof Group[]){
			log.info("Constructing graph model for Group type");
			Group[] groups = (Group[]) observations;

			gmodel.addSeriesLabel(getSeriesLabel());
			for (Group group : groups){
				gmodel.addGroupLabel(group.getGroupName());
				List<Double> values = new ArrayList();
				Double valueDouble = new Double(group.getValue());
				values.add(valueDouble);
				gmodel.addGroupValue(values);
			}
		}
		else if (observations instanceof Bucket[]){
			log.info("Constructing graph model for Bucket type");
			Bucket[] buckets = (Bucket[])observations;

			gmodel.addSeriesLabel(getSeriesLabel());
			DateTimeFormatter startParser = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm");
			DateTimeFormatter endParser = DateTimeFormat.forPattern("HH:mm");
			for (Bucket bucket : buckets){
				gmodel.addGroupLabel(startParser.print(bucket.getStart())+"-"+endParser.print(bucket.getEnd()));
				List<Double> values = new ArrayList();
				Double valueDouble = new Double(bucket.getValue());
				values.add(valueDouble);
				gmodel.addGroupValue(values);
			}
		}

		return gmodel;
	}

	/**
	 * <p> pre processing effects the entries that go into the statistical unit
	 * post processing effects the observations that result form the statistical unit </p>
	 */
	public void postProcess() {
		try {
			if (getPostprocessor()!=null){
				for (StatisticsPostProcessor post : postprocessor)
					post.postProcess(observations);

			}
		} catch (PostprocessorException e) {
			log.error("Could not post process entries, using "+getPostprocessor().getClass());
		}
	}


	public Set<Entry> getAuthEntries() {
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

	public void setPreprocessor(StatisticsPreProcessor preprocessor) {
		this.preprocessor = preprocessor;
	}

	public StatisticsPreProcessor getPreprocessor() {
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



	public List<StatisticsPostProcessor> getPostprocessor() {
		return postprocessor;
	}


	public void setPostprocessor(List<StatisticsPostProcessor> postprocessor) {
		this.postprocessor = postprocessor;
	}








}
