
package uk.ac.cardiff.raptormua.engine.runtimestatistics;

import org.joda.time.DateTime;

public class TodayStatisticsPeriod implements StatisticPeriod {

    public DateTime getStart() {
        DateTime now = new DateTime(System.currentTimeMillis());
        now = now.minusHours(now.getHourOfDay());
        now = now.minusMinutes(now.getMinuteOfHour());
        now = now.minusSeconds(now.getSecondOfMinute());
        return now;
    }

    public DateTime getEnd() {
        return new DateTime(System.currentTimeMillis());
    }

    public String getPeriodIdentifier() {
        return "todayStatisticsPeriod";
    }

}
