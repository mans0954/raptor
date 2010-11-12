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
package uk.ac.cardiff.model;

import java.util.Date;

import org.joda.time.DateTime;

/**
 * @author philsmart
 *
 */
public class Entry {

    /* used if a persistant db primary key is required*/
    private long persistantId;

    /* attributes generic to all entries in Raptor*/
    private DateTime eventTime;
    private String requestHost;
    private String serverHost;
    private String requestHostFriendlyName;
    private String serverHostFriendlyName;

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
    	return getClass().getName()+"@[" + this.getEventTime() + "," + this.getRequestHost() + "," + this.getServerHost() + "]";
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

	public void setRequestHostFriendlyName(String requestHostFriendlyName) {
		this.requestHostFriendlyName = requestHostFriendlyName;
	}

	public String getRequestHostFriendlyName() {
		return requestHostFriendlyName;
	}

	public void setServerHostFriendlyName(String serverHostFriendlyName) {
		this.serverHostFriendlyName = serverHostFriendlyName;
	}

	public String getServerHostFriendlyName() {
		return serverHostFriendlyName;
	}

	public void setPersistantId(long persistantId) {
	    this.persistantId = persistantId;
	}

	public long getPersistantId() {
	    return persistantId;
	}
}
