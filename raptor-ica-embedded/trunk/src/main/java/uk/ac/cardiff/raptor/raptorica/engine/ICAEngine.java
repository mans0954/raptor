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
package uk.ac.cardiff.raptor.raptorica.engine;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import uk.ac.cardiff.raptor.exceptions.ReleaseFailureException;
import uk.ac.cardiff.raptor.raptorica.dao.external.AuthenticationInput;
import uk.ac.cardiff.raptor.registry.EventReleaseEngine;
import uk.ac.cardiff.raptor.remoting.client.EventReleaseClient;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import uk.ac.cardiff.model.ClientMetadata;
import uk.ac.cardiff.model.Event;


/**
 * @author philsmart
 *
 * Responsible for ALL low level capture operations
 */
public class ICAEngine {

    	/** Class Logger*/
    	private final Logger log = LoggerFactory.getLogger(ICAEngine.class);

	private DataAccessRegister authRegister;
	private EventReleaseClient eventReleaseClient;
	private ClientMetadata icaMetadata;

	public ICAEngine(){
	    	log.info("ICA Capture Engine is running...");
	}

	public void capturePerform() throws Exception{
		for (AuthenticationInput authI : authRegister.getAuthenticationModules()){
			log.info("Capturing from {}",authI);
			authI.parse();
		}
	}

	public void setAuthRegister(DataAccessRegister authRegister) {
		this.authRegister = authRegister;
	}

	public DataAccessRegister getAuthRegister() {
		return authRegister;
	}

	/**
	 * This method removes all stored entries, in this way the ICA must only talk to
	 * one endpoint, otherwise the operation is nonmonotoinc whereas it should be monotonic
	 * remove this method if more sophisticated operation is desired.
	 */
	private void retrieveTransactionFinished() {
	    log.debug("Retrieve Transaction Finished, entries are being removed from the ICA...");
	    for (AuthenticationInput authI : authRegister.getAuthenticationModules()){
		authI.removeAllEntries();
	    }
	    log.debug("Retrieve Transaction Finished, entries are being removed form the ICA...done");

	}

	/**
	 * Converts all information from all modules into a single list that is sent to the release engine
	 * @return
	 */
	public boolean release() {
		List<Event> eventsToSend = new ArrayList<Event>();
	    boolean success =false;
		try {
			success = eventReleaseClient.release(eventsToSend, getIcaMetadata());
		} catch (ReleaseFailureException e) {
			log.error("Release failed ",e);
		}
	    if (success) retrieveTransactionFinished();
	    return success;
	}


	public void setIcaMetadata(ClientMetadata icaMetadata) {
	    this.icaMetadata = icaMetadata;
	}

	public ClientMetadata getIcaMetadata() {
	    return icaMetadata;
	}

	public void setEventReleaseClient(EventReleaseClient eventReleaseClient) {
		this.eventReleaseClient = eventReleaseClient;
	}

	public EventReleaseClient getEventReleaseClient() {
		return eventReleaseClient;
	}


}
