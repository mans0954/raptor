/**
 *
 */
package uk.ac.cardiff.raptorweb.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.annotation.Secured;

import uk.ac.cardiff.model.Graph.AggregatorGraphModel;
import uk.ac.cardiff.model.StatisticParameters.StatisticType;
import uk.ac.cardiff.model.wsmodel.Capabilities;
import uk.ac.cardiff.model.wsmodel.StatisticalUnitInformation;
import uk.ac.cardiff.raptorweb.engine.ChartProcessor;
import uk.ac.cardiff.raptorweb.engine.RaptorWebEngine;
import uk.ac.cardiff.raptorweb.model.ChartOptions;
import uk.ac.cardiff.raptorweb.model.GraphModel;
import uk.ac.cardiff.raptorweb.model.MUAEntry;
import uk.ac.cardiff.raptorweb.model.RaptorGraphModel;
import uk.ac.cardiff.raptorweb.model.ReportModel;
import uk.ac.cardiff.raptorweb.model.WebSession;
import uk.ac.cardiff.raptorweb.service.GraphService;

/**
 * @author philsmart
 *
 */
public class GraphServiceImpl implements GraphService{

	static Logger log = LoggerFactory.getLogger(GraphServiceImpl.class);

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

	/**
	 * Only retrieves USER level units from those retrieved by the MUA
	 */
	public List getStatisticalUnits(){
		List<StatisticalUnitInformation> units = webEngine.getStatisticalUnits();
		List<StatisticalUnitInformation> userUnits = new ArrayList<StatisticalUnitInformation>();
		for (StatisticalUnitInformation unit : units){
			if (unit.getStatisticParameters().getType()==StatisticType.USER)
			    userUnits.add(unit);
		 }
		return userUnits;
	}

	@Override
	public List getChartData(String statisticalUnitName) {
		// TODO Auto-generated method stub
		return null;
	}

	public void generateExcelReport(WebSession websession){
		webEngine.generateReport(websession,"excel");
	}

	public void generateCSVReport(WebSession websession){
		webEngine.generateReport(websession,"csv");
	}

	/**
	 * loads the reports from the download directory into the graphmodel
	 * @param model
	 */
	public void loadSavedReports(WebSession websession){
		webEngine.loadSavedReports(websession);
	}


	/*
	 * (non-Javadoc)
	 * @see uk.ac.cardiff.raptorweb.service.GraphService#invokeStatisticalUnit(uk.ac.cardiff.raptorweb.model.GraphModel)
	 * Adds the trinidad GraphModel into the Raptor Web Model
	 */
	@Override
	public void invokeStatisticalUnit(WebSession websession) {
	        GraphModel model = websession.getGraphmodel();
		log.info("Graph Service Invoking "+model.getSelectedStatisticalUnit());
		AggregatorGraphModel gmodel = webEngine.invokeStatisticalUnit(model.getSelectedStatisticalUnit());
		if (gmodel!=null){
		    model.setCurrentTableGraph(ChartProcessor.constructRaptorTableChartModel(gmodel));
		    model.setCurrentGraph(ChartProcessor.constructRaptorGraphModel(gmodel));
		    model.setProcessingResult("Done");
		    //auto select chart heights based on heuristic
		    if (model.getCurrentTableGraph().getRows().size()<50)
			model.getChartOptions().setChartHeight(ChartOptions.ChartHeight.SMALL);
		    else if (model.getCurrentTableGraph().getRows().size()<100)
			model.getChartOptions().setChartHeight(ChartOptions.ChartHeight.MEDIUM);
		    else
			model.getChartOptions().setChartHeight(ChartOptions.ChartHeight.LARGE);

		}
		else{
		    log.error("Chart model came back from the MUA as null");
		    model.setCurrentTableGraph(null);
		    model.setCurrentGraph(null);
		    model.setProcessingResult("The statistic failed to produce a graphable result");
		}


	}

	/* (non-Javadoc)
	 * @see uk.ac.cardiff.raptorweb.service.GraphService#updateMUAStatistic(uk.ac.cardiff.raptorweb.model.GraphModel)
	 */
	@Override
	public void updateMUAStatistic(WebSession websession) {
	    webEngine.updateMUAStatistic(websession.getGraphmodel());

	}

	public void removeReport(WebSession websession){
	    webEngine.removeReport(websession.getReportmodel());
	}

	@Override
	public Capabilities getAttachedCapabilities() {
	    return webEngine.getAttachedCapabilities();
	}


}
