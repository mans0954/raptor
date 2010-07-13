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
	
	public List getStatisticalUnits(){
		/* get the statistical methods from the currently attached MUA */
		MUAEntry attached = null;
		for (MUAEntry entry : registry.getUAEntries()){
			if (entry.getIsAttached()){
				attached = entry;
			}
		}
		Capabilities capabilities = ServiceEndpointInterface.discoverMUACapabilities(attached.getServiceEndpoint());
		if (!capabilities.isError()) return capabilities.getStatisticalServices();
		return null;
	}

	/**
	 * @param selectedEndpoint
	 * @return
	 */
	public Capabilities getCapabilities(MUAEntry selectedEndpoint) {
			return ServiceEndpointInterface.discoverMUACapabilities(selectedEndpoint.getServiceEndpoint());
	}

	public MUAEntry getCurrentlyAttached() {
		for (MUAEntry entry : registry.getUAEntries()){
			if (entry.getIsAttached()){
				return entry;
			}
		}
		return null;
	}

	public void invokeStatisticalModel(String selectedStatisticalUnit) {
		
		
	}




}
