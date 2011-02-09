package uk.ac.cardiff.raptorweb.model;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.event.ValueChangeEvent;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import uk.ac.cardiff.model.wsmodel.StatisticalUnitInformation;



public class GraphModel implements Serializable{
	static Logger log = LoggerFactory.getLogger(GraphModel.class);

	//private String selectedStatisticalUnit;
	private RaptorGraphModel currentGraph;
	private ChartOptions chartOptions;
	private RaptorTableChartModel currentTableGraph;
	private StatisticalUnitInformation selectedStatisticalUnit;
	private String processingResult;
	private boolean showControlPanel;
	private RaptorJFreeChartModel currentJFreeGraph;


	/**
	 * Set some sensible defaults for this graphs models chart options
	 */
	public GraphModel(){
	    chartOptions = new ChartOptions();
	    chartOptions.setPerspective("false");
	    chartOptions.setGraphType("horizontalBar");
	    chartOptions.setxMajorGridCount(-1);
	    chartOptions.setyMajorGridCount(-1);
	    chartOptions.setChartHeight(ChartOptions.ChartHeight.MEDIUM);
	    showControlPanel=true;

	    currentTableGraph = new RaptorTableChartModel();
	}

	/**
	 * @param selectedStatisticalUnit the selectedStatisticalUnit to set
	 */
	public void setSelectedStatisticalUnit(StatisticalUnitInformation selectedStatisticalUnit) {
		this.selectedStatisticalUnit = selectedStatisticalUnit;
	}



	/**
	 * @return the selectedStatisticalUnit
	 */
	public StatisticalUnitInformation getSelectedStatisticalUnit() {
		return selectedStatisticalUnit;
	}

	/**
	 * @param invokeStatisticalUnit
	 */
	public void setCurrentGraph(RaptorGraphModel graph) {
		this.currentGraph = graph;

	}

	/**
	 *
	 * @return the <code>RaptorGraphMode</code> associated with this graph view
	 */
	public RaptorGraphModel getCurrentGraph() {
		return currentGraph;
	}

	public void setCurrentTableGraph(RaptorTableChartModel currentTableGraph) {
		this.currentTableGraph = currentTableGraph;
	}

	public RaptorTableChartModel getCurrentTableGraph() {
		return currentTableGraph;
	}



	public void setChartOptions(ChartOptions chartOptions) {
	    this.chartOptions = chartOptions;
	}



	public ChartOptions getChartOptions() {
	    return chartOptions;
	}

	public void setProcessingResult(String processingResult) {
	    this.processingResult = processingResult;
	}

	public String getProcessingResult() {
	    return processingResult;
	}

	public void setShowControlPanel(boolean showControlPanel) {
	    this.showControlPanel = showControlPanel;
	}

	public boolean isShowControlPanel() {
	    return showControlPanel;
	}

	public void toggleShowControlPanel(){
	    showControlPanel = !showControlPanel;
	}

	public void setCurrentJFreeGraph(RaptorJFreeChartModel currentJFreeGraph) {
	    this.currentJFreeGraph = currentJFreeGraph;
	}

	public RaptorJFreeChartModel getCurrentJFreeGraph() {
	    return currentJFreeGraph;
	}







}
