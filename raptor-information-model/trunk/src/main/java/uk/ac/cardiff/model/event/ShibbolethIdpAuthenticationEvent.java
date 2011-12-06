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

import java.util.Arrays;

import uk.ac.cardiff.utility.EqualsUtil;
import uk.ac.cardiff.utility.HashCodeUtil;
import uk.ac.cardiff.utility.StringUtils;

/**
 * The Class ShibbolethIdpAuthenticationEvent.
 * 
 * @author philsmart
 */
public class ShibbolethIdpAuthenticationEvent extends AuthenticationEvent {

    /** The request id. */
    private String requestId;

    /** The message profile id. */
    private String messageProfileId;

    /** The response binding. */
    private String responseBinding;

    /** The response id. */
    private String responseId;

    /** The request binding. */
    private String requestBinding;

    /** The name identifier. */
    private String nameIdentifier;

    /** The assertion ids for the released attributes. */
    private String[] assertions;

    /** The released attributes. */
    private String[] attributes;

    /**
     * Instantiates a new shibboleth idp authentication event.
     */
    public ShibbolethIdpAuthenticationEvent() {
        super();
    }

    /**
     * New instance.
     * 
     * @return the shibboleth idp authentication event
     */
    public static ShibbolethIdpAuthenticationEvent newInstance() {
        return new ShibbolethIdpAuthenticationEvent();
    }

    /**
     * Copy constructor.
     * 
     * @param event
     *            the event to copy
     */
    protected ShibbolethIdpAuthenticationEvent(ShibbolethIdpAuthenticationEvent event) {
        super(event);
        this.requestId = event.getRequestId();
        this.messageProfileId = event.getMessageProfileId();
        this.responseBinding = event.getResponseBinding();
        this.responseId = event.getResponseId();
        this.requestBinding = event.getRequestBinding();
        this.nameIdentifier = event.getNameIdentifier();

        // shallow copy is OK here, as a new array is created with immutable objects (String).
        this.assertions = event.getAssertions().clone();
        this.attributes = event.getAttributes().clone();
    }

    /**
     * Copy method. Alternative to clone. Returns a copied version of this event.
     * 
     * @return the shibboleth idp authentication event
     */
    public ShibbolethIdpAuthenticationEvent copy() {
        return new ShibbolethIdpAuthenticationEvent(this);
    }

    /**
     * Sets the response binding.
     * 
     * @param responseBinding
     *            the new response binding
     */
    public void setResponseBinding(String responseBinding) {
        this.responseBinding = responseBinding;
    }

    /**
     * Gets the response binding.
     * 
     * @return the response binding
     */
    public String getResponseBinding() {
        return responseBinding;
    }

    /**
     * Sets the request binding.
     * 
     * @param requestBinding
     *            the new request binding
     */
    public void setRequestBinding(String requestBinding) {
        this.requestBinding = requestBinding;
    }

    /**
     * Gets the request binding.
     * 
     * @return the request binding
     */
    public String getRequestBinding() {
        return requestBinding;
    }

    /**
     * Sets the message profile id.
     * 
     * @param messageProfileId
     *            the new message profile id
     */
    public void setMessageProfileId(String messageProfileId) {
        this.messageProfileId = messageProfileId;
    }

    /**
     * Gets the message profile id.
     * 
     * @return the message profile id
     */
    public String getMessageProfileId() {
        return messageProfileId;
    }

    /**
     * Sets the released attributes.
     * 
     * @param releasedAttributes
     *            the releasedAttributes to set
     */
    public void setAttributes(String[] releasedAttributes) {
        this.attributes = releasedAttributes;
    }

    /**
     * Gets the released attributes.
     * 
     * @return the releasedAttributes
     */
    public String[] getAttributes() {
        return attributes;
    }

    /*
     * (non-Javadoc)
     * 
     * @see uk.ac.cardiff.model.event.Event#toString()
     */
    public String toString() {
        return StringUtils.buildToString(this);
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if ((obj == null) || (obj.getClass() != this.getClass()))
            return false;
        ShibbolethIdpAuthenticationEvent that = (ShibbolethIdpAuthenticationEvent) obj;
        boolean areEqual = EqualsUtil.areEqual(this.getEventTimeMillis(), that.getEventTimeMillis()) && EqualsUtil.areEqual(this.getEventId(), that.getEventId())
                && EqualsUtil.areEqual(this.getAuthenticationType(), that.getAuthenticationType()) && EqualsUtil.areEqual(this.getServiceHost(), that.getServiceHost())
                && EqualsUtil.areEqual(this.getRequestId(), that.getRequestId()) && EqualsUtil.areEqual(this.getResponseBinding(), that.getResponseBinding())
                && EqualsUtil.areEqual(this.getResourceHost(), that.getResourceHost()) && EqualsUtil.areEqual(this.getMessageProfileId(), that.getMessageProfileId())
                && EqualsUtil.areEqual(this.getRequestBinding(), that.getRequestBinding()) && EqualsUtil.areEqual(this.getPrincipalName(), that.getPrincipalName())
                && EqualsUtil.areEqual(this.getNameIdentifier(), that.getNameIdentifier()) && EqualsUtil.areEqual(this.getResponseId(), that.getResponseId())
                && EqualsUtil.areEqual(this.getServiceId(), that.getServiceId()) && EqualsUtil.areEqual(this.getEventType(), that.getEventType())
                && EqualsUtil.areEqual(this.getResourceId(), that.getResourceId()) && Arrays.equals(this.getAssertions(), that.getAssertions())
                && Arrays.equals(this.getAttributes(), that.getAttributes());

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
        hash = HashCodeUtil.hash(hash, getRequestId());
        hash = HashCodeUtil.hash(hash, getResponseBinding());
        hash = HashCodeUtil.hash(hash, getResourceHost());
        hash = HashCodeUtil.hash(hash, getAttributes());
        hash = HashCodeUtil.hash(hash, getMessageProfileId());
        hash = HashCodeUtil.hash(hash, getRequestBinding());
        hash = HashCodeUtil.hash(hash, getPrincipalName());
        hash = HashCodeUtil.hash(hash, getNameIdentifier());
        hash = HashCodeUtil.hash(hash, getResponseId());
        hash = HashCodeUtil.hash(hash, getAssertions());
        hash = HashCodeUtil.hash(hash, getEventType());
        hash = HashCodeUtil.hash(hash, getServiceId());
        hash = HashCodeUtil.hash(hash, getResourceId());
        return hash;

    }

    /**
     * Sets the name identifier.
     * 
     * @param nameIdentifier
     *            the new name identifier
     */
    public void setNameIdentifier(String nameIdentifier) {
        this.nameIdentifier = nameIdentifier;
    }

    /**
     * Gets the name identifier.
     * 
     * @return the name identifier
     */
    public String getNameIdentifier() {
        return nameIdentifier;
    }

    /**
     * Sets the assertion id.
     * 
     * @param assertionId
     *            the new assertion id
     */
    public void setAssertions(String[] assertionId) {
        this.assertions = assertionId;
    }

    /**
     * Gets the assertion id.
     * 
     * @return the assertion id
     */
    public String[] getAssertions() {
        return assertions;
    }

    /**
     * Sets the response id.
     * 
     * @param responseId
     *            the new response id
     */
    public void setResponseId(String responseId) {
        this.responseId = responseId;
    }

    /**
     * Gets the response id.
     * 
     * @return the response id
     */
    public String getResponseId() {
        return responseId;
    }

    /**
     * Sets the request id.
     * 
     * @param requestId
     *            the new request id
     */
    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    /**
     * Gets the request id.
     * 
     * @return the request id
     */
    public String getRequestId() {
        return requestId;
    }

}
