
package uk.ac.cardiff.raptormua.jmx;

import org.springframework.jmx.export.annotation.ManagedAttribute;
import org.springframework.jmx.export.annotation.ManagedResource;

import uk.ac.cardiff.raptormua.engine.runtimestatistics.PersistentEventStorageStatistics;

@ManagedResource(objectName = "uk.ac.cardiff.raptor:name=persistentEventStorageStatistics",
        description = "Persistent event storage statistics mbean")
public class PersistentEventStorageStatisticsJmx {

    /**
     * Class that stores the statistics this MBean uses to report.
     */
    private PersistentEventStorageStatistics eventStorageStatistics;

    @ManagedAttribute
    public String getEventsStored() {
        return eventStorageStatistics.getAllPeriodStatistics();
    }

    /**
     * @param eventStorageStatistics the eventStorageStatistics to set
     */
    public void setEventStorageStatistics(PersistentEventStorageStatistics eventStorageStatistics) {
        this.eventStorageStatistics = eventStorageStatistics;
    }

    /**
     * @return the eventStorageStatistics
     */
    public PersistentEventStorageStatistics getEventStorageStatistics() {
        return eventStorageStatistics;
    }

}
