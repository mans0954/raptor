package uk.ac.cardiff.raptorweb.model;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import uk.ac.cardiff.model.report.AggregatorGraphModel;
import uk.ac.cardiff.model.report.Series;
import uk.ac.cardiff.model.wsmodel.StatisticParameters;
import uk.ac.cardiff.model.wsmodel.StatisticParameters.EventType;
import uk.ac.cardiff.model.wsmodel.StatisticalUnitInformation;
import uk.ac.cardiff.model.wsmodel.SuggestionValues;
import uk.ac.cardiff.raptorweb.model.ChartOptions.ChartType;
import uk.ac.cardiff.raptorweb.model.ChartOptions.GraphPresentation;



public class GraphModel implements Serializable{

    	/** Generated SerialVersionUID */
    	private static final long serialVersionUID = -2803349385469406219L;

	/** The raw graph model, for later reconstruction*/
	private AggregatorGraphModel rawGraphModel;

	private RaptorGraphModel currentGraph;
	private ChartOptions chartOptions;
	private RaptorTableChartModel currentTableGraph;
	private StatisticalUnitInformationView selectedStatisticalUnit;
	private String processingResult;
	private boolean showControlPanel;
	private RaptorJFreeChartModel currentJFreeGraph;
	private String controlPanelSize;
	private List<StatisticalUnitInformationView> statisticalUnitsForView;

	private SuggestionValues suggestionValues;

	/* Selected series modal panel*/
	private Series selectedSeries;



	/**
	 * Set some sensible defaults for this graphs models chart options
	 */
	public GraphModel(){
	    chartOptions = new ChartOptions();
	    chartOptions.setPerspective("false");
	    chartOptions.setGraphType(ChartOptions.ChartType.BAR);
	    chartOptions.setxMajorGridCount(-1);
	    chartOptions.setyMajorGridCount(-1);
	    chartOptions.setOrientation(ChartOptions.OrientationType.VERTICAL);
	    chartOptions.setImageWidth(1480);
	    chartOptions.setImageHeight(1024);
	    chartOptions.setxLabelPosition(ChartOptions.LabelPositionType.UP_90);
	    chartOptions.setGraphPresentation(GraphPresentation.FANCY);
	    chartOptions.setGraphType(ChartType.BAR3D);

	    //create a blank selected statistical unit for display
	    selectedStatisticalUnit = new StatisticalUnitInformationView();

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
	
	/** Find all fields for the currently selected event type 
	 * 
	 * @return
	 */
	public List<String> getPossibleFieldNameValues(){
	    EventType eventType = selectedStatisticalUnit.getStatisticalUnitInformation().getStatisticParameters().getEventType();
	    String[] classFilter = eventType.getClassHierarchy();
	    return suggestionValues.getPossibleFieldNameValuesList(classFilter);	    	    
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

	public void setSelectedSeries(Series selectedSeries) {
	    this.selectedSeries = selectedSeries;
	}

	public Series getSelectedSeries() {
	    return selectedSeries;
	}

	public void setSuggestionValues(SuggestionValues suggestionValues) {
	    this.suggestionValues = suggestionValues;
	}

	public SuggestionValues getSuggestionValues() {
	    return suggestionValues;
	}

	public void setRawGraphModel(AggregatorGraphModel rawGraphModel) {
	    this.rawGraphModel = rawGraphModel;
	}

	public AggregatorGraphModel getRawGraphModel() {
	    return rawGraphModel;
	}








}
