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
 * The Class OpenathenslaAuthenticationEvent.
 * 
 * @author philsmart
 */
public class OpenathenslaAuthenticationEvent extends Event {

    /** The requester ip. */
    private String requesterIP;

    /**
     * Instantiates a new openathensla authentication event.
     */
    public OpenathenslaAuthenticationEvent() {
        super();
    }

    /**
     * Copy constructor.
     * 
     * @param event
     *            the event
     */
    public OpenathenslaAuthenticationEvent(OpenathenslaAuthenticationEvent event) {
        super(event);
        this.requesterIP = event.getRequesterIP();
    }

    /**
     * Copy method. Alternative to clone.
     * 
     * @return the openathensla authentication event
     */
    public OpenathenslaAuthenticationEvent copy() {
        return new OpenathenslaAuthenticationEvent(this);
    }

    /**
     * Sets the requester ip.
     * 
     * @param requesterIP
     *            the new requester ip
     */
    public void setRequesterIP(String requesterIP) {
        this.requesterIP = requesterIP;
    }

    /**
     * Gets the requester ip.
     * 
     * @return the requester ip
     */
    public String getRequesterIP() {
        return requesterIP;
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
