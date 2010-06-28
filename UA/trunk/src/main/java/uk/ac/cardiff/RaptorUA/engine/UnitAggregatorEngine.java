/**
 *
 */
package uk.ac.cardiff.RaptorUA.engine;

import org.apache.log4j.Logger;

import uk.ac.cardiff.RaptorUA.model.EntryHandler;
import uk.ac.cardiff.RaptorUA.model.ICAEntry;
import uk.ac.cardiff.RaptorUA.model.MemoryEntryHandler;
import uk.ac.cardiff.model.Entry;




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
	for (Entry entryObject : entryHandler.getEntries()){
	    log.info("Stored Entry -- "+entryObject);
	}

    }

}
