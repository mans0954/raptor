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

package uk.ac.cardiff.raptor.registry;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import uk.ac.cardiff.raptor.store.dao.RaptorDataConnection;

/**
 * @author philsmart handles the list of attached ICAs as injected by Spring
 */
public class EndpointRegistry {

	/** Class logger. */
	private final Logger log = LoggerFactory.getLogger(EndpointRegistry.class);

	/** List of endpoints for invoking methods on */
	private List<Endpoint> endpoints;
	
	/** Whether release information should be persisted. */
	private boolean persistReleaseInformation;
	
	/** The data connection class to use for persisting
	 * release information
	 */
	private RaptorDataConnection dataConnection;

	public EndpointRegistry() {
		setEndpoints(new ArrayList<Endpoint>());
	}

	public void setEndpoints(List<Endpoint> endpoints) {
		for (Endpoint endpoint : endpoints){
			log.info("Registering Service Endpoint: {}, persisting event release information {}", endpoint.getServiceEndpoint(),isPersistReleaseInformation());
			if (isPersistReleaseInformation()){
				ReleaseInformation releaseInformation = loadReleaseInformation(endpoint);
				if (releaseInformation!=null){
					endpoint.setReleaseInformation(releaseInformation);
				}
			}
			
		}
		this.endpoints = endpoints;
	}

	public List<Endpoint> getEndpoints() {
		return endpoints;
	}
	

	public void storeReleaseInformationIfEnabled() {
		if (!isPersistReleaseInformation())
			return;
			
		for (Endpoint entry : endpoints){
			dataConnection.save(entry.getReleaseInformation());
		}
		
	}
	
	
	public ReleaseInformation loadReleaseInformation(Endpoint endpoint){
		List releaseInfoResults = dataConnection.runQuery("from ReleaseInformation where serviceEndpoint=?",new Object[]{endpoint.getServiceEndpoint()});
		if (releaseInfoResults==null){
			log.error("Loading error...no release information found, blank release information used");
		}
		if (releaseInfoResults.size()>1){
			log.error("Loading error...ambiguity in the persisted release information, too many results");
		}
		if (releaseInfoResults.size()==1){
			return (ReleaseInformation) releaseInfoResults.get(0);
		}
		return null;
		
	}
	

	/**
	 * @param persistReleaseInformation the persistReleaseInformation to set
	 */
	public void setPersistReleaseInformation(boolean persistReleaseInformation) {
		this.persistReleaseInformation = persistReleaseInformation;
	}

	/**
	 * @return the persistReleaseInformation
	 */
	public boolean isPersistReleaseInformation() {
		return persistReleaseInformation;
	}

	/**
	 * @param dataConnection the dataConnection to set
	 */
	public void setDataConnection(RaptorDataConnection dataConnection) {
		this.dataConnection = dataConnection;
	}

	/**
	 * @return the dataConnection
	 */
	public RaptorDataConnection getDataConnection() {
		return dataConnection;
	}

}
