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

import org.joda.time.DateTime;

import uk.ac.cardiff.utility.StringUtils;

/**
 * The Class ProxyEvent.
 * 
 * @author philsmart
 */
public class ProxyEvent extends Event {

    /** The request url. */
    private String requestURL;

    /** The http response code. */
    private int httpResponseCode;

    /** The response size. */
    private long responseSize;

    /** The response time. */
    private DateTime responseTime;

    /** The requester ip. */
    private String requesterIp;

    /**
     * Instantiates a new proxy event.
     */
    public ProxyEvent() {
        super();
    }

    /**
     * Copy constructor.
     * 
     * @param event
     *            the event
     */
    public ProxyEvent(ProxyEvent event) {
        super(event);
        this.requestURL = event.getRequestURL();
        this.httpResponseCode = event.getHttpResponseCode();
        this.responseSize = event.getResponseSize();
        this.responseTime = event.getResponseTime();
        this.requesterIp = event.getRequesterIp();
    }

    /**
     * Copy method. Alternative to clone.
     * 
     * @return the proxy event
     */
    public ProxyEvent cop() {
        return new ProxyEvent(this);
    }

    /**
     * Sets the request url.
     * 
     * @param requestURL
     *            the new request url
     */
    public void setRequestURL(String requestURL) {
        this.requestURL = requestURL;
    }

    /**
     * Gets the request url.
     * 
     * @return the request url
     */
    public String getRequestURL() {
        return requestURL;
    }

    /**
     * Sets the http response code.
     * 
     * @param httpResponseCode
     *            the new http response code
     */
    public void setHttpResponseCode(int httpResponseCode) {
        this.httpResponseCode = httpResponseCode;
    }

    /**
     * Gets the http response code.
     * 
     * @return the http response code
     */
    public int getHttpResponseCode() {
        return httpResponseCode;
    }

    /**
     * Sets the response size.
     * 
     * @param responseSize
     *            the new response size
     */
    public void setResponseSize(long responseSize) {
        this.responseSize = responseSize;
    }

    /**
     * Gets the response size.
     * 
     * @return the response size
     */
    public long getResponseSize() {
        return responseSize;
    }

    /**
     * Sets the response time.
     * 
     * @param responseTime
     *            the new response time
     */
    public void setResponseTime(DateTime responseTime) {
        this.responseTime = responseTime;
    }

    /**
     * Get response time, using a defensive copy.
     * 
     * @return the response time
     */
    public DateTime getResponseTime() {
        return new DateTime(responseTime);
    }

    /**
     * Sets the requester ip.
     * 
     * @param requesterIp
     *            the requesterIp to set
     */
    public void setRequesterIp(String requesterIp) {
        this.requesterIp = requesterIp;
    }

    /**
     * Gets the requester ip.
     * 
     * @return the requesterIp
     */
    public String getRequesterIp() {
        return requesterIp;
    }

    /*
     * (non-Javadoc)
     * 
     * @see uk.ac.cardiff.model.event.Event#toString()
     */
    public String toString() {
        return StringUtils.buildToString(this);
    }

}
