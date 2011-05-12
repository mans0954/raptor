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

/**
 * @author philsmart
 *
 */
public class Event {

    /** used if a persistant db primary key is required */
    private Long persistantId;

    /** attributes generic to all entries */
    private DateTime eventTime;
    private long eventId;
    private String serviceId;
    private String eventType;
    private String serviceHost;
    private String resourceHost;
    private String resourceId;

    /** User defined category for this event for this resourceId, e.g. internal resource or external resource*/
    private int resourceIdCategory;


    public Event(){

    }

    /** A Copy constructor */
    public Event(Event event){
        //this has a defensive getter, so a direct assignment is possible
        this.eventTime = event.getEventTime();
        this.eventId = event.getEventId();
        this.serviceId = event.getServiceId();
        this.eventType = event.getEventType();
        this.serviceHost = event.getServiceHost();
        this.resourceHost = event.getServiceHost();
        this.resourceId = event.getResourceId();
        this.resourceIdCategory = event.getResourceIdCategory();
    }

    public Event copy(){
        return new Event(this);
    }

    public static Event newInstance() {
        return new Event();
      }


    public void setEventTime(DateTime eventTime) {
	this.eventTime = eventTime;
    }

    /**
     * Returns the eventTime using a defensive copy
     * @return
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
     * @return a Date representation of the eventTime DateTime format
     */
    public void setDate(Date date) {
	eventTime = new DateTime(date.getTime());

    }

    /**
     * Gets the event time in milliseconds since EPOCH. Used for consistent hashing
     * of the <code>eventTime<code> field.
     *
     * @return
     */
    public long getEventTimeMillis(){
        return eventTime.getMillis();
    }

    public void setPersistantId(Long persistantId) {
	this.persistantId = persistantId;
    }

    public Long getPersistantId() {
	return persistantId;
    }

    public void setEventType(String eventType) {
	this.eventType = eventType;
    }

    public String getEventType() {
	return eventType;
    }

    public void setServiceHost(String serviceHost) {
	this.serviceHost = serviceHost;
    }

    public String getServiceHost() {
	return serviceHost;
    }

    public void setResourceHost(String resourceHost) {
	this.resourceHost = resourceHost;
    }

    public String getResourceHost() {
	return resourceHost;
    }

    public void setResourceId(String resourceId) {
	this.resourceId = resourceId;
    }

    public String getResourceId() {
	return resourceId;
    }

    /**
     * @param resourceIdCategory the resourceIdCategory to set
     */
    public void setResourceIdCategory(int resourceIdCategory) {
	this.resourceIdCategory = resourceIdCategory;
    }

    /**
     * @return the resourceIdCategory
     */
    public int getResourceIdCategory() {
	return resourceIdCategory;
    }

    public void setEventId(long eventId) {
        this.eventId = eventId;
    }

    public long getEventId() {
        return eventId;
    }

    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }

    public String getServiceId() {
        return serviceId;
    }


    public String toString() {
        return getClass().getName()+"@[" + this.getEventTime() + "," + this.getServiceHost() + "," + this.getResourceHost() + "]";
    }
}
