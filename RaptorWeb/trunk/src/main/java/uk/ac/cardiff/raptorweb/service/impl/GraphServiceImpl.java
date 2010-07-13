/**
 *
 */
package uk.ac.cardiff.raptorweb.service.impl;

import java.util.List;

import uk.ac.cardiff.raptorweb.engine.RaptorWebEngine;
import uk.ac.cardiff.raptorweb.model.GraphModel;
import uk.ac.cardiff.raptorweb.model.MUAEntry;
import uk.ac.cardiff.raptorweb.service.GraphService;

/**
 * @author philsmart
 *
 */
public class GraphServiceImpl implements GraphService{

	private RaptorWebEngine webEngine;

	public void setWebEngine(RaptorWebEngine webEngine) {
		this.webEngine = webEngine;
	}

	public RaptorWebEngine getWebEngine() {
		return webEngine;
	}
	
	public List getAttached(){
		return webEngine.getAttached();
	}
	
	public MUAEntry getCurrentlyAttached(){
		return webEngine.getCurrentlyAttached();
	}
	
	@Override
	public List getStatisticalUnits(){
		return webEngine.getStatisticalUnits();
	}

	@Override
	public List getChartData(String statisticalUnitName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void invokeStatisticalUnit(GraphModel model) {
		webEngine.invokeStatisticalModel(model.getSelectedStatisticalUnit());
		
	}

}
