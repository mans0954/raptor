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

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import uk.ac.cardiff.model.event.Event;
import uk.ac.cardiff.raptor.store.SaveAndApplyResourceClassificationTask;

public class StorageStatisticsInterceptor {

    /** class logger */
    private final Logger log = LoggerFactory.getLogger(SaveAndApplyResourceClassificationTask.class);

    /** realtime statistics storage . */
    private RuntimeEventStorageStatistics eventStorageStatistics;

    public void eventStoreStatistics(int transactionId, List<Event> events) throws Throwable {
        log.info("Storage statistics intercepted, storage [{}] has [{}] events", transactionId, events.size());
        eventStorageStatistics.recordEventStorage(events);
    }

    /**
     * @param eventStorageStatistics the eventStorageStatistics to set
     */
    public void setEventStorageStatistics(RuntimeEventStorageStatistics eventStorageStatistics) {
        this.eventStorageStatistics = eventStorageStatistics;
    }

}
