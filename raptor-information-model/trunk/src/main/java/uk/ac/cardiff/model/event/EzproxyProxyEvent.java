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

import uk.ac.cardiff.utility.StringUtils;

/**
 * The Class EzproxyProxyEvent.
 * 
 * @author philsmart
 */
public class EzproxyProxyEvent extends ProxyEvent {

    /** The session id. */
    private String sessionId;

    /**
     * Instantiates a new ezproxy proxy event.
     */
    public EzproxyProxyEvent() {
        super();
    }

    /**
     * Copy constructor.
     * 
     * @param event
     *            the event
     */
    protected EzproxyProxyEvent(EzproxyProxyEvent event) {
        super(event);
        this.sessionId = event.getSessionId();
    }

    /**
     * Copy method. Alternative to clone.
     * 
     * @return the ezproxy proxy event
     */
    public EzproxyProxyEvent copy() {
        return new EzproxyProxyEvent(this);
    }

    /**
     * Sets the session id.
     * 
     * @param sessionId
     *            the sessionId to set
     */
    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    /**
     * Gets the session id.
     * 
     * @return the sessionId
     */
    public String getSessionId() {
        return sessionId;
    }

    /*
     * (non-Javadoc)
     * 
     * @see uk.ac.cardiff.model.event.ProxyEvent#toString()
     */
    public String toString() {
        return StringUtils.buildToString(this);
    }

}
