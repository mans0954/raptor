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

package uk.ac.cardiff.raptormua.engine.runtimestatistics;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.time.DurationFormatUtils;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import uk.ac.cardiff.model.event.Event;

/**
 * 
 * Stores the runtime event storage count. This only reports the number of stored events since this MUA has been
 * running. It does not show the event storage count from persistent storage - which is may be accurate.
 * 
 */
public class RuntimeEventStorageStatistics extends BaseExecutionStatistics {

    /** class logger */
    private final Logger log = LoggerFactory.getLogger(RuntimeEventStorageStatistics.class);

    /** The total events stored. */
    private long totalEventsStored;

    /** The count of stored events per event type. */
    private Map<String, Long> eventsStored = new HashMap<String, Long>();

    /**
     * Stores the date and time when this class was instantiated. Hence stores the date and time when this class started
     * recording stats.
     */
    private DateTime recordedFrom;

    private RuntimeEventStorageStatistics() {
        recordedFrom = new DateTime(System.currentTimeMillis());
    }

    public String getUptime() {
        long now = System.currentTimeMillis();
        long started = recordedFrom.getMillis();
        long uptime = now - started;
        return DurationFormatUtils.formatDuration(uptime, "HH:mm:ss:SS");
    }

    /**
     * Records the amount of events being stored by delegating to each individual storageStatisticsPeriod.
     * 
     * @param events the list of events that are being stored.
     */
    public void recordEventStorage(List<Event> events) {
        totalEventsStored += events.size();
        for (Event event : events) {
            String simpleName = event.getClass().getSimpleName();
            if (eventsStored.containsKey(simpleName)) {
                Long currentNo = eventsStored.get(simpleName);
                currentNo += 1;
                eventsStored.put(simpleName, currentNo);
            } else {
                eventsStored.put(simpleName, new Long(1));
            }

        }
        printEventStatisticsStdOut();
    }

    public long getTotalEventsStored() {
        return totalEventsStored;
    }

    private void printEventStatisticsStdOut() {
        for (Map.Entry<String, Long> entry : eventsStored.entrySet()) {
            log.info("from {}, [{}->{}]", new Object[] {recordedFrom, entry.getKey(), entry.getValue()});
        }
    }

    /**
     * @param recordedFrom the recordedFrom to set
     */
    public void setRecordedFrom(DateTime recordedFrom) {
        this.recordedFrom = recordedFrom;
    }

    /**
     * @return the recordedFrom
     */
    public DateTime getRecordedFrom() {
        return recordedFrom;
    }

    /**
     * @param eventsStored the eventsStored to set
     */
    public void setEventsStored(Map<String, Long> eventsStored) {
        this.eventsStored = eventsStored;
    }

    /**
     * @return the eventsStored
     */
    public Map<String, Long> getEventsStored() {
        return eventsStored;
    }

}
