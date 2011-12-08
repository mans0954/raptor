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

package uk.ac.cardiff.raptormua.engine.statistics.records;

import org.joda.time.DateTime;

/**
 * The Class Bucket.
 */
public class Bucket extends Observation {

    /** The start date and time. */
    private DateTime start;

    /** The end date and time. */
    private DateTime end;

    /**
     * Checks if is inside.
     * 
     * @param eventTime the event time
     * @return true, if is inside
     */
    public boolean isInside(DateTime eventTime) {
        /* semantics here are; if equal to or after start but before end return true */
        if ((eventTime.isEqual(start) && eventTime.isBefore(end))
                || (eventTime.isAfter(start) && eventTime.isBefore(end)))
            return true;
        return false;
    }

    /**
     * Increment.
     */
    public void increment() {
        value++;
    }

    /**
     * Sets the start.
     * 
     * @param start the start to set
     */
    public void setStart(DateTime start) {
        this.start = start;
    }

    /**
     * Gets the start.
     * 
     * @return the start
     */
    public DateTime getStart() {
        return start;
    }

    /**
     * Sets the end.
     * 
     * @param end the end to set
     */
    public void setEnd(DateTime end) {
        this.end = end;
    }

    /**
     * Gets the end.
     * 
     * @return the end
     */
    public DateTime getEnd() {
        return end;
    }

}
