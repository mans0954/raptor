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
