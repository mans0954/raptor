/**
 * Copyright (C) 2010 Cardiff University, Wales <smartp@cf.ac.uk>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
/**
 *
 */
package uk.ac.cardiff.raptorweb.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.annotation.Secured;

import uk.ac.cardiff.model.report.AggregatorGraphModel;
import uk.ac.cardiff.model.wsmodel.Capabilities;
import uk.ac.cardiff.model.wsmodel.ProcessorInformation;
import uk.ac.cardiff.model.wsmodel.StatisticalUnitInformation;
import uk.ac.cardiff.model.wsmodel.StatisticParameters.StatisticType;
import uk.ac.cardiff.raptorweb.engine.ChartProcessor;
import uk.ac.cardiff.raptorweb.engine.RaptorWebEngine;
import uk.ac.cardiff.raptorweb.model.ChartOptions;
import uk.ac.cardiff.raptorweb.model.GraphModel;
import uk.ac.cardiff.raptorweb.model.MUAEntry;
import uk.ac.cardiff.raptorweb.model.RaptorGraphModel;
import uk.ac.cardiff.raptorweb.model.ReportModel;
import uk.ac.cardiff.raptorweb.model.StatisticalUnitInformationView;
import uk.ac.cardiff.raptorweb.model.WebSession;
import uk.ac.cardiff.raptorweb.service.GraphService;

/**
 * @author philsmart
 *
 */
public class GraphServiceImpl implements GraphService{

        /** Class logger */
	private final Logger log = LoggerFactory.getLogger(GraphServiceImpl.class);

	/* RaptorWeb's main engine class for dealing with all service requests */
	private RaptorWebEngine webEngine;

	/* The chart processor which converts Aggregator Charts into RaptorWeb charts for outputting */
	private ChartProcessor chartProcessor;

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
	
	public void removePostProcessorFromSelectedStatistic(WebSession websession){
	    websession.getGraphmodel().getSelectedStatisticalUnit().getStatisticalUnitInformation().getPostprocessors().remove(
	            websession.getGraphmodel().getSelectedPostProcessor());
	}
	
	public void addPostProcessorToSelectedStatistic(WebSession websession){
	    ProcessorInformation information = new ProcessorInformation();
	    information.setBeanName("Please Change");	   
	    if (websession.getGraphmodel().getSuggestionValues().getPossiblePostProcessorValuesList()!=null &&
	            websession.getGraphmodel().getSuggestionValues().getPossiblePostProcessorValuesList().size()>0){
	        information.setBeanName(websession.getGraphmodel().getSuggestionValues().getPossiblePostProcessorValuesList().get(0));
	    }
	    websession.getGraphmodel().getSelectedStatisticalUnit().getStatisticalUnitInformation().getPostprocessors().add(information);
	}

	public void populateStatisticalUnits(WebSession websession){
	    ArrayList<StatisticalUnitInformationView> statisticalUnitsForView = new ArrayList();
	    List<StatisticalUnitInformation> units = getStatisticalUnits();
	    for (StatisticalUnitInformation unit : units){
        	    StatisticalUnitInformationView unitForView = new StatisticalUnitInformationView();
        	    unitForView.setSelected(false);
        	    unitForView.setStatisticalUnitInformation(unit);
        	    statisticalUnitsForView.add(unitForView);
	    }
	    websession.getGraphmodel().setStatisticalUnitsForView(statisticalUnitsForView);

	}

	public void populateSuggestionValues(WebSession websession){
	    websession.getGraphmodel().setSuggestionValues(webEngine.getSuggestionValues());
	}

	/**
	 * Only retrieves USER level units from those retrieved by the MUA. Encapsulates them in a view object
	 */
	public List getStatisticalUnits(){
		List<StatisticalUnitInformation> units = webEngine.getStatisticalUnits();
		List<StatisticalUnitInformation> unitsForUser = new ArrayList<StatisticalUnitInformation>();

		for (StatisticalUnitInformation unit : units){
			if (unit.getStatisticParameters().getType()==StatisticType.USER){
			    unitsForUser.add(unit);
			}
		 }
		return unitsForUser;
	}

	@Override
	public List getChartData(String statisticalUnitName) {
		return null;
	}

	public void generateExcelReport(WebSession websession){
	    if (websession.getGraphmodel().getCurrentTableGraph()!=null)
		webEngine.generateReport(websession,"excel");
	}

	public void generateCSVReport(WebSession websession){
	    if (websession.getGraphmodel().getCurrentTableGraph()!=null)
		webEngine.generateReport(websession,"csv");
	}

	public void generatePDFReport(WebSession websession){
	    if (websession.getGraphmodel().getCurrentTableGraph()!=null)
		webEngine.generateReport(websession,"pdf");
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
		log.info("Graph Service Invoking "+model.getSelectedStatisticalUnit().getStatisticalUnitInformation().getStatisticParameters().getUnitName());
		AggregatorGraphModel gmodel = webEngine.invokeStatisticalUnit(model.getSelectedStatisticalUnit().getStatisticalUnitInformation());
		model.setRawGraphModel(gmodel);
		if (gmodel!=null){
		    model.setCurrentTableGraph(chartProcessor.constructRaptorTableChartModel(gmodel));
		    model.setCurrentJFreeGraph(chartProcessor.constructJFreeGraph(gmodel,websession.getGraphmodel().getChartOptions()));
		    model.setProcessingResult("Done");

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

	public void setChartProcessor(ChartProcessor chartProcessor) {
	    this.chartProcessor = chartProcessor;
	}

	public ChartProcessor getChartProcessor() {
	    return chartProcessor;
	}

	/* (non-Javadoc)
	 * @see uk.ac.cardiff.raptorweb.service.GraphService#removeSeriesFromSelectedStatistic(uk.ac.cardiff.raptorweb.model.WebSession)
	 */
	@Override
	public void removeSeriesFromSelectedStatistic(WebSession websession) {
	    websession.getGraphmodel().getSelectedStatisticalUnit().removeSeries(websession.getGraphmodel().getSelectedSeries());

	}

	/* (non-Javadoc)
	 * @see uk.ac.cardiff.raptorweb.service.GraphService#addSeriesToSelectedStatistic(uk.ac.cardiff.raptorweb.model.WebSession)
	 */
	@Override
	public void addSeriesToSelectedStatistic(WebSession websession) {
	    websession.getGraphmodel().getSelectedStatisticalUnit().addSeries();

	}

	@Override
	public void addFilterToSelectedSeries(WebSession websession) {
	    websession.getGraphmodel().getSelectedStatisticalUnit().addFilterToSeries(websession.getGraphmodel().getSelectedSeries());

	}

	@Override
	public void removeSelectedFilterFromSelectedStatistic(WebSession websession) {
	    websession.getGraphmodel().getSelectedStatisticalUnit().removeFilterFromSeries(websession.getGraphmodel().getSelectedSeries());

	}

	@Override
	public void rerenderGraph(WebSession websession) {
	    log.debug("Rerendering graph for display");
	    GraphModel model = websession.getGraphmodel();
	    model.setCurrentJFreeGraph(chartProcessor.constructJFreeGraph(model.getRawGraphModel(), model.getChartOptions()));

	}


}
