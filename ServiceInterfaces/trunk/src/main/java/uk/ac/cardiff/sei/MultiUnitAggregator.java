/**
 *
 */
package uk.ac.cardiff.sei;

import javax.jws.WebService;

import org.sdmx.resources.sdmxml.schemas.v2_0.message.CompactDataType;
import org.sdmx.resources.sdmxml.schemas.v2_0.message.MessageType;

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

	//public void setParametersForStatisticalUnit(String dummy);

	public MessageType getExampleSDMX();

}
