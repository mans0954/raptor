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
import uk.ac.cardiff.raptor.attribute.filtering.AttributeFilterPolicy;
import uk.ac.cardiff.raptor.remoting.policy.AbstractPushPolicy;

/**
 *
 * Stores information about an endpoint that a client can send events to. Also includes the Push Policy
 * to determine if events should be sent, an attribute filter policy for determining which attributes
 * of each event should be sent, and information about previous event releases.
 *
 * This class could be subclassed if additional functionality is required.
 *
 * @author philsmart
 *
 */
public class Endpoint {

	/** class logger */
	private final Logger log = LoggerFactory.getLogger(Endpoint.class);


	/** The URL of the service endpoint */
	private String serviceEndpoint;

	/** A List of the PushPolicies defined for this endpoint */
	private List<AbstractPushPolicy> pushPolicies;

	/** The filter policy defined for this endpoint **/
	private AttributeFilterPolicy attributeFilterPolicy;

	/** Information about what information has been released to this endpoint */
	private ReleaseInformation releaseInformation;

	/** Which event types can be sent to this endpoint */
	private Class<?>[] supportedEvents;


	/**
	 * Default constructor. Instantiate <code>releaseInformation</code>
	 */
	public Endpoint(){
		releaseInformation = new ReleaseInformation();
	}


	/**
	 * Actions the <code>releasedPerformed</code> on the <code>releaseInformation</code> object.
	 *
	 * @param filteredEntries
	 */
	public void releasePerformed(List<Event> filteredEntries) {
		releaseInformation.releasePerformed(filteredEntries);
	}

	public void setServiceEndpoint(String serviceEndpoint) {
		this.serviceEndpoint = serviceEndpoint;
		releaseInformation.setServiceEndpoint(serviceEndpoint);
	}

	public String getServiceEndpoint() {
		return serviceEndpoint;
	}

	public void setAttributeFilterPolicy(AttributeFilterPolicy attributeFilterPolicy) {
		this.attributeFilterPolicy = attributeFilterPolicy;
	}

	public AttributeFilterPolicy getAttributeFilterPolicy() {
		return attributeFilterPolicy;
	}

	public void setPushPolicies(List<AbstractPushPolicy> pushPolicies) {
		this.pushPolicies = pushPolicies;
	}

	public List<AbstractPushPolicy> getPushPolicies() {
		return pushPolicies;
	}

	/**
	 * @return the releaseInformation
	 */
	public ReleaseInformation getReleaseInformation() {
		return releaseInformation;
	}


	/**
	 * Sets the releaseInformation
	 *
	 * @param releaseInformation
	 */
	public void setReleaseInformation(ReleaseInformation releaseInformation) {
		this.releaseInformation = releaseInformation;
	}


    /**
     * @param supportedEvents the supportedEvents to set
     */
    public void setSupportedEvents(Class<?>[] supportedEvents) {
        this.supportedEvents = supportedEvents;
    }


    /**
     * @return the supportedEvents
     */
    public Class<?>[] getSupportedEvents() {
        return supportedEvents;
    }



}
