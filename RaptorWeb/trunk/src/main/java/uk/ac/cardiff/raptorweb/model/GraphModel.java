package uk.ac.cardiff.raptorweb.model;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.event.ValueChangeEvent;

import org.apache.log4j.Logger;

import uk.ac.cardiff.model.wsmodel.StatisticalUnitInformation;



public class GraphModel implements Serializable{
	static Logger log = Logger.getLogger(GraphModel.class);

	//private String selectedStatisticalUnit;
	private RaptorGraphModel currentGraph;
	private String graphType;
	private RaptorTableChartModel currentTableGraph;
	private StatisticalUnitInformation selectedStatisticalUnit;


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

	public RaptorGraphModel getCurrentGraph() {
		return currentGraph;
	}

	public void setGraphType(String type) {
		log.debug("Setting graph type: "+type);
		this.graphType = type;
	}

	public String getGraphType() {
		return graphType;
	}


	public void setCurrentTableGraph(RaptorTableChartModel currentTableGraph) {
		this.currentTableGraph = currentTableGraph;
	}

	public RaptorTableChartModel getCurrentTableGraph() {
		return currentTableGraph;
	}





}
