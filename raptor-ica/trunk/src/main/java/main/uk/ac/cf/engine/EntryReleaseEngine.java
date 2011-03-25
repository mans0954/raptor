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
package main.uk.ac.cf.engine;

import java.util.Date;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import main.uk.ac.cf.dao.external.AuthenticationInput;
import main.uk.ac.cf.engine.sei.ServiceEndpointInterface;
import main.uk.ac.cf.model.UAEntry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import uk.ac.cardiff.model.Event;
import uk.ac.cardiff.model.ICAMetadata;
import uk.ac.cardiff.model.wsmodel.ICAEntryPush;


/**
 * @author philsmart
 *
 */
public class EntryReleaseEngine {
    static Logger log = LoggerFactory.getLogger(EntryReleaseEngine.class);

    /**
     * @param authenticationModules
     */
    public boolean release(UARegistry uaRegistry, List<AuthenticationInput> authenticationModules, ICAMetadata icaMetaData) {
	Set<Event> allEntries = new LinkedHashSet<Event>();
	for (AuthenticationInput authI : authenticationModules){
	    log.debug("AuthenticationInput: {}, has {} entries",authI,authI.getEntryHandler().getEntries().size());
	    allEntries.addAll(authI.getEntryHandler().getEntries());
	}
	boolean releasedtoAll = true;
	int releaseCount=0;
	for (UAEntry uaEntry : uaRegistry.getUAEntries()){
	    boolean shouldRelease = (uaEntry.getPushPolicy().getPushOnOrAfterNoEntries() <= allEntries.size());
	    log.debug("UA Entry: {}, should release {}",uaEntry.getServiceEndpoint(),shouldRelease);
	    Set<Event> filteredEntries = filterAttributes(uaEntry, allEntries);
	    ICAEntryPush pushMessage = constructICAEntryPush(icaMetaData, filteredEntries);
	    if (shouldRelease) {
		log.debug("Pushing {} entries to the UA [{}]",filteredEntries.size(),uaEntry.getServiceEndpoint());
		boolean releaseSuccess = ServiceEndpointInterface.sendAuthentications(pushMessage, uaEntry.getServiceEndpoint());
		log.debug("Release to [{}] succeeded {}",uaEntry.getServiceEndpoint(),releaseSuccess);
		if (releaseSuccess==false)  releasedtoAll =false;
		else releaseCount++;
	    }
	    else{
		releasedtoAll=false;
	    }

	}
	log.debug("Released to {} listening UAs, out of a total of {}",releaseCount,uaRegistry.getUAEntries().size());

	return releasedtoAll;


    }

    public ICAEntryPush getRelease(List<AuthenticationInput> authenticationModules, ICAMetadata icaMetaData) {
	Set<Event> allEntries = new LinkedHashSet<Event>();
	for (AuthenticationInput authI : authenticationModules){
	    log.debug("AuthenticationInput: {}, has {} entries",authI,authI.getEntryHandler().getEntries().size());
	    allEntries.addAll(authI.getEntryHandler().getEntries());
	}
	 ICAEntryPush pushMessage = constructICAEntryPush(icaMetaData, allEntries);
	 return pushMessage;




    }

    /**
     * Filters the attributes from the entries being pushed to the input <code>uaEntry</code>.
     * If no filter policy has been defined, no work is done, and the input <code>allEntries</code>
     * is returned without modification
     *
     * @param uaEntry
     * @param allEntries
     * @return
     */
    private Set<Event> filterAttributes(UAEntry uaEntry, Set<Event> allEntries){
	if (uaEntry.getAttributeFilterPolicy()==null) return allEntries;
	return AtrributeFilterEngine.filter(uaEntry.getAttributeFilterPolicy(), allEntries);
    }

    private ICAEntryPush constructICAEntryPush(ICAMetadata icaMetaData, Set<Event> entries){
	ICAEntryPush pushMessage = new ICAEntryPush();
	pushMessage.setIcaMetaData(icaMetaData);
	pushMessage.setEntries(entries);
	pushMessage.setTimeOfPush(new Date(System.currentTimeMillis()));
	return pushMessage;
    }

}
