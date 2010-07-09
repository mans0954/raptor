/**
 *
 */
package uk.ac.cardiff.raptormua.service;

import java.util.List;

import uk.ac.cardiff.model.wsmodel.Capabilities;

/**
 * @author philsmart
 *
 */
public interface MUAProcess {


	public void poll();

	/**
	 * @param statisticName
	 */
	public void performStatistic(String statisticName);

	/**
	 * @return
	 */
	public Capabilities getCapabilities();
}
