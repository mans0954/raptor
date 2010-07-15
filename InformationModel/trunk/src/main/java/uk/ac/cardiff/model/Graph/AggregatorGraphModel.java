package uk.ac.cardiff.model.Graph;

import java.util.ArrayList;
import java.util.List;


/*
 * based on the Apache Trinidad ChartModel, for compatibility. However, does not extend the Apache Trinidad Model
 * so as to remain view agnostic.
 */

public class AggregatorGraphModel {

	private List<String> groupLabels;
	private List<String> seriesLabels;
	private List<List<Double>> chartValues;


	public List<String> getGroupLabels() {
		return groupLabels;
	}

	public void addGroupLabel(String label){
		if (groupLabels==null)groupLabels = new ArrayList<String>();
		groupLabels.add(label);
	}

	public void addSeriesLabel(String label){
		if (seriesLabels==null)seriesLabels = new ArrayList<String>();
		seriesLabels.add(label);
	}


	public List<String> getSeriesLabels() {
		return seriesLabels;
	}


	public List<List<Double>> getYValues() {
		return chartValues;
		// Fill the groups

	}


}
