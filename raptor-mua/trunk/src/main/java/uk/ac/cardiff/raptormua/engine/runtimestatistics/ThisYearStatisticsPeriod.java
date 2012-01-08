
package uk.ac.cardiff.raptormua.engine.runtimestatistics;

import org.joda.time.DateTime;

public class ThisYearStatisticsPeriod implements StatisticPeriod {

    public DateTime getStart() {
        DateTime start = new DateTime(System.currentTimeMillis());
        start = start.minusHours(start.getHourOfDay());
        start = start.minusMinutes(start.getMinuteOfHour());
        start = start.minusSeconds(start.getSecondOfMinute());
        start = start.minusDays(start.getDayOfYear());
        start = start.minusMillis(start.getMillisOfSecond());
        return start;
    }

    public DateTime getEnd() {
        return new DateTime(System.currentTimeMillis());
    }

    public String getPeriodIdentifier() {
        return "thisYearStatisticPeriod";
    }

}
