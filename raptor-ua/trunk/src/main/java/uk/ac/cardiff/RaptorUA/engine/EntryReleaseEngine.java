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
package uk.ac.cardiff.RaptorUA.engine;

import java.util.Date;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import uk.ac.cardiff.RaptorUA.engine.sei.ServiceEndpointInterface;
import uk.ac.cardiff.RaptorUA.model.EntryHandler;
import uk.ac.cardiff.RaptorUA.model.MUAEntry;
import uk.ac.cardiff.model.Entry;
import uk.ac.cardiff.model.ICAMetadata;
import uk.ac.cardiff.model.UAMetadata;
import uk.ac.cardiff.model.wsmodel.UAEntryPush;


/**
 * @author philsmart
 *
 */
public class EntryReleaseEngine {
    static Logger log = LoggerFactory.getLogger(EntryReleaseEngine.class);

    /**
     * @param authenticationModules
     */
    public boolean release(EntryHandler entryHandler, MUARegistry muaRegistry, UAMetadata uaMetaData) {
	Set<Entry> allEntries = entryHandler.getEntries();
	log.debug("Releasing {} entries to approved MUAs",entryHandler.getEntries().size());
	boolean releasedtoAll = true;
	int releaseCount=0;
	for (MUAEntry muaEntry : muaRegistry.getMUAEntries()){
	    boolean shouldRelease = (muaEntry.getPushPolicy().getPushOnOrAfterNoEntries() <= allEntries.size());
	    log.debug("MUA Entry: {}, should release {}",muaEntry.getServiceEndpoint(),shouldRelease);
	    UAEntryPush pushMessage = constructEntryPush(uaMetaData, allEntries);
	    if (shouldRelease) {
		log.debug("Pushing {} entries to the MUA [{}]",allEntries.size(),muaEntry.getServiceEndpoint());
		boolean releaseSuccess = ServiceEndpointInterface.sendAuthentications(pushMessage, muaEntry.getServiceEndpoint());
		log.debug("Release to [{}] succeeded {}",muaEntry.getServiceEndpoint(),releaseSuccess);
		if (releaseSuccess==false)  releasedtoAll =false;
		else releaseCount++;
	    }
	    else{
		releasedtoAll=false;
	    }

	}
	log.debug("Released to {} listening MUAs, out of a total of {}",releaseCount,muaRegistry.getMUAEntries().size());

	return releasedtoAll;


    }

    private UAEntryPush constructEntryPush(UAMetadata uaMetaData, Set<Entry> entries){
	UAEntryPush pushMessage = new UAEntryPush();
	pushMessage.setUaMetaData(uaMetaData);
	pushMessage.setEntries(entries);
	pushMessage.setTimeOfPush(new Date(System.currentTimeMillis()));
	return pushMessage;
    }

}
