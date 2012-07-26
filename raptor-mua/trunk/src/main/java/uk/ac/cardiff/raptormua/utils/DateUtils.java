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

package uk.ac.cardiff.raptormua.utils;

import java.util.List;

import org.joda.time.DateTime;

import uk.ac.cardiff.model.event.Event;

public class DateUtils {

    /**
     * Returns the earliest date and time (by eventTime) {@link DateTime} from the input set of <code>events</code>
     * 
     * @param events the list of events from which to return the earliest.
     * @return the date and time of the earliest event, null otherwise.
     */
    public DateTime getEarliestTime(List<Event> events) {
        DateTime earliest = null;
        for (Event event : events) {
            if (earliest == null) {
                earliest = event.getEventTime();
            }
            if (event.getEventTime().isBefore(earliest)) {
                earliest = event.getEventTime();
            }
        }
        return earliest;
    }

    /**
     * Returns the latest date and time (by eventTime) {@link DateTime} from the input set of <code>events</code>
     * 
     * @param events the list of events from which to return the latest.
     * @return the date and time of the latest event, null otherwise.
     */
    public DateTime getLatestTime(List<Event> events) {
        DateTime earliest = null;
        for (Event event : events) {
            if (earliest == null) {
                earliest = event.getEventTime();
            }
            if (event.getEventTime().isAfter(earliest)) {
                earliest = event.getEventTime();
            }
        }
        return earliest;
    }

}
