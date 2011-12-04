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

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import uk.ac.cardiff.model.report.AggregatorGraphModel;
import uk.ac.cardiff.model.wsmodel.MethodParameter;
import uk.ac.cardiff.model.wsmodel.ProcessorInformation;
import uk.ac.cardiff.model.wsmodel.StatisticParameters;
import uk.ac.cardiff.raptor.store.QueryableEventHandler;
import uk.ac.cardiff.raptormua.engine.statistics.processor.PostprocessorException;
import uk.ac.cardiff.raptormua.engine.statistics.records.Bucket;
import uk.ac.cardiff.raptormua.engine.statistics.records.Group;
import uk.ac.cardiff.raptormua.engine.statistics.records.Observation;
import uk.ac.cardiff.raptormua.engine.statistics.records.ObservationSeries;

/**
 * Holds a statistics unit or one statistics operation on one piece of data
 */
public abstract class BaseStatistic {

    /** Class logger */
    private final Logger log = LoggerFactory.getLogger(BaseStatistic.class);

    /**
     * The <code>QueryableEventHandler</code> that allows access to all <code>Event</code>s this statistic works off.
     */
    private QueryableEventHandler eventHandler;

    /** The parameters used to configure this statistic. */
    protected StatisticParameters statisticParameters;

    /** A list of postprocessing modules. */
    private List<StatisticPostProcessor> postprocessor;

    /**
     * A list of processors that needs to be instantiated for this class (during initialization of the MUA, or
     * dynamically).
     */
    private List<ProcessorInformation> attachProcessors;

    /**
     * After each statistic has been invoked, the results of each series are stored as <code>ObservationSeries</code> in
     * this list.
     */
    private List<ObservationSeries> observationSeries;

    /**
     * Default constructor.
     */
    public BaseStatistic() {
        setObservationSeries(new ArrayList<ObservationSeries>());
    }

    /**
     * Method that performs the statistical operation. Overridden by each concrete implementation statistic class.
     * 
     * @param methodParams
     * @param sqlWhere essentially a filter that is applied to stored events before the statistic is performed.
     * @return
     * @throws StatisticalUnitException
     */
    public abstract Boolean performStatistic(List<MethodParameter> methodParams, String sqlWhere)
            throws StatisticalUnitException;

    /**
     * Important this this method is called during the statistic process lifecycle, so that any state variables e.g. the
     * observationSeries, is reset ready for the next invocation
     */
    public void reset() {
        observationSeries.clear();
    }

    public void setEntryHandler(QueryableEventHandler entryHandler) {
        this.eventHandler = entryHandler;
    }

    /**
     * <p>
     * construct a graph model from the data observations and groupings stored in the buckets
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
        if (countGroup == 0 && countBucket == 0)
            return gmodel;
        log.debug("Statistic has {} series and {} observations", this.getStatisticParameters().getSeries().size(),
                observationSeries.size());
        log.debug("Observation type, GroupType {}, BucketType {}", countGroup, countBucket);

        if (countGroup == observationSeries.size()) {
            log.info("Constructing graph model for Group type");
            // construct the groups from the series with the most groups
            Observation[] observations = getObservationWithMostGroups(observationSeries);
            Group[] groups = (Group[]) observations;
            log.trace("Has {} groups", groups.length);
            for (Group group : groups) {
                log.trace("Group {}", group.getGroupName());
                gmodel.addGroupLabel(group.getGroupName());
            }

            // now add each series and their values
            for (int i = 0; i < observationSeries.size(); i++) {
                groups = (Group[]) observationSeries.get(i).getObservations();
                gmodel.getSeriesLabels().add(statisticParameters.getSeries().get(i).getSeriesLabel());
                List<Double> values = new ArrayList<Double>();
                for (String label : gmodel.getGroupLabels()) {
                    Double valueDouble = new Double(0);
                    for (Group group : groups) {
                        if (group.getGroupName().equals(label))
                            valueDouble = new Double(group.getValue());
                    }
                    values.add(valueDouble);
                }

                log.trace("Adding Values {}", Arrays.toString(values.toArray(new Double[0])));
                gmodel.addGroupValue(values);
            }

        } else if (countBucket == observationSeries.size()) {
            log.info("Constructing graph model for Bucket type");

            // construct the groups from the first series (each series will have the same grouping)
            Observation[] observations = observationSeries.get(0).getObservations();
            Bucket[] buckets = (Bucket[]) observations;
            String pattern = "yyyy-MM-dd HH:mm";
            if (statisticParameters.getPresentation().getDateFormat() != null)
                pattern = statisticParameters.getPresentation().getDateFormat();
            DateTimeFormatter startParser = DateTimeFormat.forPattern(pattern);
            DateTimeFormatter endParser = DateTimeFormat.forPattern(pattern);
            for (Bucket bucket : buckets) {
                gmodel.addGroupLabel(startParser.print(bucket.getStart()) + "  " + endParser.print(bucket.getEnd()));
            }
            // Buckets are time series, and so are already sorted chronologically.
            for (int i = 0; i < observationSeries.size(); i++) {
                buckets = (Bucket[]) observationSeries.get(i).getObservations();
                gmodel.getSeriesLabels().add(statisticParameters.getSeries().get(i).getSeriesLabel());

                List<Double> values = new ArrayList();
                for (Bucket bucket : buckets) {
                    Double valueDouble = new Double(bucket.getValue());
                    values.add(valueDouble);
                }
                log.trace("Adding Values {}", Arrays.toString(values.toArray(new Double[0])));
                gmodel.addGroupValue(values);
            }
        } else {
            log.error("Statistic had series with mixed observation types, which is currently not supported");
        }

        return gmodel;
    }

    private Observation[] getObservationWithMostGroups(List<ObservationSeries> observationSeries) {
        Observation[] maxObs = null;
        int max = 0;
        for (ObservationSeries series : observationSeries) {
            Observation[] obs = series.getObservations();
            if (obs.length > max) {
                max = obs.length;
                maxObs = obs;
            }

        }
        return maxObs;
    }

    /**
     * <p>
     * Send the results of performing the statistic to the post processors.
     * </p>
     */
    public void postProcess() {
        try {
            if (getPostprocessor() != null) {
                log.info("There are {} post processors to apply, these are {}", getPostprocessor().size(),
                        Arrays.toString(getPostprocessor().toArray(new StatisticPostProcessor[0])));
                for (StatisticPostProcessor post : postprocessor) {
                    for (ObservationSeries obsSeries : getObservationSeries()) {
                        obsSeries.setObservations(post.process(obsSeries.getObservations()));
                    }
                }
            } else {
                log.info("There are 0 post processors to apply");
            }
        } catch (PostprocessorException e) {
            log.error("Could not post process entries, using {}", getPostprocessor().getClass());
        }
    }

    protected DateTime startingTime() {
        if (statisticParameters.getStartTimeAsDate() != null)
            return statisticParameters.getStartTimeAsDate();
        DateTime start = (DateTime) this.getEventHandler().queryUnique("select min(eventTime) from Event", null);
        return start;
    }

    protected DateTime endingTime() {
        if (statisticParameters.getEndTimeAsDate() != null)
            return statisticParameters.getEndTimeAsDate();
        DateTime end = (DateTime) this.getEventHandler().queryUnique("select max(eventTime) from Event", null);
        return end;
    }

    public void setField(String field) {

    }

    public List<StatisticPostProcessor> getPostprocessor() {
        return postprocessor;
    }

    public void setPostprocessor(List<StatisticPostProcessor> postprocessor) {
        this.postprocessor = postprocessor;
    }

    public abstract void setStatisticParameters(StatisticParameters statisticParameters);

    public StatisticParameters getStatisticParameters() {
        return statisticParameters;
    }

    public QueryableEventHandler getEventHandler() {
        return eventHandler;
    }

    public void setObservationSeries(List<ObservationSeries> observationSeries) {
        this.observationSeries = observationSeries;
    }

    public List<ObservationSeries> getObservationSeries() {
        return observationSeries;
    }

    /**
     * @param attachProcessors the attachProcessors to set
     */
    public void setAttachProcessors(List<ProcessorInformation> attachProcessors) {
        this.attachProcessors = attachProcessors;
    }

    /**
     * @return the attachProcessors
     */
    public List<ProcessorInformation> getAttachProcessors() {
        return attachProcessors;
    }

}
