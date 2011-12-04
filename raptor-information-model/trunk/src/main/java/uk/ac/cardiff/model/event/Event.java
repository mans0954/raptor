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
package uk.ac.cardiff.model.event;

import java.util.Date;

import org.joda.time.DateTime;

import uk.ac.cardiff.model.event.auxiliary.EventMetadata;
import uk.ac.cardiff.utility.StringUtils;

/**
 * For any class that extends this class, equality must be computed for the same fields as the hash. Otherwise a discrepancy will occur when checking
 * containment through object equality (e.g. set containment), and through hashcodes.
 * 
 * @author philsmart
 * 
 */
public class Event {

    /**
     * used if a persistant db primary key is required. Not to be used in the computation of the hash or equals methods
     */
    private Long persistantId;

    /** The date and time the event was generated. */
    private DateTime eventTime;

    /**
     * Event id, as generated and added by the hashcode of this method. Not to be used in computation of the hash or equals methods.
     */
    private int eventId;

    /** The service id. This is the service that serves the event. */
    private String serviceId;

    /** The event type. */
    private String eventType;

    /** The hostname of the service that provided this event. */
    private String serviceHost;

    /** The hostname of the resource you are using. */
    private String resourceHost;

    /** The resource id. This is the resource you are using. */
    private String resourceId;

    /**
     * User defined category for this event for this resourceId, e.g. internal resource or external resource. 1 - Internal 2 - External
     */
    private int resourceIdCategory;

    /**
     * Metadata about the service this event was generated from. Not used in Hash or Equality methods.
     */
    private EventMetadata eventMetadata;

    /**
     * Instantiates a new event.
     */
    public Event() {

    }

    /**
     * A Copy constructor.
     * 
     * @param event
     *            the event
     */
    public Event(Event event) {
        // this has a defensive getter, so direct assignment is possible
        this.eventTime = event.getEventTime();
        this.eventId = event.getEventId();
        this.serviceId = event.getServiceId();
        this.eventType = event.getEventType();
        this.serviceHost = event.getServiceHost();
        this.resourceHost = event.getServiceHost();
        this.resourceId = event.getResourceId();
        this.resourceIdCategory = event.getResourceIdCategory();
    }

    /**
     * Copy.
     * 
     * @return the event
     */
    public Event copy() {
        return new Event(this);
    }

    /**
     * New instance.
     * 
     * @return the event
     */
    public static Event newInstance() {
        return new Event();
    }

    /**
     * Sets the event time.
     * 
     * @param eventTime
     *            the new event time
     */
    public void setEventTime(DateTime eventTime) {
        this.eventTime = eventTime;
    }

    /**
     * Returns the eventTime using a defensive copy.
     * 
     * @return the event time
     */
    public DateTime getEventTime() {
        return new DateTime(eventTime);
    }

    /**
     * Only XML DATE or XML DATETIME objects can be sent through XML WS hence, the joda.DateTime property is ignored, instead a proxy getDate method is used to
     * convert from DateTime to Date
     * <p>
     * 
     * @return a Date representation of the eventTime DateTime format
     */
    public Date getDate() {
        Date now = new Date(eventTime.getMillis());
        return now;

    }

    /**
     * Only XML DATE or XML DATETIME objects can be sent through XML WS hence, the joda.DateTime property is ignored, instead a proxy setDate method is used to
     * convert from the XML Date to the DateTime used by eventTime
     * <p>
     * 
     * @param date
     *            the new date
     * @return a Date representation of the eventTime DateTime format
     */
    public void setDate(Date date) {
        eventTime = new DateTime(date.getTime());

    }

    /**
     * Gets the event time in milliseconds since EPOCH. Used for consistent hashing of the <code>eventTime<code> field.
     * 
     * @return the event time millis
     */
    public long getEventTimeMillis() {
        return eventTime.getMillis();
    }

    /**
     * Sets the persistant id.
     * 
     * @param persistantId
     *            the new persistant id
     */
    public void setPersistantId(Long persistantId) {
        this.persistantId = persistantId;
    }

    /**
     * Gets the persistant id.
     * 
     * @return the persistant id
     */
    public Long getPersistantId() {
        return persistantId;
    }

    /**
     * Sets the event type.
     * 
     * @param eventType
     *            the new event type
     */
    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    /**
     * Gets the event type.
     * 
     * @return the event type
     */
    public String getEventType() {
        return eventType;
    }

    /**
     * Sets the service host.
     * 
     * @param serviceHost
     *            the new service host
     */
    public void setServiceHost(String serviceHost) {
        this.serviceHost = serviceHost;
    }

    /**
     * Gets the service host.
     * 
     * @return the service host
     */
    public String getServiceHost() {
        return serviceHost;
    }

    /**
     * Sets the resource host.
     * 
     * @param resourceHost
     *            the new resource host
     */
    public void setResourceHost(String resourceHost) {
        this.resourceHost = resourceHost;
    }

    /**
     * Gets the resource host.
     * 
     * @return the resource host
     */
    public String getResourceHost() {
        return resourceHost;
    }

    /**
     * Sets the resource id.
     * 
     * @param resourceId
     *            the new resource id
     */
    public void setResourceId(String resourceId) {
        this.resourceId = resourceId;
    }

    /**
     * Gets the resource id.
     * 
     * @return the resource id
     */
    public String getResourceId() {
        return resourceId;
    }

    /**
     * Sets the resource id category.
     * 
     * @param resourceIdCategory
     *            the resourceIdCategory to set
     */
    public void setResourceIdCategory(int resourceIdCategory) {
        this.resourceIdCategory = resourceIdCategory;
    }

    /**
     * Gets the resource id category.
     * 
     * @return the resourceIdCategory
     */
    public int getResourceIdCategory() {
        return resourceIdCategory;
    }

    /**
     * Sets the event id.
     * 
     * @param eventId
     *            the new event id
     */
    public void setEventId(int eventId) {
        this.eventId = eventId;
    }

    /**
     * Gets the event id.
     * 
     * @return the event id
     */
    public int getEventId() {
        return eventId;
    }

    /**
     * Sets the service id.
     * 
     * @param serviceId
     *            the new service id
     */
    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }

    /**
     * Gets the service id.
     * 
     * @return the service id
     */
    public String getServiceId() {
        return serviceId;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    public String toString() {
        return StringUtils.buildToString(this);
    }

    /**
     * Sets the event metadata.
     * 
     * @param eventMetadata
     *            the eventMetadata to set
     */
    public void setEventMetadata(EventMetadata eventMetadata) {
        this.eventMetadata = eventMetadata;
    }

    /**
     * Gets the event metadata.
     * 
     * @return the eventMetadata
     */
    public EventMetadata getEventMetadata() {
        return eventMetadata;
    }
}
