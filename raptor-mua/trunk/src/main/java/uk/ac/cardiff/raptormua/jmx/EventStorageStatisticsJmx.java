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
