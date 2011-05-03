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
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.LinkedHashSet;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import uk.ac.cardiff.model.event.Event;
import uk.ac.cardiff.model.report.AggregatorGraphModel;
import uk.ac.cardiff.model.report.Series;
import uk.ac.cardiff.model.wsmodel.MethodParameter;
import uk.ac.cardiff.model.wsmodel.StatisticParameters;
import uk.ac.cardiff.raptor.store.EntryHandler;
import uk.ac.cardiff.raptormua.engine.statistics.records.Bucket;
import uk.ac.cardiff.raptormua.engine.statistics.records.Group;
import uk.ac.cardiff.raptormua.engine.statistics.records.Observation;
import uk.ac.cardiff.raptormua.exceptions.PostprocessorException;
import uk.ac.cardiff.raptormua.exceptions.PreprocessorException;
import uk.ac.cardiff.raptormua.exceptions.StatisticalUnitException;
import uk.ac.cardiff.raptormua.runtimeutils.EntryClone;

/**
 * @author philsmart Holds a statistics unit or one statistics operation on one
 *         piece of data
 */
public abstract class Statistic {

	/** Class logger */
	private final Logger log = LoggerFactory.getLogger(Statistic.class);

	private EntryHandler entryHandler;

	protected StatisticParameters statisticParameters;

	/* add a preprocessing module to the statistical method */
	private StatisticsPreProcessor preprocessor;

	/* add a postprocessing module to the statistical method */
	private List<StatisticsPostProcessor> postprocessor;

	private List<ObservationSeries> observationSeries;

	public Statistic() {
		setObservationSeries(new ArrayList<ObservationSeries>());
	}

	public abstract Boolean performStatistic(ArrayList<MethodParameter> methodParams, String sqlWhere) throws StatisticalUnitException;


	/**
	 * Important this this method is called during the statistic process lifecycle, so that any state variables
	 * e.g. the observationSeries, is reset ready for the next invocation
	 */
	public void reset(){
	    observationSeries.clear();
	}

	public void setEntryHandler(EntryHandler entryHandler) {
		if (preprocessor != null)
			try {
				log.info("Invoking statistical preprocessor " + preprocessor.getClass());
				preprocessor.preProcess(entryHandler);
			} catch (PreprocessorException e) {
				log.error("Could not preprocess entries " + preprocessor.getClass());
			}
		this.entryHandler = entryHandler;
	}

	/**
	 * <p>
	 * construct a graph model from the data observations and groupings stored
	 * in the buckets
	 * </p>
	 *
	 * @return
	 */
	public AggregatorGraphModel constructGraphModel() {
		AggregatorGraphModel gmodel = new AggregatorGraphModel();
		gmodel.setPresentation(this.getStatisticParameters().getPresentation());
		int countGroup = 0;
		int countBucket = 0;

		for (ObservationSeries obsSeries : observationSeries) {
			if (obsSeries.getObservations() instanceof Group[])
				countGroup++;
			if (obsSeries.getObservations() instanceof Bucket[])
				countBucket++;
		}
		if (countGroup==0 && countBucket==0)return gmodel;
		log.debug("Statistic has {} series and {} observations",this.getStatisticParameters().getSeries().size(),observationSeries.size());
		log.debug("Observation type, GroupType {}, BucketType {}",countGroup, countBucket);


		if (countGroup == observationSeries.size()) {
			log.info("Constructing graph model for Group type");
			//construct the groups from the first series (each series will have the same grouping)
			Observation[] observations = observationSeries.get(0).getObservations();
			Group[] groups = (Group[]) observations;
			for (Group group : groups) {
				gmodel.addGroupLabel(group.getGroupName());
			}
			//now add each series and their values
			for (int i=0; i < observationSeries.size(); i++){
				groups = (Group[]) observationSeries.get(i).getObservations();
				gmodel.getSeriesLabels().add(statisticParameters.getSeries().get(i).getSeriesLabel());

				List<Double> values = new ArrayList<Double>();
				for (Group group : groups) {
					Double valueDouble = new Double(group.getValue());
					values.add(valueDouble);
				}
				log.debug("Adding Values {}",Arrays.toString(values.toArray(new Double[0])));
				gmodel.addGroupValue(values);
			}

		} else if (countBucket==observationSeries.size()) {
			log.info("Constructing graph model for Bucket type");

			//construct the groups from the first series (each series will have the same grouping)
			Observation[] observations = observationSeries.get(0).getObservations();
			Bucket[] buckets = (Bucket[]) observations;
			String pattern ="yyyy-MM-dd HH:mm";
			if (statisticParameters.getPresentation().getDateFormat()!=null)
			    pattern = statisticParameters.getPresentation().getDateFormat();
			DateTimeFormatter startParser = DateTimeFormat.forPattern(pattern);
			DateTimeFormatter endParser = DateTimeFormat.forPattern(pattern);
			for (Bucket bucket : buckets) {
				gmodel.addGroupLabel(startParser.print(bucket.getStart()) + "  " + endParser.print(bucket.getEnd()));
			}

			for (int i=0; i < observationSeries.size(); i++){
				buckets = (Bucket[]) observationSeries.get(i).getObservations();
				gmodel.getSeriesLabels().add(statisticParameters.getSeries().get(i).getSeriesLabel());

				List<Double> values = new ArrayList();
				for (Bucket bucket : buckets) {
					Double valueDouble = new Double(bucket.getValue());
					values.add(valueDouble);
				}
				//log.debug("Adding Values {}",Arrays.toString(values.toArray(new Double[0])));
				gmodel.addGroupValue(values);
			}
		}
		else{
			log.error("Statistic had series with mixed observation types, which is currently not supported");
		}

		return gmodel;
	}

	/**
	 * <p>
	 * pre processing effects the entries that go into the statistical unit post
	 * processing effects the observations that result form the statistical unit
	 * </p>
	 */
	public void postProcess() {
		try {
			if (getPostprocessor() != null) {
				for (StatisticsPostProcessor post : postprocessor) {
					// perform the same post process on each observationseries
					for (ObservationSeries obsSeries : getObservationSeries()) {
						obsSeries.setObservations(post.postProcess(obsSeries.getObservations()));
					}
				}
			}
		} catch (PostprocessorException e) {
			log.error("Could not post process entries, using " + getPostprocessor().getClass());
		}
	}


	protected DateTime startingTime() {
		if (statisticParameters.getStartTimeAsDate() != null)
			return statisticParameters.getStartTimeAsDate();
		DateTime start = (DateTime) this.getEntryHandler().queryUnique("select min(eventTime) from Event");
		return start;
	}

	protected DateTime endingTime() {
		if (statisticParameters.getEndTimeAsDate() != null)
			return statisticParameters.getEndTimeAsDate();
		DateTime end = (DateTime) this.getEntryHandler().queryUnique("select max(eventTime) from Event");
		return end;
	}


	public void setField(String field) {

	}

	public void setPreprocessor(StatisticsPreProcessor preprocessor) {
		this.preprocessor = preprocessor;
	}

	public StatisticsPreProcessor getPreprocessor() {
		return preprocessor;
	}

	public List<StatisticsPostProcessor> getPostprocessor() {
		return postprocessor;
	}

	public void setPostprocessor(List<StatisticsPostProcessor> postprocessor) {
		this.postprocessor = postprocessor;
	}

	public abstract void setStatisticParameters(StatisticParameters statisticParameters) ;


	public StatisticParameters getStatisticParameters() {
		return statisticParameters;
	}

	public EntryHandler getEntryHandler() {
		return entryHandler;
	}

	public void setObservationSeries(List<ObservationSeries> observationSeries) {
		this.observationSeries = observationSeries;
	}

	public List<ObservationSeries> getObservationSeries() {
		return observationSeries;
	}

}
