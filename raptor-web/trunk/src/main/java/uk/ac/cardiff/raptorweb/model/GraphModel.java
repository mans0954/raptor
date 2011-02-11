package uk.ac.cardiff.raptorweb.model;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import uk.ac.cardiff.model.StatisticParameters;
import uk.ac.cardiff.model.wsmodel.StatisticalUnitInformation;



public class GraphModel implements Serializable{
	static Logger log = LoggerFactory.getLogger(GraphModel.class);

	//private String selectedStatisticalUnit;
	private RaptorGraphModel currentGraph;
	private ChartOptions chartOptions;
	private RaptorTableChartModel currentTableGraph;
	private StatisticalUnitInformationView selectedStatisticalUnit;
	private String processingResult;
	private boolean showControlPanel;
	private RaptorJFreeChartModel currentJFreeGraph;
	private String controlPanelSize;
	private List<StatisticalUnitInformationView> statisticalUnitsForView;



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
	    controlPanelSize="0%";
	    currentTableGraph = new RaptorTableChartModel();

	    //create a blank selected statistical unit for display
	    selectedStatisticalUnit = new StatisticalUnitInformationView();
	    StatisticalUnitInformation tmp = new StatisticalUnitInformation();
	    tmp.setStatisticParameters(new StatisticParameters());
	    selectedStatisticalUnit.setStatisticalIUnitInformation(tmp);

	}

	/**
	 * Set the old statistical unit to false, update it with the newly selected statisticalunit
	 * and set it to selected.
	 *
	 * @param selectedStatisticalUnit the selectedStatisticalUnit to set
	 */
	public void setSelectedStatisticalUnit(StatisticalUnitInformationView selectedStatisticalUnit) {
	    	this.selectedStatisticalUnit.setSelected(false);
	    	this.selectedStatisticalUnit = selectedStatisticalUnit;
	    	this.selectedStatisticalUnit.setSelected(true);
	}



	/**
	 * @return the selectedStatisticalUnit
	 */
	public StatisticalUnitInformationView getSelectedStatisticalUnit() {
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
	    controlPanelSize="30%";
	}

	public void setCurrentJFreeGraph(RaptorJFreeChartModel currentJFreeGraph) {
	    this.currentJFreeGraph = currentJFreeGraph;
	}

	public RaptorJFreeChartModel getCurrentJFreeGraph() {
	    return currentJFreeGraph;
	}

	public void setControlPanelSize(String controlPanelSize) {
	    this.controlPanelSize = controlPanelSize;
	}

	public String getControlPanelSize() {
	    return controlPanelSize;
	}

	public void setStatisticalUnitsForView(List<StatisticalUnitInformationView> statisticalUnitsForView) {
	    this.statisticalUnitsForView = statisticalUnitsForView;
	}

	public List<StatisticalUnitInformationView> getStatisticalUnitsForView() {
	    return statisticalUnitsForView;
	}








}
