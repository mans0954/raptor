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

import uk.ac.cardiff.model.Entry;
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
	Set<Entry> allEntries = new LinkedHashSet<Entry>();
	for (AuthenticationInput authI : authenticationModules){
	    log.debug("AuthenticationInput: {}, has {} entries",authI,authI.getEntryHandler().getEntries().size());
	    allEntries.addAll(authI.getEntryHandler().getEntries());
	}
	boolean releasedtoAll = true;
	int releaseCount=0;
	for (UAEntry uaEntry : uaRegistry.getUAEntries()){
	    boolean shouldRelease = (uaEntry.getPushPolicy().getPushOnOrAfterNoEntries() <= allEntries.size());
	    log.debug("UA Entry: {}, should release {}",uaEntry.getServiceEndpoint(),shouldRelease);
	    Set<Entry> filteredEntries = filterAttributes(uaEntry, allEntries);
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

    private Set<Entry> filterAttributes(UAEntry uaEntry, Set<Entry> allEntries){
	return AtrributeFilterEngine.filter(uaEntry.getAttributeFilterPolicy(), allEntries);
    }

    private ICAEntryPush constructICAEntryPush(ICAMetadata icaMetaData, Set<Entry> entries){
	ICAEntryPush pushMessage = new ICAEntryPush();
	pushMessage.setIcaMetaData(icaMetaData);
	pushMessage.setEntries(entries);
	pushMessage.setTimeOfPush(new Date(System.currentTimeMillis()));
	return pushMessage;
    }

}
