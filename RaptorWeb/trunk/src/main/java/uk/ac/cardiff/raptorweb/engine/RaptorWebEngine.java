/**
 *
 */
package uk.ac.cardiff.raptorweb.engine;

import java.util.List;

import uk.ac.cardiff.model.wsmodel.Capabilities;
import uk.ac.cardiff.raptorweb.model.MUAEntry;
import uk.ac.cardiff.raptorweb.sei.ServiceEndpointInterface;

/**
 * @author philsmart
 *
 */
public class RaptorWebEngine {

	private MUARegistry registry;

	/**
	 * @return
	 */
	public List getAttached() {

		return registry.getUAEntries();
	}

	public void setRegistry(MUARegistry registry) {
		this.registry = registry;
	}

	public MUARegistry getRegistry() {
		return registry;
	}

	/**
	 * @param selectedEndpoint
	 * @return
	 */
	public Capabilities getCapabilities(MUAEntry selectedEndpoint) {
			return ServiceEndpointInterface.discoverMUACapabilities(selectedEndpoint.getServiceEndpoint());
	}




}
