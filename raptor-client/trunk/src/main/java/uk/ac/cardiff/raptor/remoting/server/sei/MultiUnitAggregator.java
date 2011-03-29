/**
 *
 */
package uk.ac.cardiff.raptor.remoting.server.sei;

import javax.jws.WebService;

import org.apache.cxf.binding.soap.SoapFault;

import uk.ac.cardiff.model.AdministrativeFunction;
import uk.ac.cardiff.model.report.AggregatorGraphModel;
import uk.ac.cardiff.model.wsmodel.Capabilities;
import uk.ac.cardiff.model.wsmodel.EventPushMessage;
import uk.ac.cardiff.model.wsmodel.StatisticalUnitInformation;


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

	public boolean performAdministrativeFunction(AdministrativeFunction function) throws SoapFault;

	public void addAuthentications(EventPushMessage pushed) throws SoapFault;

}