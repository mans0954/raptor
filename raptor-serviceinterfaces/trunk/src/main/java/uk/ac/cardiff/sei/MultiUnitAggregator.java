/**
 *
 */
package uk.ac.cardiff.sei;

import javax.jws.WebService;

import org.apache.cxf.binding.soap.SoapFault;
import org.sdmx.resources.sdmxml.schemas.v2_0.message.CompactDataType;
import org.sdmx.resources.sdmxml.schemas.v2_0.message.MessageType;

import uk.ac.cardiff.model.AdministrativeFunction;
import uk.ac.cardiff.model.Graph.AggregatorGraphModel;
import uk.ac.cardiff.model.wsmodel.Capabilities;
import uk.ac.cardiff.model.wsmodel.ICAEntryPush;
import uk.ac.cardiff.model.wsmodel.StatisticalUnitInformation;
import uk.ac.cardiff.model.wsmodel.UAEntryPush;

/**
 * @author philsmart
 *
 */

@WebService
public interface MultiUnitAggregator {

	public String getVersion();


	public Capabilities getCapabilities();

	public void updateStatisticalUnit(StatisticalUnitInformation statisticalUnitInformation) throws SoapFault;

	public AggregatorGraphModel invokeStatisticalUnit(String statisticName) throws SoapFault;

	/**
	 * A single shot interface that both updates and then performs a statistic (returning the result)
	 * in one go. This is useful if you want to perform both operations while there is a lock on the class
	 * so as other operations do not take place between update and invoke.
	 *
	 * @param statisticalUnitInformation
	 * @return
	 */
	public AggregatorGraphModel updateAndInvokeStatisticalUnit(StatisticalUnitInformation statisticalUnitInformation) throws SoapFault;

	public MessageType getExampleSDMX();

	public boolean performAdministrativeFunction(AdministrativeFunction function) throws SoapFault;

	public void addAuthentications(UAEntryPush pushed);

}
