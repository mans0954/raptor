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

/**
 * The Class GenericAuthenticationEvent.
 * 
 */
public class GenericAuthenticationEvent extends AuthenticationEvent {

    /**
     * The name of a field not included in any of the superclasses and specific to this event.
     */
    private String someField;

    /**
     * Instantiates a new Generic Authentication Event.
     */
    public GenericAuthenticationEvent() {
        super();
    }

    /**
     * New instance.
     * 
     * @return the Generic Authentication Event
     */
    public static GenericAuthenticationEvent newInstance() {
        return new GenericAuthenticationEvent();
    }

    /**
     * Copy constructor.
     * 
     * @param event
     *            the event to copy
     */
    protected GenericAuthenticationEvent(GenericAuthenticationEvent event) {
        super(event);
        someField = event.getSomeField();

    }

    /**
     * Copy method. Alternative to clone. Returns a copied version of this event.
     * 
     * @return the Generic Authentication Event
     */
    public GenericAuthenticationEvent copy() {
        return new GenericAuthenticationEvent(this);
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
        GenericAuthenticationEvent that = (GenericAuthenticationEvent) obj;
        boolean areEqual = EqualsUtil.areEqual(this.getEventTimeMillis(), that.getEventTimeMillis()) && EqualsUtil.areEqual(this.getEventId(), that.getEventId())
                && EqualsUtil.areEqual(this.getAuthenticationType(), that.getAuthenticationType()) && EqualsUtil.areEqual(this.getServiceHost(), that.getServiceHost())
                && EqualsUtil.areEqual(this.getResourceHost(), that.getResourceHost()) && EqualsUtil.areEqual(this.getPrincipalName(), that.getPrincipalName())
                && EqualsUtil.areEqual(this.getServiceId(), that.getServiceId()) && EqualsUtil.areEqual(this.getEventType(), that.getEventType())
                && EqualsUtil.areEqual(this.getResourceId(), that.getResourceId()) &&
                // all new fields should be included in the equality method under here.
                EqualsUtil.areEqual(this.getSomeField(), that.getSomeField());

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
        // all inherited fields are hashed here.
        hash = HashCodeUtil.hash(hash, getEventTimeMillis());
        hash = HashCodeUtil.hash(hash, getAuthenticationType());
        hash = HashCodeUtil.hash(hash, getEventId());
        hash = HashCodeUtil.hash(hash, getServiceHost());
        hash = HashCodeUtil.hash(hash, getResourceHost());
        hash = HashCodeUtil.hash(hash, getPrincipalName());
        hash = HashCodeUtil.hash(hash, getEventType());
        hash = HashCodeUtil.hash(hash, getServiceId());
        hash = HashCodeUtil.hash(hash, getResourceId());
        // all new fields part of the business key should be hashed below.
        hash = HashCodeUtil.hash(hash, getSomeField());

        return hash;

    }

    /**
     * Automatic construction of a basic to string method for this class using reflection. Does not require modification unless different behavior is desired.
     */
    public String toString() {
        return StringUtils.buildToString(this);
    }

    /**
     * @param someField
     *            the someField to set
     */
    public void setSomeField(String someField) {
        this.someField = someField;
    }

    /**
     * @return the someField
     */
    public String getSomeField() {
        return someField;
    }

}
