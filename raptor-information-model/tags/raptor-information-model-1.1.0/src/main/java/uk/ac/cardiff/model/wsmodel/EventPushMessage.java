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
/**
 *
 */
package uk.ac.cardiff.model.wsmodel;

import java.util.Date;
import java.util.List;

import uk.ac.cardiff.model.ServiceMetadata;
import uk.ac.cardiff.model.event.Event;

/**
 * The Class EventPushMessage.
 * 
 * @author philsmart
 */
public class EventPushMessage {
    // TODO to be replaced by SAML messages

    /** The list of events that are being sent. */
    private List<Event> events;

    /** The metadata of the client sending the events. */
    private ServiceMetadata clientMetadata;

    /** The time instant the message was created. */
    private Date timeOfPush;

    /**
     * Sets the events.
     * 
     * @param events
     *            the new events
     */
    public void setEvents(List<Event> events) {
        this.events = events;
    }

    /**
     * Gets the events.
     * 
     * @return the events
     */
    public List<Event> getEvents() {
        return events;
    }

    /**
     * Sets the time of push.
     * 
     * @param timeOfPush
     *            the new time of push
     */
    public void setTimeOfPush(Date timeOfPush) {
        this.timeOfPush = timeOfPush;
    }

    /**
     * Gets the time of push.
     * 
     * @return the time of push
     */
    public Date getTimeOfPush() {
        return timeOfPush;
    }

    /**
     * Sets the client metadata.
     * 
     * @param clientMetadata
     *            the new client metadata
     */
    public void setClientMetadata(ServiceMetadata clientMetadata) {
        this.clientMetadata = clientMetadata;
    }

    /**
     * Gets the client metadata.
     * 
     * @return the client metadata
     */
    public ServiceMetadata getClientMetadata() {
        return clientMetadata;
    }

}
