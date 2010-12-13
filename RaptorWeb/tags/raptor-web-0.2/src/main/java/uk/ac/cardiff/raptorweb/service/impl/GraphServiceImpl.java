/**
 *
 */
package uk.ac.cardiff.raptorweb.service.impl;

import java.util.List;

import org.apache.log4j.Logger;

import uk.ac.cardiff.model.Graph.AggregatorGraphModel;
import uk.ac.cardiff.model.wsmodel.Capabilities;
import uk.ac.cardiff.raptorweb.engine.ChartProcessor;
import uk.ac.cardiff.raptorweb.engine.RaptorWebEngine;
import uk.ac.cardiff.raptorweb.model.GraphModel;
import uk.ac.cardiff.raptorweb.model.MUAEntry;
import uk.ac.cardiff.raptorweb.model.RaptorGraphModel;
import uk.ac.cardiff.raptorweb.model.ReportModel;
import uk.ac.cardiff.raptorweb.service.GraphService;

/**
 * @author philsmart
 *
 */
public class GraphServiceImpl implements GraphService{

	static Logger log = Logger.getLogger(GraphServiceImpl.class);

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

	public void generateExcelReport(GraphModel model, ReportModel report){
		webEngine.generateReport(model,"excel", report);
	}

	public void generateCSVReport(GraphModel model, ReportModel report){
		webEngine.generateReport(model,"csv", report);
	}

	/**
	 * loads the reports from the download directory into the graphmodel
	 * @param model
	 */
	public void loadSavedReports(ReportModel model){
		webEngine.loadSavedReports(model);
	}


	/*
	 * (non-Javadoc)
	 * @see uk.ac.cardiff.raptorweb.service.GraphService#invokeStatisticalUnit(uk.ac.cardiff.raptorweb.model.GraphModel)
	 * Adds the trinidad GraphModel into the Raptor Web Model
	 */
	@Override
	public void invokeStatisticalUnit(GraphModel model) {
		log.info("Graph Service Invoking "+model.getSelectedStatisticalUnit());
		AggregatorGraphModel gmodel = webEngine.invokeStatisticalUnit(model.getSelectedStatisticalUnit());
		if (gmodel!=null){
		    model.setCurrentTableGraph(ChartProcessor.constructRaptorTableChartModel(gmodel));
		    model.setCurrentGraph(ChartProcessor.constructRaptorGraphModel(gmodel));
		}
		else{
		    model.setCurrentTableGraph(null);
		    model.setCurrentGraph(null);
		}


	}

	/* (non-Javadoc)
	 * @see uk.ac.cardiff.raptorweb.service.GraphService#updateMUAStatistic(uk.ac.cardiff.raptorweb.model.GraphModel)
	 */
	@Override
	public void updateMUAStatistic(GraphModel model) {
	    webEngine.updateMUAStatistic(model);

	}

	public void removeReport(ReportModel model){
	    webEngine.removeReport(model);
	}

	@Override
	public Capabilities getAttachedCapabilities() {
	    return webEngine.getAttachedCapabilities();
	}


}
