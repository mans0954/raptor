
package uk.ac.cardiff.raptormua.jmx;

import java.util.Map;

import org.springframework.jmx.export.annotation.ManagedAttribute;
import org.springframework.jmx.export.annotation.ManagedResource;

import uk.ac.cardiff.raptormua.engine.Version;
import uk.ac.cardiff.raptormua.engine.runtimestatistics.RuntimeEventStorageStatistics;

@ManagedResource(objectName = "uk.ac.cardiff.raptor:name=runtimeEventStorageStatistics",
        description = "Runtime event storage statistics mbean")
public class EventStorageStatisticsJmx {

    /**
     * Class that stores the statistics this MBean uses to report.
     */
    private RuntimeEventStorageStatistics eventStorageStatistics;

    @ManagedAttribute
    public String getVersion() {
        return Version.getMajorVersion() + "." + Version.getMinorVersion() + "." + Version.getMicroVersion();
    }

    @ManagedAttribute
    public String getRuntimeEventsStored() {
        StringBuilder eventsStoredString = new StringBuilder();
        for (Map.Entry<String, Long> entry : eventStorageStatistics.getEventsStored().entrySet()) {
            eventsStoredString.append("[");
            eventsStoredString.append(entry.getKey());
            eventsStoredString.append(",");
            eventsStoredString.append(entry.getValue());
            eventsStoredString.append("]");
            eventsStoredString.append(System.getProperty("line.separator"));
        }

        return eventsStoredString.toString();
    }

    @ManagedAttribute
    public String getRuntimeEventsStoredFrom() {
        return eventStorageStatistics.getRecordedFrom().toString();
    }

    /**
     * @param eventStorageStatistics the eventStorageStatistics to set
     */
    public void setEventStorageStatistics(RuntimeEventStorageStatistics eventStorageStatistics) {
        this.eventStorageStatistics = eventStorageStatistics;
    }

}
