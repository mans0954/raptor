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

package uk.ac.cardiff.raptor.registry;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import uk.ac.cardiff.model.event.Event;
import uk.ac.cardiff.model.wsmodel.StatisticParameters.ResourceCategory;
import uk.ac.cardiff.raptor.attribute.filtering.AttributeFilterPolicy;
import uk.ac.cardiff.raptor.remoting.policy.PushPolicy;

/**
 * 
 * Stores information about an endpoint that a client can send events to. Also includes the Push Policy used to
 * determine if/when events should be sent, an attribute filter policy for determining which attributes of each event
 * should be sent, and information about previous event releases.
 * 
 * This class could be subclassed if additional functionality is required.
 * 
 */
public class Endpoint {

    /** class logger. */
    private final Logger log = LoggerFactory.getLogger(Endpoint.class);

    /** The URL of the service endpoint. */
    private String serviceEndpoint;

    /** A List of the PushPolicies defined for this endpoint. */
    private List<PushPolicy> pushPolicies;

    /** The filter policy defined for this endpoint *. */
    private AttributeFilterPolicy attributeFilterPolicy;

    /** Information about what information has been released to this endpoint. */
    private ReleaseInformation releaseInformation;

    /** Which event types can be sent to this endpoint. */
    private Class<?>[] supportedEvents;

    /**
     * The resource category this endpoint supports, e.g. internal, external or all.
     */
    private ResourceCategory supportedResourceCategory;

    /**
     * Default constructor. Instantiate <code>releaseInformation</code>
     */
    public Endpoint() {
        releaseInformation = new ReleaseInformation();
    }

    /**
     * Actions the <code>releasedPerformed</code> on the <code>releaseInformation</code> object.
     * 
     * @param filteredEntries the filtered entries
     */
    public void releasePerformed(final List<Event> filteredEntries) {
        releaseInformation.releasePerformed(filteredEntries);
    }

    /**
     * Sets the service endpoint.
     * 
     * @param serviceEndpoint the new service endpoint
     */
    public void setServiceEndpoint(String serviceEndpoint) {
        this.serviceEndpoint = serviceEndpoint;
        releaseInformation.setServiceEndpoint(serviceEndpoint);
    }

    /**
     * Gets the service endpoint.
     * 
     * @return the service endpoint
     */
    public String getServiceEndpoint() {
        return serviceEndpoint;
    }

    /**
     * Sets the attribute filter policy.
     * 
     * @param attributeFilterPolicy the new attribute filter policy
     */
    public void setAttributeFilterPolicy(AttributeFilterPolicy attributeFilterPolicy) {
        this.attributeFilterPolicy = attributeFilterPolicy;
    }

    /**
     * Gets the attribute filter policy.
     * 
     * @return the attribute filter policy
     */
    public AttributeFilterPolicy getAttributeFilterPolicy() {
        return attributeFilterPolicy;
    }

    /**
     * Sets the push policies.
     * 
     * @param pushPolicies the new push policies
     */
    public void setPushPolicies(List<PushPolicy> pushPolicies) {
        this.pushPolicies = pushPolicies;
    }

    /**
     * Gets the push policies.
     * 
     * @return the push policies
     */
    public List<PushPolicy> getPushPolicies() {
        return pushPolicies;
    }

    /**
     * Gets the release information.
     * 
     * @return the releaseInformation
     */
    public ReleaseInformation getReleaseInformation() {
        return releaseInformation;
    }

    /**
     * Sets the releaseInformation.
     * 
     * @param releaseInformation the new release information
     */
    public void setReleaseInformation(ReleaseInformation releaseInformation) {
        this.releaseInformation = releaseInformation;
    }

    /**
     * Sets the supported events.
     * 
     * @param supportedEvents the supportedEvents to set
     */
    public void setSupportedEvents(Class<?>[] supportedEvents) {
        this.supportedEvents = supportedEvents;
    }

    /**
     * Gets the supported events.
     * 
     * @return the supportedEvents
     */
    public Class<?>[] getSupportedEvents() {
        return supportedEvents;
    }

    /**
     * @param supportedResourceCategory the supportedResourceCategory to set
     */
    public void setSupportedResourceCategory(ResourceCategory supportedResourceCategory) {
        this.supportedResourceCategory = supportedResourceCategory;
    }

    /**
     * @return the supportedResourceCategory
     */
    public ResourceCategory getSupportedResourceCategory() {
        return supportedResourceCategory;
    }

}
