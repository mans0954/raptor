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

import uk.ac.cardiff.utility.EqualsUtil;
import uk.ac.cardiff.utility.HashCodeUtil;
import uk.ac.cardiff.utility.StringUtils;

/**
 * The Class OpenathenslaAuthenticationEvent.
 * 
 * @author philsmart
 */
public class OpenathenslaAuthenticationEvent extends AuthenticationEvent {

    /** The requester ip. */
    private String requesterIp;

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
        this.requesterIp = event.getRequesterIp();
    }

    /**
     * Copy method. Alternative to clone.
     * 
     * @return the openathensla authentication event
     */
    @Override
    public OpenathenslaAuthenticationEvent copy() {
        return new OpenathenslaAuthenticationEvent(this);
    }

    /**
     * Sets the requester ip.
     * 
     * @param requesterIP
     *            the new requester ip
     */
    public void setRequesterIp(String requesterIP) {
        this.requesterIp = requesterIP;
    }

    /**
     * Gets the requester ip.
     * 
     * @return the requester ip
     */
    public String getRequesterIp() {
        return requesterIp;
    }

    /*
     * (non-Javadoc)
     * 
     * @see uk.ac.cardiff.model.event.Event#toString()
     */
    @Override
    public String toString() {
        return StringUtils.buildToString(this);
    }

    /**
     * For hibernate, so the hashcode can be persisted.
     * 
     * @return the hash code
     */
    public int getHashCode() {
        return hashCode();
    }

    /**
     * For hibernate, does nothing as the hascode is computed on the fly from the <code>hashCode</code> method.
     * 
     * @param hashCode
     *            the new hash code
     */
    public void setHashCode(int hashCode) {

    }

    /**
     * create a unique hash, with as uniform a distribution as possible.
     * 
     * @return the int
     */
    @Override
    public int hashCode() {
        int hash = HashCodeUtil.SEED;

        hash = HashCodeUtil.hash(hash, getEventTimeMillis());
        hash = HashCodeUtil.hash(hash, getAuthenticationType());
        hash = HashCodeUtil.hash(hash, getEventId());
        hash = HashCodeUtil.hash(hash, getServiceHost());
        hash = HashCodeUtil.hash(hash, getRequesterIp());
        hash = HashCodeUtil.hash(hash, getResourceHost());
        hash = HashCodeUtil.hash(hash, getPrincipalName());
        hash = HashCodeUtil.hash(hash, getEventType());
        hash = HashCodeUtil.hash(hash, getServiceId());
        hash = HashCodeUtil.hash(hash, getResourceId());

        return hash;

    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if ((obj == null) || (obj.getClass() != this.getClass()))
            return false;
        OpenathenslaAuthenticationEvent that = (OpenathenslaAuthenticationEvent) obj;
        boolean areEqual = EqualsUtil.areEqual(this.getEventTimeMillis(), that.getEventTimeMillis()) && EqualsUtil.areEqual(this.getEventId(), that.getEventId())
                && EqualsUtil.areEqual(this.getAuthenticationType(), that.getAuthenticationType()) && EqualsUtil.areEqual(this.getServiceHost(), that.getServiceHost())
                && EqualsUtil.areEqual(this.getRequesterIp(), that.getRequesterIp()) && EqualsUtil.areEqual(this.getResourceHost(), that.getResourceHost())
                && EqualsUtil.areEqual(this.getPrincipalName(), that.getPrincipalName()) && EqualsUtil.areEqual(this.getServiceId(), that.getServiceId())
                && EqualsUtil.areEqual(this.getEventType(), that.getEventType()) && EqualsUtil.areEqual(this.getResourceId(), that.getResourceId());

        return areEqual;
    }
}
