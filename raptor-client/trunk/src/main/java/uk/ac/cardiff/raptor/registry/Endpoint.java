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
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import uk.ac.cardiff.model.event.Event;
import uk.ac.cardiff.raptor.attribute.filtering.AttributeFilterPolicy;
import uk.ac.cardiff.raptor.remoting.policy.PushPolicy;
import uk.ac.cardiff.raptor.store.dao.RaptorDataConnection;

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
	private List<PushPolicy> pushPolicies;

	/** The filter policy defined for this endpoint **/
	private AttributeFilterPolicy attributeFilterPolicy;

	/** Information about what information has been released to this endpoint */
	private ReleaseInformation releaseInformation;

	
	/**
	 * Default constructor. Instantiate <code>releaseInformation</code>
	 */
	public Endpoint(){
		releaseInformation = new ReleaseInformation(serviceEndpoint);
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

	public void setPushPolicies(List<PushPolicy> pushPolicies) {
		this.pushPolicies = pushPolicies;
	}

	public List<PushPolicy> getPushPolicies() {
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



}
