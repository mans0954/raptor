/**
 *
 */
package uk.ac.cardiff.RaptorUA.engine;

import org.apache.log4j.Logger;

import uk.ac.cardiff.RaptorUA.model.ICAEntry;




/**
 * @author philsmart
 *
 */
public class UnitAggregatorEngine {

    private ICARegistry icaRegistry;
    static Logger log = Logger.getLogger(UnitAggregatorEngine.class);

    public UnitAggregatorEngine(){
	log.info("Aggregator Engine is running...");
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
	    ica.getAllAuthentications();
	}

    }

}
