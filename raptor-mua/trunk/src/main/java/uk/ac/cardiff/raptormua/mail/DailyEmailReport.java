
package uk.ac.cardiff.raptormua.mail;

import java.util.HashMap;
import java.util.Map;

import org.joda.time.DateTime;

import uk.ac.cardiff.raptormua.engine.Version;
import uk.ac.cardiff.raptormua.engine.runtimestatistics.PersistentEventStorageStatistics;
import uk.ac.cardiff.raptormua.engine.runtimestatistics.RuntimeEventStorageStatistics;

public class DailyEmailReport extends AbstractEmailReport {

    private PersistentEventStorageStatistics persistentStorageStatistics;

    private RuntimeEventStorageStatistics runtimeStorageStatistics;

    public Map<String, Object> generateModel() {
        Map<String, Object> model = new HashMap<String, Object>();

        DateTime now = new DateTime(System.currentTimeMillis());
        model.put("today", now.toString());
        model.put("todayEventStorageStats", persistentStorageStatistics.computePeriodStatistic("todayStatisticsPeriod"));
        model.put("thisYearEventStorageStats",
                persistentStorageStatistics.computePeriodStatistic("thisYearStatisticPeriod"));
        model.put("yesterdayEventStorageStats",
                persistentStorageStatistics.computePeriodStatistic("yesterdayStatisticPeriod"));
        model.put("lastWeekEventStorageStats", runtimeStorageStatistics.getTotalEventsStored());

        model.put("eventsSinceStarted", persistentStorageStatistics.computePeriodStatistic("thisWeekStatisticPeriod"));

        String version = "Unknown";
        try {
            version = Version.getMajorVersion() + "." + Version.getMinorVersion() + "." + Version.getMicroVersion();
        } catch (Throwable e) {

        }

        model.put("muaVersion", version);

        model.put("muaUptime", runtimeStorageStatistics.getUptime());
        return model;
    }

    /**
     * @param persistentStorageStatistics the persistentStorageStatistics to set
     */
    public void setPersistentStorageStatistics(PersistentEventStorageStatistics persistentStorageStatistics) {
        this.persistentStorageStatistics = persistentStorageStatistics;
    }

    /**
     * @return the persistentStorageStatistics
     */
    public PersistentEventStorageStatistics getPersistentStorageStatistics() {
        return persistentStorageStatistics;
    }

    /**
     * @param runtimeStorageStatistics the runtimeStorageStatistics to set
     */
    public void setRuntimeStorageStatistics(RuntimeEventStorageStatistics runtimeStorageStatistics) {
        this.runtimeStorageStatistics = runtimeStorageStatistics;
    }

    /**
     * @return the runtimeStorageStatistics
     */
    public RuntimeEventStorageStatistics getRuntimeStorageStatistics() {
        return runtimeStorageStatistics;
    }

}
