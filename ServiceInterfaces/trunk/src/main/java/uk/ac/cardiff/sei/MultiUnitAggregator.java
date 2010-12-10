/**
 *
 */
package uk.ac.cardiff.sei;

import javax.jws.WebService;

import org.sdmx.resources.sdmxml.schemas.v2_0.message.CompactDataType;
import org.sdmx.resources.sdmxml.schemas.v2_0.message.MessageType;

import uk.ac.cardiff.model.Graph.AggregatorGraphModel;
import uk.ac.cardiff.model.wsmodel.Capabilities;
import uk.ac.cardiff.model.wsmodel.StatisticalUnitInformation;

/**
 * @author philsmart
 *
 */

@WebService
public interface MultiUnitAggregator {

	public String getVersion();


	public Capabilities getCapabilities();

	public void updateStatisticalUnit(StatisticalUnitInformation statisticalUnitInformation);

	public AggregatorGraphModel invokeStatisticalUnit(String statisticName);

	//public void setParametersForStatisticalUnit(String dummy);

	public MessageType getExampleSDMX();

}
