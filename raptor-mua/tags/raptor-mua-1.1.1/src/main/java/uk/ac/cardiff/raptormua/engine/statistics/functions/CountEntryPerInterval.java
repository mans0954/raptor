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
import uk.ac.cardiff.model.wsmodel.MethodParameter.ParameterType;
import uk.ac.cardiff.model.wsmodel.MethodParameterNotOfRequiredTypeException;
import uk.ac.cardiff.model.wsmodel.StatisticParameters;
import uk.ac.cardiff.raptormua.engine.statistics.BaseStatistic;
import uk.ac.cardiff.raptormua.engine.statistics.StatisticalUnitException;
import uk.ac.cardiff.raptormua.engine.statistics.records.Bucket;
import uk.ac.cardiff.raptormua.engine.statistics.records.ObservationSeries;

public class CountEntryPerInterval extends BaseStatistic {

    static Logger log = LoggerFactory.getLogger(CountEntryPerInterval.class);

    /**
     * <p>
     * returns false if semantic error with the entries, throws an exception on code failure
     * </p>
     * 
     * @param timeInterval - assumes a String representing minutes
     * @param sqlWhere if a SQL filter has been attached to the statistical unit, it is inputed to the method if there
     *            is not SQL filter, it comes through as an empty string (not null)
     * @return
     * @throws StatisticalUnitException
     */
    public Boolean performStatistic(List<MethodParameter> methodParams, String sqlWhere)
            throws StatisticalUnitException {

        if (methodParams.size() != 1)
            throw new StatisticalUnitException("incorrect method parameters");

        String timeInterval;
        try {
            timeInterval = methodParams.get(0).getValue(String.class);
        } catch (MethodParameterNotOfRequiredTypeException e) {
            throw new StatisticalUnitException(e);
        }

        log.debug("Performing countEntryPerInterval Statistical Operation");
        int timeIntervalInt = Integer.parseInt(timeInterval);
        // convert minutes to ms for the procedure
        timeIntervalInt = timeIntervalInt * 60 * 1000;
        log.debug("Params for method:  {}, {}. SQL Filter {}", new Object[] {this.getClass().getSimpleName(),
                statisticParameters.getUnitName(), sqlWhere});

        /* divide the temporal extent into evenly sized buckets */
        DateTime start = startingTime();
        DateTime end = endingTime();
        log.debug("countEntryPerInterval between [start:{}] [end:{}]", start, end);
        long difference = end.getMillis() - start.getMillis();
        log.debug("There is " + difference + "ms difference between start and end entries");
        int numberOfBuckets = (int) (difference / timeIntervalInt);
        long reminder = difference % timeIntervalInt;
        log.debug("There are " + numberOfBuckets + " buckets, with reminder " + reminder + "ms");

        if (difference < 0) {
            log.error("Possible statistical parameter error, negative time difference, try swapping the start and end times");
            throw new StatisticalUnitException("negative time difference");
        }

        String resourceCategoryFilter = statisticParameters.getResourceCategory().getSql();
        log.debug("Resource Category Filter {}", resourceCategoryFilter);

        /* now create that many buckets of length timeIntervalInt */
        Bucket[] buckets = null;
        if (reminder > 0)
            buckets = new Bucket[numberOfBuckets + 1]; // add 1 for the reminder
        else
            buckets = new Bucket[numberOfBuckets];

        buckets[0] = new Bucket();
        buckets[0].setStart(start);
        buckets[0].setEnd(new DateTime(start.getMillis() + timeIntervalInt));
        DateTime endOfEvenBuckets = null;
        for (int i = 1; i < numberOfBuckets; i++) {
            Bucket buck = new Bucket();
            buck.setStart(buckets[i - 1].getEnd());
            buck.setEnd(new DateTime(buckets[i - 1].getEnd().getMillis() + timeIntervalInt));
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
        String tableName = statisticParameters.getEventType();
        for (Bucket bucket : buckets) {
            /*
             * SQL between is >= start && <= end. We want, >= start && < end, so must exclude equals end
             */
            String query = null;
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

        log.debug("Entries: " + this.getEventHandler().getNumberOfEvents() + ", total in buckets: " + testCount);

        ObservationSeries series = new ObservationSeries();
        series.setObservations(buckets);
        getObservationSeries().add(series);

        // finished successfully, no exception thrown
        return true;

    }

    @Override
    public void setStatisticParameters(StatisticParameters statisticParameters) {
        List<MethodParameter> methodParams = statisticParameters.getMethodParams();
        if (methodParams.size() == 1) {
            methodParams.get(0).setParameterName("Time Interval (minutes)");
            methodParams.get(0).setParameterType(ParameterType.VALUE);
        } else {
            log.error("Unable to set parameter type for statistic {}, incorrect number of parameters", this.getClass()
                    .getSimpleName());
        }
        this.statisticParameters = statisticParameters;

    }

}
