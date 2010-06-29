/**
 *
 */
package uk.ac.cardiff.raptormua.engine;

import org.apache.log4j.Logger;

/**
 * @author philsmart
 *
 */
public class MUAEngine {
	static Logger log = Logger.getLogger(MUAEngine.class);

	private UARegistry uaRegistry;

	/**
	 *
	 */
	public void poll() {
		log.info("Polling Unit Aggregators");

	}

	public void setUaRegistry(UARegistry uaRegistry) {
		this.uaRegistry = uaRegistry;
	}

	public UARegistry getUaRegistry() {
		return uaRegistry;
	}

}
