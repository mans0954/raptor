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

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import uk.ac.cardiff.RaptorUA.model.EntryHandler;
import uk.ac.cardiff.RaptorUA.model.ICAEntry;
import uk.ac.cardiff.RaptorUA.model.MemoryEntryHandler;
import uk.ac.cardiff.model.Entry;
import uk.ac.cardiff.model.ShibbolethEntry;
import uk.ac.cardiff.model.wsmodel.ICAEntryPush;




/**
 * @author philsmart
 *
 */
public class UnitAggregatorEngine {

    private ICARegistry icaRegistry;
    private EntryHandler entryHandler;

    /* class logger */
    static Logger log = LoggerFactory.getLogger(UnitAggregatorEngine.class);

    public UnitAggregatorEngine(){
	log.info("Setup Unit Aggregator Engine...");
	log.info("Unit Aggregator Engine is running...");

    }

    public void setIcaRegistry(ICARegistry icaRegistry) {
	this.icaRegistry = icaRegistry;
    }

    public ICARegistry getIcaRegistry() {
	return icaRegistry;
    }


    public void poll() {
	for (ICAEntry ica : icaRegistry.getICAEntries()){
	    entryHandler.addEntries(ica.getAllAuthentications());
	}
	entryHandler.endTransaction();
	/* test output all entries*/
	//toStdOut();

    }

    public void setEntryHandler(EntryHandler entryHandler) {
	this.entryHandler = entryHandler;
    }

    public EntryHandler getEntryHandler() {
	return entryHandler;
    }

    /**
     *
     */
    public void toStdOut() {
	log.info("Outputting All Entries to StdOut");
	int count=0;
	for (Entry entryObject : entryHandler.getEntries()){
	    if (entryObject instanceof ShibbolethEntry)
		log.info(count+". Stored Entry -- "+((ShibbolethEntry)entryObject));
	    count++;
	}

    }

    /**
     * @return
     */
    public Set getAllAuthentications() {
	LinkedHashSet<Entry> returnedAuths = new LinkedHashSet();
	for (Entry entry : entryHandler.getEntries()) returnedAuths.add(entry);

	/* is this the best way to stop duplicated being retrieved - NO
	 * we will probably want to maintain the data in the UA*/
	//retrieveTransactionFinished();
	return returnedAuths;



    }

    /**
	 * This method removes all stored entries, in this way the ICA must only talk to
	 * one UA, otherwise the operation is nonmonotoinc whereas it should be monotonic
	 * remove this method if more sophisticated operation is desired.
	 */
	private void retrieveTransactionFinished() {
	    log.debug("Retrieve Transaction Finished, entries are being removed from the UA...");
	    entryHandler.removeAllEntries();
	    log.debug("Retrieve Transaction Finished, entries are being removed.from the UA..done");

	}

    /**
     * @param pushed
     */
    public void addAuthentications(ICAEntryPush pushed) {
	log.info("Committing {} entries to the entryHandler",pushed.getEntries().size());
	entryHandler.addEntries(pushed.getEntries());
	log.info("EntryHandler now contains {} entries",entryHandler.getEntries().size());

    }

}
