/**
 *
 */
package uk.ac.cardiff.sei;

import javax.jws.WebService;

import uk.ac.cardiff.model.Graph.AggregatorGraphModel;
import uk.ac.cardiff.model.wsmodel.Capabilities;

/**
 * @author philsmart
 *
 */

@WebService
public interface MultiUnitAggregator {

	public String getVersion();


	public Capabilities getCapabilities();

	public AggregatorGraphModel invokeStatisticalUnit(String statisticName);

}
