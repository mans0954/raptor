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
package uk.ac.cardiff.model.event;

import org.joda.time.DateTime;

/**
 *
 */
public class AggregatedEvent extends Event {

    private DateTime periodStart;

    private DateTime periodEnd;

    public AggregatedEvent() {

    }

    /**
     * Copy constructor.
     * 
     * @param event
     *            the event to copy
     */
    protected AggregatedEvent(AggregatedEvent event) {
        super(event);
        periodStart = event.getPeriodStart();
        periodEnd = event.getPeriodEnd();

    }

    /**
     * @return Returns the periodStart.
     */
    public DateTime getPeriodStart() {
        return periodStart;
    }

    /**
     * @param periodStart
     *            The periodStart to set.
     */
    public void setPeriodStart(DateTime periodStart) {
        this.periodStart = periodStart;
    }

    /**
     * @return Returns the periodEnd.
     */
    public DateTime getPeriodEnd() {
        return periodEnd;
    }

    /**
     * @param periodEnd
     *            The periodEnd to set.
     */
    public void setPeriodEnd(DateTime periodEnd) {
        this.periodEnd = periodEnd;
    }

}
