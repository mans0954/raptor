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

    public CurrentTimeRanges() {
        computeTimeRanges();
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
        } else {
            throw new NoSuchTimeRangeException("Requested a time period that does not exists [" + period + "]");
        }
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

        startMonth = oneMonthPrevious;
        startToday = today;
        startWeek = oneWeekPrevious;
        startYear = oneYearPrevious;
        currentTime = currentDateTime;

        log.debug("Start Page, TODAY [start:{}] [end:{}]", today, currentDateTime);
        log.debug("Start Page, LASTWEEK [start:{}] [end:{}]", oneWeekPrevious, currentDateTime);
        log.debug("Start Page, LASTMONTH [start:{}] [end:{}]", oneMonthPrevious, currentDateTime);
        log.debug("Start Page, LASTYEAR [start:{}] [end:{}]", oneYearPrevious, currentDateTime);

    }

}