
package uk.ac.cardiff.raptormua.engine.runtimestatistics;

import org.joda.time.DateTime;

public class YesterdayStatisticsPeriod implements StatisticPeriod {

    public DateTime getStart() {
        DateTime now = new DateTime(System.currentTimeMillis());
        now = now.minusHours(now.getHourOfDay());
        now = now.minusMinutes(now.getMinuteOfHour());
        now = now.minusSeconds(now.getSecondOfMinute());
        now = now.minusMillis(now.getMillisOfSecond());
        now = now.minusDays(1);
        return now;
    }

    public DateTime getEnd() {
        DateTime now = new DateTime(System.currentTimeMillis());
        now = now.minusHours(now.getHourOfDay());
        now = now.minusMinutes(now.getMinuteOfHour());
        now = now.minusSeconds(now.getSecondOfMinute());
        now = now.minusMillis(now.getMillisOfSecond());
        return now;
    }

    public String getPeriodIdentifier() {
        return "yesterdayStatisticPeriod";
    }

}
