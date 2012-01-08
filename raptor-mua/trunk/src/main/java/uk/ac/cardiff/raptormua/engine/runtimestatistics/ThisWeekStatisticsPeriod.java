
package uk.ac.cardiff.raptormua.engine.runtimestatistics;

import org.joda.time.DateTime;

public class ThisWeekStatisticsPeriod implements StatisticPeriod {

    public DateTime getStart() {
        DateTime now = new DateTime(System.currentTimeMillis());
        now = now.minusHours(now.getHourOfDay());
        now = now.minusMinutes(now.getMinuteOfHour());
        now = now.minusSeconds(now.getSecondOfMinute());
        now = now.minusMillis(now.getMillisOfSecond());
        now = now.minusDays(now.getDayOfWeek() - 1);
        return now;
    }

    public DateTime getEnd() {
        return new DateTime(System.currentTimeMillis());
    }

    public String getPeriodIdentifier() {
        return "thisWeekStatisticPeriod";
    }

}
