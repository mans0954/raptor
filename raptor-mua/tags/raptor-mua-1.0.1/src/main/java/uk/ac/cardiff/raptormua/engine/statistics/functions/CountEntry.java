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

package uk.ac.cardiff.raptormua.engine.statistics.functions;

import java.util.List;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import uk.ac.cardiff.model.wsmodel.MethodParameter;
import uk.ac.cardiff.model.wsmodel.MethodParameterNotOfRequiredTypeException;
import uk.ac.cardiff.model.wsmodel.StatisticParameters;
import uk.ac.cardiff.raptormua.engine.statistics.BaseStatistic;
import uk.ac.cardiff.raptormua.engine.statistics.StatisticalUnitException;
import uk.ac.cardiff.raptormua.engine.statistics.records.Bucket;
import uk.ac.cardiff.raptormua.engine.statistics.records.ObservationSeries;

public class CountEntry extends BaseStatistic {

    /** Class logger */
    private final Logger log = LoggerFactory.getLogger(CountEntry.class);

    public Boolean performStatistic(List<MethodParameter> methodParams, String sqlWhere)
            throws StatisticalUnitException {

        if (methodParams.size() != 1)
            throw new StatisticalUnitException("incorrect method parameters");

        String numberOfIntervalsString;
        try {
            numberOfIntervalsString = methodParams.get(0).getValue(String.class);
        } catch (MethodParameterNotOfRequiredTypeException e) {
            throw new StatisticalUnitException(e);
        }

        int numberOfIntervals = Integer.parseInt(numberOfIntervalsString);
        log.debug("Performing countEntry Statistical Operation");
        log.debug("Params for method:  {}, {} ", this.getClass().getSimpleName(), statisticParameters.getUnitName());

        /* divide the temporal extent into evenly sized buckets */
        DateTime start = startingTime();
        DateTime end = endingTime();
        log.debug("countEntry between [start:{}] [end:{}]", start, end);
        long difference = end.getMillis() - start.getMillis();
        log.debug("There is " + difference + "ms difference between start and end entries");
        long timeIntervalsInMs = (long) (difference / numberOfIntervals);
        long reminder = difference % numberOfIntervals;
        log.debug("There are " + numberOfIntervals + " buckets, with reminder " + reminder + "ms");

        if (difference < 0) {
            log.error("Possible statistical parameter error, negative time difference, try swapping the start and end times");
            throw new StatisticalUnitException("negative time difference");
        }

        String resourceCategoryFilter = statisticParameters.getResourceCategory().getSql();
        log.debug("Resource Category Filter {}", resourceCategoryFilter);

        /* now create that many buckets of length timeIntervalInt */
        Bucket[] buckets = null;
        if (reminder > 0)
            buckets = new Bucket[numberOfIntervals + 1]; // add 1 for the
                                                         // reminder
        else
            buckets = new Bucket[numberOfIntervals];

        buckets[0] = new Bucket();
        buckets[0].setStart(start);
        buckets[0].setEnd(new DateTime(start.getMillis() + timeIntervalsInMs));
        DateTime endOfEvenBuckets = null;
        for (int i = 1; i < numberOfIntervals; i++) {
            Bucket buck = new Bucket();
            buck.setStart(buckets[i - 1].getEnd());
            buck.setEnd(new DateTime(buckets[i - 1].getEnd().getMillis() + timeIntervalsInMs));
            buckets[i] = buck;
            endOfEvenBuckets = buck.getEnd();
        }

        /*
         * there may only be a reminder if the time frame specified is not big enough to fit the interval specified, in
         * this case the endOfEvenBuckets has not been set, and it needs setting to the current starttime.
         */
        boolean hasOnlyReminder = false;
        if (endOfEvenBuckets == null) {
            hasOnlyReminder = true;
            endOfEvenBuckets = start;
        }

        if (reminder > 0) {
            /*
             * now do something with the reminder, create a bucket from the last entry, to the maximum temporal extent
             * of all entries
             */
            Bucket reminderBucket = new Bucket();
            reminderBucket.setStart(endOfEvenBuckets);
            if (!hasOnlyReminder)
                reminderBucket.setEnd(end);
            else
                reminderBucket.setEnd(new DateTime(start.getMillis() + end.getMillis()));
            buckets[buckets.length - 1] = reminderBucket;
        }

        long testCount = 0;
        for (Bucket bucket : buckets) {
            /*
             * SQL between is >= start && <= end. We want, >= start && < end, so must exclude equals end
             */
            String tableName = statisticParameters.getEventType().getHibernateSimpleClassName();
            String query = "";
            if (sqlWhere.equals(""))
                query =
                        "select count(*) from " + tableName
                                + " where (eventTime between ? and ?) and (eventTime !=?) and resourceIdCategory "
                                + resourceCategoryFilter;
            else
                query =
                        "select count(*) from " + tableName
                                + " where (eventTime between ? and ?) and (eventTime !=?) and resourceIdCategory "
                                + resourceCategoryFilter + " and " + sqlWhere;

            Object[] params = new Object[] {bucket.getStart(), bucket.getEnd(), bucket.getEnd()};
            Long count = (Long) this.getEventHandler().queryUnique(query, params);
            bucket.setValue(count);
            testCount += bucket.getValue();
        }

        /*
         * test count should equal the number of entries unless there is a reminder, or the specified start time and
         * endtime does not completely contain the entries.
         */
        log.debug("Events: " + this.getEventHandler().getNumberOfEvents() + ", total in buckets: " + testCount);

        ObservationSeries series = new ObservationSeries();
        series.setObservations(buckets);
        getObservationSeries().add(series);

        return true;

    }

    @Override
    public void setStatisticParameters(StatisticParameters statisticParameters) {
        List<MethodParameter> methodParams = statisticParameters.getMethodParams();
        if (methodParams.size() == 1) {
            methodParams.get(0).setParameterName("Number of Intervals");
            methodParams.get(0).setParameterType(MethodParameter.ParameterType.VALUE);
        } else {
            log.error("Unable to set parameter type for statistic {}, incorrect number of parameters", this.getClass()
                    .getSimpleName());
        }
        this.statisticParameters = statisticParameters;

    }

}
