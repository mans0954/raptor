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

package uk.ac.cardiff.raptorweb.model;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import uk.ac.cardiff.raptorweb.model.dashboard.DashboardStatisticsSet.TimeRange;

public class CurrentTimeRanges {

    /** Class logger */
    private final Logger log = LoggerFactory.getLogger(CurrentTimeRanges.class);

    public DateTime currentTime;

    public DateTime startToday;

    public DateTime startWeek;

    public DateTime startMonth;

    public DateTime startYear;

    public DateTime startLastMonth;

    public DateTime endLastMonth;

    public DateTime startSconulYear;

    public DateTime endSconulYear;

    @Deprecated
    public CurrentTimeRanges() {
        computeTimeRanges();
    }

    /**
     * if forceDayBoundaries is true, then computes start times from the beginning of days and end times from the end of
     * days.
     * 
     * Constructor.
     * 
     * @param forceDayStart
     */
    public CurrentTimeRanges(boolean forceDayBoundaries) {
        if (forceDayBoundaries) {
            computeTimeRangesForceDayBoundaries();
        } else {
            computeTimeRanges();
        }
    }

    public DateTime getStartTime(TimeRange period) throws NoSuchTimeRangeException {
        if (period == TimeRange.TODAY) {
            return startToday;
        } else if (period == TimeRange.LASTWEEK) {
            return startWeek;
        } else if (period == TimeRange.LASTMONTH) {
            return startMonth;
        } else if (period == TimeRange.LASTYEAR) {
            return startYear;
        } else if (period == TimeRange.PREVIOUSMONTH) {
            return startLastMonth;
        } else if (period == TimeRange.SCONULYEAR) {
            return startSconulYear;
        } else {
            throw new NoSuchTimeRangeException("Requested a time period that does not exists [" + period + "]");
        }

    }

    /**
     * Returns the end time, which is always the current time;
     * 
     * @param period
     * @return
     * @throws NoSuchTimeRangeException
     */
    public DateTime getEndTime(TimeRange period) throws NoSuchTimeRangeException {
        if (period == TimeRange.TODAY) {
            return currentTime;
        } else if (period == TimeRange.LASTWEEK) {
            return currentTime;
        } else if (period == TimeRange.LASTMONTH) {
            return currentTime;
        } else if (period == TimeRange.LASTYEAR) {
            return currentTime;
        } else if (period == TimeRange.PREVIOUSMONTH) {
            return endLastMonth;
        } else if (period == TimeRange.SCONULYEAR) {
            return endSconulYear;
        } else {
            throw new NoSuchTimeRangeException("Requested a time period that does not exists [" + period + "]");
        }
    }

    private void computeTimeRangesForceDayBoundaries() {
        long currentTimeInMS = System.currentTimeMillis();
        DateTime currentDateTime = new DateTime(currentTimeInMS);
        currentDateTime = currentDateTime.plusDays(1);
        currentDateTime = currentDateTime.minusHours(currentDateTime.getHourOfDay());
        currentDateTime = currentDateTime.minusMinutes(currentDateTime.getMinuteOfHour());
        currentDateTime = currentDateTime.minusSeconds(currentDateTime.getSecondOfMinute());

        DateTime today = new DateTime(currentTimeInMS);
        today = today.minusHours(today.getHourOfDay());
        today = today.minusMinutes(today.getMinuteOfHour());
        today = today.minusSeconds(today.getSecondOfMinute());

        DateTime oneMonthPrevious = currentDateTime.minusMonths(1);
        oneMonthPrevious = oneMonthPrevious.minusHours(oneMonthPrevious.getHourOfDay());
        oneMonthPrevious = oneMonthPrevious.minusMinutes(oneMonthPrevious.getMinuteOfHour());
        oneMonthPrevious = oneMonthPrevious.minusSeconds(oneMonthPrevious.getSecondOfMinute());

        DateTime oneYearPrevious = currentDateTime.minusYears(1);
        oneYearPrevious = oneYearPrevious.minusHours(oneYearPrevious.getHourOfDay());
        oneYearPrevious = oneYearPrevious.minusMinutes(oneYearPrevious.getMinuteOfHour());
        oneYearPrevious = oneYearPrevious.minusSeconds(oneYearPrevious.getSecondOfMinute());

        DateTime oneWeekPrevious = currentDateTime.minusWeeks(1);
        oneWeekPrevious = oneWeekPrevious.minusHours(oneWeekPrevious.getHourOfDay());
        oneWeekPrevious = oneWeekPrevious.minusMinutes(oneWeekPrevious.getMinuteOfHour());
        oneWeekPrevious = oneWeekPrevious.minusSeconds(oneWeekPrevious.getSecondOfMinute());

        DateTime lastMonthStart = new DateTime().minusMonths(1).dayOfMonth().withMinimumValue();
        DateTime lastMonthEnd = new DateTime().minusMonths(1).dayOfMonth().withMaximumValue();
        lastMonthStart = lastMonthStart.minusMillis(lastMonthStart.getMillisOfDay());
        lastMonthEnd =
                lastMonthEnd.withHourOfDay(23).withMinuteOfHour(59).withSecondOfMinute(59).withMillisOfSecond(999);

        DateTime previousAugust = new DateTime();
        if (previousAugust.getMonthOfYear() < 8) {
            previousAugust = previousAugust.withYear(previousAugust.getYear() - 1);
        }
        previousAugust = previousAugust.withMonthOfYear(8);
        previousAugust = previousAugust.dayOfMonth().withMinimumValue().minusMillis(previousAugust.getMillisOfDay());

        DateTime endJuly = new DateTime();
        if (endJuly.getMonthOfYear() > 7) {
            endJuly = endJuly.withYear(endJuly.getYear() + 1);
        }
        endJuly = endJuly.withMonthOfYear(7);
        endJuly =
                endJuly.dayOfMonth().withMaximumValue().withHourOfDay(23).withMinuteOfHour(59).withSecondOfMinute(59)
                        .withMillisOfSecond(999);

        startMonth = oneMonthPrevious;
        startToday = today;
        startWeek = oneWeekPrevious;
        startYear = oneYearPrevious;
        currentTime = currentDateTime;
        endLastMonth = lastMonthEnd;
        startLastMonth = lastMonthStart;

        startSconulYear = previousAugust; // aug 1st,
        endSconulYear = endJuly; // july 31st

        log.debug("Ignore time, TODAY [start:{}] [end:{}]", today, currentDateTime);
        log.debug("Ignore time, LASTWEEK [start:{}] [end:{}]", oneWeekPrevious, currentDateTime);
        log.debug("Ignore time, LASTMONTH [start:{}] [end:{}]", oneMonthPrevious, currentDateTime);
        log.debug("Ignore time, LASTYEAR [start:{}] [end:{}]", oneYearPrevious, currentDateTime);
        log.debug("Ignore time, PREVIOUSYEAR [start:{}] [end:{}]", startLastMonth, endLastMonth);
        log.debug("Ignore time, SCONULYEAR [start:{}] [end:{}]", startLastMonth, endLastMonth);

    }

    private void computeTimeRanges() {
        long currentTimeInMS = System.currentTimeMillis();
        DateTime currentDateTime = new DateTime(currentTimeInMS);
        DateTime today = new DateTime(currentTimeInMS);
        today = today.minusHours(today.getHourOfDay());
        today = today.minusMinutes(today.getMinuteOfHour());
        today = today.minusSeconds(today.getSecondOfMinute());
        DateTime oneMonthPrevious = currentDateTime.minusMonths(1);
        DateTime oneYearPrevious = currentDateTime.minusYears(1);
        DateTime oneWeekPrevious = currentDateTime.minusWeeks(1);

        DateTime lastMonthStart = new DateTime().minusMonths(1).dayOfMonth().withMinimumValue();
        DateTime lastMonthEnd = new DateTime().minusMonths(1).dayOfMonth().withMaximumValue();
        lastMonthStart = lastMonthStart.minusMillis(lastMonthStart.getMillisOfDay());

        DateTime previousAugust = new DateTime();
        if (previousAugust.getMonthOfYear() < 8) {
            previousAugust = previousAugust.withYear(previousAugust.getYear() - 1);
        }
        previousAugust = previousAugust.withMonthOfYear(8);
        previousAugust = previousAugust.dayOfMonth().withMinimumValue();

        DateTime endJuly = new DateTime();
        if (endJuly.getMonthOfYear() > 7) {
            endJuly = endJuly.withYear(endJuly.getYear() + 1);
        }
        endJuly = endJuly.withMonthOfYear(7);
        endJuly = endJuly.dayOfMonth().withMaximumValue();

        startMonth = oneMonthPrevious;
        startToday = today;
        startWeek = oneWeekPrevious;
        startYear = oneYearPrevious;
        currentTime = currentDateTime;
        endLastMonth = lastMonthEnd;
        startLastMonth = lastMonthStart;
        startSconulYear = previousAugust;
        endSconulYear = endJuly;

        log.debug("TODAY [start:{}] [end:{}]", today, currentDateTime);
        log.debug("LASTWEEK [start:{}] [end:{}]", oneWeekPrevious, currentDateTime);
        log.debug("LASTMONTH [start:{}] [end:{}]", oneMonthPrevious, currentDateTime);
        log.debug("LASTYEAR [start:{}] [end:{}]", oneYearPrevious, currentDateTime);
        log.debug("PREVIOUSYEAR [start:{}] [end:{}]", startLastMonth, endLastMonth);
        log.debug("SCONULYEAR [start:{}] [end:{}]", startLastMonth, endLastMonth);

    }

    public static void main(String args[]) {
        DateTime previousAugust = new DateTime();
        if (previousAugust.getMonthOfYear() < 8) {
            previousAugust = previousAugust.withYear(previousAugust.getYear() - 1);
        }
        previousAugust = previousAugust.withMonthOfYear(8);
        previousAugust = previousAugust.dayOfMonth().withMinimumValue().minusMillis(previousAugust.getMillisOfDay());

        DateTime endJuly = new DateTime();
        if (endJuly.getMonthOfYear() > 7) {
            endJuly = endJuly.withYear(endJuly.getYear() + 1);
        }
        endJuly = endJuly.withMonthOfYear(7);
        endJuly =
                endJuly.dayOfMonth().withMaximumValue().withHourOfDay(23).withMinuteOfHour(59).withSecondOfMinute(59)
                        .withMillisOfSecond(999);
        System.out.println("Start " + previousAugust + "  End " + endJuly);

    }

}
