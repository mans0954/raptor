/**
 *
 */
package uk.ac.cardiff.raptorweb.model;

import java.io.Serializable;



import org.apache.log4j.Logger;

import uk.ac.cardiff.model.wsmodel.Capabilities;

/**
 * @author philsmart
 *
 */
public class SetupModel implements Serializable{

	static Logger log = Logger.getLogger(SetupModel.class);

	private MUAEntry selectedEndpoint;
	private Capabilities selectEndpointCapabilities;



	public void setSelectEndpointCapabilities(Capabilities selectEndpointCapabilities) {
		this.selectEndpointCapabilities = selectEndpointCapabilities;
	}
	public Capabilities getSelectEndpointCapabilities() {
		return selectEndpointCapabilities;
	}
	public void setSelectedEndpoint(MUAEntry selectedEndpoint) {
		log.debug("Setting Selected Endpoint..."+selectedEndpoint);
		this.selectedEndpoint = selectedEndpoint;
	}
	public MUAEntry getSelectedEndpoint() {
		return selectedEndpoint;
	}

}
