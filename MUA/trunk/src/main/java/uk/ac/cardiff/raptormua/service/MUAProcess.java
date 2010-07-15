/**
 *
 */
package uk.ac.cardiff.raptormua.service;

import uk.ac.cardiff.model.Graph.AggregatorGraphModel;
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
	public AggregatorGraphModel performStatistic(String statisticName);

	/**
	 * @return
	 */
	public Capabilities getCapabilities();
}
