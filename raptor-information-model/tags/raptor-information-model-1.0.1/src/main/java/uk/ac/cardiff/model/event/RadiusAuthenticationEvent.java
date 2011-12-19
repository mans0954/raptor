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

public class RadiusAuthenticationEvent extends AuthenticationEvent {
    /*
     * grep for Auth Login OK AND grep for without @ in the line
     * 
     * DAY DATE, principalName,
     */

    private String clientIdentifier;

    /**
     * Copy constructor.
     * 
     * @param event
     *            the {@link RadiusAuthenticationEvent} to copy.
     */
    protected RadiusAuthenticationEvent(RadiusAuthenticationEvent event) {
        super(event);
        this.clientIdentifier = event.clientIdentifier;
    }

    /**
     * @param clientIdentifier
     *            the clientIdentifier to set
     */
    public void setClientIdentifier(String clientIdentifier) {
        this.clientIdentifier = clientIdentifier;
    }

    /**
     * @return the clientIdentifier
     */
    public String getClientIdentifier() {
        return clientIdentifier;
    }
}
