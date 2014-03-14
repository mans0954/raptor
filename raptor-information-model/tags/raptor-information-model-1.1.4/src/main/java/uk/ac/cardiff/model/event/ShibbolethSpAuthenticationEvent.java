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
 * The Class ShibbolethIdpAuthenticationEvent.
 * 
 * @author philsmart
 */
public class ShibbolethSpAuthenticationEvent extends AuthenticationEvent {

    /**
     * Which protocol was used in communication between IdP and this Sp.
     */
    private String protocol;

    /**
     * The IP address of the client accessing this Sp.
     */
    private String clientIp;

    /**
     * The id of the session given by this SP.
     */
    private String sessionId;

    /**
     * Instantiates a new shibboleth idp authentication event.
     */
    public ShibbolethSpAuthenticationEvent() {
        super();
    }

    /**
     * New instance.
     * 
     * @return the shibboleth sp authentication event
     */
    public static ShibbolethSpAuthenticationEvent newInstance() {
        return new ShibbolethSpAuthenticationEvent();
    }

    /**
     * Copy constructor.
     * 
     * @param event
     *            the event to copy
     */
    protected ShibbolethSpAuthenticationEvent(ShibbolethSpAuthenticationEvent event) {
        super(event);
        this.clientIp = event.getClientIp();
        this.sessionId = event.getSessionId();
        this.protocol = event.getProtocol();

    }

    /**
     * Copy method. Alternative to clone. Returns a copied version of this event.
     * 
     * @return the shibboleth idp authentication event
     */
    public ShibbolethSpAuthenticationEvent copy() {
        return new ShibbolethSpAuthenticationEvent(this);
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
        ShibbolethSpAuthenticationEvent that = (ShibbolethSpAuthenticationEvent) obj;
        boolean areEqual = EqualsUtil.areEqual(this.getEventTimeMillis(), that.getEventTimeMillis()) && EqualsUtil.areEqual(this.getEventId(), that.getEventId())
                && EqualsUtil.areEqual(this.getAuthenticationType(), that.getAuthenticationType()) && EqualsUtil.areEqual(this.getServiceHost(), that.getServiceHost())
                && EqualsUtil.areEqual(this.getResourceHost(), that.getResourceHost()) && EqualsUtil.areEqual(this.getPrincipalName(), that.getPrincipalName())
                && EqualsUtil.areEqual(this.getServiceId(), that.getServiceId()) && EqualsUtil.areEqual(this.getEventType(), that.getEventType())
                && EqualsUtil.areEqual(this.getResourceId(), that.getResourceId()) && EqualsUtil.areEqual(this.getProtocol(), that.getProtocol())
                && EqualsUtil.areEqual(this.getClientIp(), that.getClientIp()) && EqualsUtil.areEqual(this.getSessionId(), that.getSessionId());

        return areEqual;
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
        hash = HashCodeUtil.hash(hash, getResourceHost());
        hash = HashCodeUtil.hash(hash, getPrincipalName());
        hash = HashCodeUtil.hash(hash, getEventType());
        hash = HashCodeUtil.hash(hash, getServiceId());
        hash = HashCodeUtil.hash(hash, getResourceId());
        hash = HashCodeUtil.hash(hash, getProtocol());
        hash = HashCodeUtil.hash(hash, getClientIp());
        hash = HashCodeUtil.hash(hash, getSessionId());
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
     * @param protocol
     *            the protocol to set
     */
    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    /**
     * @return the protocol
     */
    public String getProtocol() {
        return protocol;
    }

    /**
     * @param clientIp
     *            the clientIp to set
     */
    public void setClientIp(String clientIp) {
        this.clientIp = clientIp;
    }

    /**
     * @return the clientIp
     */
    public String getClientIp() {
        return clientIp;
    }

    /**
     * @param sessionId
     *            the sessionId to set
     */
    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    /**
     * @return the sessionId
     */
    public String getSessionId() {
        return sessionId;
    }

}
