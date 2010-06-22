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
package main.uk.ac.cardiff.model;

import java.util.Date;

import org.joda.time.DateTime;

/**
 * @author philsmart
 *
 */
public class Entry {
    private DateTime eventTime;
    private String requestHost;
    private String serverHost;

    public void setRequestHost(String requestHost) {
	this.requestHost = requestHost;
    }

    public String getRequestHost() {
	return requestHost;
    }

    public void setEventTime(DateTime eventTime) {
	this.eventTime = eventTime;
    }

    public DateTime getEventTime() {
	return eventTime;
    }

    public void setServerHost(String serverHost) {
	this.serverHost = serverHost;
    }

    public String getServerHost() {
	return serverHost;
    }

    public String toString() {
	return "[" + this.getEventTime() + "," + this.getRequestHost() + "," + this.getServerHost() + "]";
    }

    /**
     * Only XML DATE or XML DATETIME objects can be sent through XML WS
     * hence, the joda.DateTime property is ignored, instead a proxy
     * getDate method is used to convert from DateTime to Date
     * <p>
     *
     * @return a Date representation of the eventTime DateTime format
     */
    public Date getDate() {
	Date now = new Date(eventTime.getMillis());
	return now;

    }

    /**
     * Only XML DATE or XML DATETIME objects can be sent through XML WS
     * hence, the joda.DateTime property is ignored, instead a proxy
     * setDate method is used to convert from the XML Date to the DateTime
     * used by eventTime
     * <p>
     *
     * @return a Date representation of the eventTime DateTime format
     */
    public void setDate(Date date) {
	eventTime = new DateTime(date.getTime());

    }
}
