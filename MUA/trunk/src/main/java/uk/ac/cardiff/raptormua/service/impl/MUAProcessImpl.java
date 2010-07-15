/**
 *
 */
package uk.ac.cardiff.raptormua.service.impl;

import org.apache.log4j.Logger;

import uk.ac.cardiff.model.Graph.AggregatorGraphModel;
import uk.ac.cardiff.model.wsmodel.Capabilities;
import uk.ac.cardiff.raptormua.engine.MUAEngine;
import uk.ac.cardiff.raptormua.service.MUAProcess;

/**
 * @author philsmart
 *
 */
public class MUAProcessImpl implements MUAProcess{
	static Logger log = Logger.getLogger(MUAProcessImpl.class);

	private MUAEngine engine;

	public void setEngine(MUAEngine engine) {
		this.engine = engine;
	}

	public MUAEngine getEngine() {
		return engine;
	}

	/* (non-Javadoc)
	 * @see uk.ac.cardiff.raptormua.service.MUAProcess#poll()
	 */
	public void poll() {
		engine.poll();

	}

	/* (non-Javadoc)
	 * @see uk.ac.cardiff.raptormua.service.MUAProcess#performStatistics(java.lang.String)
	 */
	public AggregatorGraphModel performStatistic(String statisticName) {
		log.info("WS Call for perform statistic - "+statisticName);
		return engine.performStatistic(statisticName);

	}

	/* (non-Javadoc)
	 * @see uk.ac.cardiff.raptormua.service.MUAProcess#getCapabilities()
	 */
	public Capabilities getCapabilities() {
		log.info("WS call for getting capabilities");
		return engine.getCapabilities();
	}

}
