/**
 *
 */
package uk.ac.cardiff.raptorweb.service.impl;

import java.util.List;

import uk.ac.cardiff.model.wsmodel.Capabilities;
import uk.ac.cardiff.raptorweb.engine.RaptorWebEngine;
import uk.ac.cardiff.raptorweb.model.SetupModel;
import uk.ac.cardiff.raptorweb.service.SetupService;

/**
 * @author philsmart
 *
 */
public class SetupServiceImpl implements SetupService{

	private RaptorWebEngine webEngine;



	/* (non-Javadoc)
	 * @see uk.ac.cardiff.raptorweb.service.SetupService#getStatisticalServices()
	 */
	@Override
	public List getStatisticalServices() {
		// TODO Auto-generated method stub
		return null;
	}

	public void setAttachedEndpoint(SetupModel model){
	    webEngine.setAttached(model.getSelectedEndpoint());
	}


	public List getAttached(){
		return webEngine.getAttached();
	}

	public void setWebEngine(RaptorWebEngine webEngine) {
		this.webEngine = webEngine;
	}

	public RaptorWebEngine getWebEngine() {
		return webEngine;
	}

	/* (non-Javadoc)
	 * @see uk.ac.cardiff.raptorweb.service.SetupService#getCapabilities(uk.ac.cardiff.raptorweb.model.SetupModel)
	 */
	@Override
	public void getCapabilities(SetupModel model) {
		model.setSelectEndpointCapabilities(webEngine.getCapabilities(model.getSelectedEndpoint()));
	}

}
