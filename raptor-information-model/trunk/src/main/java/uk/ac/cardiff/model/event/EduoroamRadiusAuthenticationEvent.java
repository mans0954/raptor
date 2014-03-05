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

import uk.ac.cardiff.utility.EqualsUtil;
import uk.ac.cardiff.utility.HashCodeUtil;
import uk.ac.cardiff.utility.StringUtils;

public class EduoroamRadiusAuthenticationEvent extends AuthenticationEvent {
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
     *            the {@link EduoroamRadiusAuthenticationEvent} to copy.
     */
    protected EduoroamRadiusAuthenticationEvent(EduoroamRadiusAuthenticationEvent event) {
        super(event);
        this.clientIdentifier = event.clientIdentifier;
    }

    /**
     * Instantiates a new shibboleth idp authentication event.
     */
    public EduoroamRadiusAuthenticationEvent() {
        super();
    }

    /**
     * New instance.
     * 
     * @return the {@link EduoroamRadiusAuthenticationEvent} authentication event
     */
    public static EduoroamRadiusAuthenticationEvent newInstance() {
        return new EduoroamRadiusAuthenticationEvent();
    }

    /**
     * Copy method. Alternative to clone. Returns a copied version of this event.
     * 
     * @return the shibboleth idp authentication event
     */
    public EduoroamRadiusAuthenticationEvent copy() {
        return new EduoroamRadiusAuthenticationEvent(this);
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
        hash = HashCodeUtil.hash(hash, getResourceHost());
        hash = HashCodeUtil.hash(hash, getPrincipalName());
        hash = HashCodeUtil.hash(hash, getEventType());
        hash = HashCodeUtil.hash(hash, getServiceId());
        hash = HashCodeUtil.hash(hash, getResourceId());
        hash = HashCodeUtil.hash(hash, getClientIdentifier());
        return hash;

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
     * For hibernate, so the hashcode can be persisted.
     * 
     * @return the hash code
     */
    public int getHashCode() {
        return hashCode();
    }

    /**
     * 
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if ((obj == null) || (obj.getClass() != this.getClass()))
            return false;
        EduoroamRadiusAuthenticationEvent that = (EduoroamRadiusAuthenticationEvent) obj;
        boolean areEqual = EqualsUtil.areEqual(this.getEventTimeMillis(), that.getEventTimeMillis()) && EqualsUtil.areEqual(this.getEventId(), that.getEventId())
                && EqualsUtil.areEqual(this.getAuthenticationType(), that.getAuthenticationType()) && EqualsUtil.areEqual(this.getServiceHost(), that.getServiceHost())
                && EqualsUtil.areEqual(this.getResourceHost(), that.getResourceHost()) && EqualsUtil.areEqual(this.getPrincipalName(), that.getPrincipalName())
                && EqualsUtil.areEqual(this.getServiceId(), that.getServiceId()) && EqualsUtil.areEqual(this.getEventType(), that.getEventType())
                && EqualsUtil.areEqual(this.getResourceId(), that.getResourceId()) && EqualsUtil.areEqual(this.getClientIdentifier(), that.getClientIdentifier());

        return areEqual;
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
