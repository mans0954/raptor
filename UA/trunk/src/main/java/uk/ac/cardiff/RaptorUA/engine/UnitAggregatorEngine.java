/**
 *
 */
package uk.ac.cardiff.RaptorUA.engine;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import uk.ac.cardiff.RaptorUA.model.EntryHandler;
import uk.ac.cardiff.RaptorUA.model.ICAEntry;
import uk.ac.cardiff.RaptorUA.model.MemoryEntryHandler;
import uk.ac.cardiff.model.Entry;
import uk.ac.cardiff.model.ShibbolethEntry;




/**
 * @author philsmart
 *
 */
public class UnitAggregatorEngine {

    private ICARegistry icaRegistry;
    private EntryHandler entryHandler;
    static Logger log = Logger.getLogger(UnitAggregatorEngine.class);

    public UnitAggregatorEngine(){
	log.info("Setup Unit Aggregator Engine...");
	entryHandler = new MemoryEntryHandler();
	log.info("Unit Aggregator Engine is running...");

    }

    public void setIcaRegistry(ICARegistry icaRegistry) {
	this.icaRegistry = icaRegistry;
    }

    public ICARegistry getIcaRegistry() {
	return icaRegistry;
    }

    /**
     *
     */
    public void poll() {
	for (ICAEntry ica : icaRegistry.getICAEntries()){
	    entryHandler.addEntries(ica.getAllAuthentications());
	}
	/* test output all entries*/
	toStdOut();

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
    public List getAllAuthentications() {
	ArrayList<Entry> returnedAuths = new ArrayList();
	for (Entry entry : entryHandler.getEntries()) returnedAuths.add(entry);

	/* is this the best way to stop duplicated being retrieved - NO
	 * we will probably want to maintain the data in the UA*/
	retrieveTransactionFinished();
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

}
