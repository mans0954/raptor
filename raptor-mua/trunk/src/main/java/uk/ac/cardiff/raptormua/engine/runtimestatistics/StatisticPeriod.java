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

import org.joda.time.DateTime;

/**
 * 
 * Interface that a define period should implement in order to be utalised by runtime statistics. Implementations
 * constructed programatically, but could be constructed by a code template engine form xml definition. Implementations
 * must provide this information dynamically for each call of getStart and getEnd, otherwise information (statistics)
 * derived from this period will not update correctly.
 */
public interface StatisticPeriod {

    /**
     * Get the start date and time of this period.
     * 
     * @return the start date and time.
     */
    public DateTime getStart();

    /**
     * Gets the end date and time of this period.
     * 
     * @return the end date and time.
     */
    public DateTime getEnd();

    /**
     * Name identifier for this period.
     * 
     * @return the name identifier for this period.
     */
    public String getPeriodIdentifier();

}
