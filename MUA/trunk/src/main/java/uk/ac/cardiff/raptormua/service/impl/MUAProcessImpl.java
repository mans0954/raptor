/**
 *
 */
package uk.ac.cardiff.raptormua.service.impl;

import org.apache.log4j.Logger;

import uk.ac.cardiff.raptormua.engine.MUAEngine;
import uk.ac.cardiff.raptormua.engine.UARegistry;
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

}
