/**
 *
 */
package uk.ac.cardiff.RaptorUA.service.impl;



import org.apache.log4j.Logger;

import uk.ac.cardiff.RaptorUA.engine.UnitAggregatorEngine;
import uk.ac.cardiff.RaptorUA.service.UAProcess;

/**
 * @author philsmart
 *
 */
public class UAProcessImpl implements UAProcess{
    static Logger log = Logger.getLogger(UAProcessImpl.class);
    private UnitAggregatorEngine aggregatorEngine;

    /* (non-Javadoc)
     * @see uk.ac.cardiff.RaptorUA.service.UAProcess#poll()
     */
    public void poll() {
	log.info("Polling ICAs...");
	aggregatorEngine.poll();
	log.info("Done");

    }

    public void setAggregatorEngine(UnitAggregatorEngine aggregatorEngine) {
	this.aggregatorEngine = aggregatorEngine;
    }

    public UnitAggregatorEngine getAggregatorEngine() {
	return aggregatorEngine;
    }

    /* (non-Javadoc)
     * @see uk.ac.cardiff.RaptorUA.service.UAProcess#toStdOut()
     */
    public void toStdOut() {
	aggregatorEngine.toStdOut();

    }

}
