/**
 *
 */
package uk.ac.cardiff.raptorweb.service.impl;

import java.util.List;

import uk.ac.cardiff.model.wsmodel.Capabilities;
import uk.ac.cardiff.raptorweb.engine.RaptorWebEngine;
import uk.ac.cardiff.raptorweb.model.MUAEntry;
import uk.ac.cardiff.raptorweb.model.SetupModel;
import uk.ac.cardiff.raptorweb.model.WebSession;
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

	public void setAttachedEndpoint(WebSession websession){
	    webEngine.setAttached(websession.getSetupmodel().getSelectedEndpoint());
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
	public void getCapabilities(WebSession websession) {
	    websession.getSetupmodel().setSelectEndpointCapabilities(webEngine.getCapabilities(websession.getSetupmodel().getSelectedEndpoint()));
	}

	public void deleteAllEntriesFromAttachedMUA(WebSession websession){
	    	webEngine.deleteAllEntriesFromAttachedMUA(websession.getSetupmodel());
	}

	/* (non-Javadoc)
	 * @see uk.ac.cardiff.raptorweb.service.SetupService#hasAttached()
	 */
	@Override
	public boolean getHasAttached() {
	    return webEngine.hasAttached();
	}

	/* (non-Javadoc)
	 * @see uk.ac.cardiff.raptorweb.service.SetupService#getAttachedCapabilities()
	 */
	@Override
	public Capabilities getAttachedCapabilities() {
	    return webEngine.getAttachedCapabilities();
	}

	public MUAEntry getCurrentlyAttached(){
		return webEngine.getCurrentlyAttached();
	}

}
