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
package uk.ac.cardiff.model.wsmodel;

import java.io.Serializable;

public class EventTypeInformation implements Serializable {

    /**
     * Generated SerialUID
     */
    private static final long serialVersionUID = 8560185706003251298L;

    private String eventTypeName;

    private long noOfEvents;

    public EventTypeInformation(String eventType, long count) {
        eventTypeName = eventType;
        noOfEvents = count;
    }

    /**
     * Default constructor.
     */
    public EventTypeInformation() {

    }

    /**
     * @param noOfEvents
     *            the noOfEvents to set
     */
    public void setNoOfEvents(long noOfEvents) {
        this.noOfEvents = noOfEvents;
    }

    /**
     * @return the noOfEvents
     */
    public long getNoOfEvents() {
        return noOfEvents;
    }

    /**
     * @param eventTypeName
     *            the eventTypeName to set
     */
    public void setEventTypeName(String eventTypeName) {
        this.eventTypeName = eventTypeName;
    }

    /**
     * @return the eventTypeName
     */
    public String getEventTypeName() {
        return eventTypeName;
    }
}