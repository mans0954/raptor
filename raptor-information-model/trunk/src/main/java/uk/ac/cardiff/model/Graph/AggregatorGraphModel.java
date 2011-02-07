package uk.ac.cardiff.model.Graph;

import java.util.ArrayList;
import java.util.List;

import uk.ac.cardiff.model.Presentation;


/*
 * based on the Apache Trinidad ChartModel, for compatibility. However, does not extend the Apache Trinidad Model
 * so as to remain view agnostic.
 */

public class AggregatorGraphModel {

	private List<String> groupLabels;
	private List<String> seriesLabels;
	private List<List<Double>> chartValues;

	private Presentation presentation;

	public AggregatorGraphModel(){
	    seriesLabels= new ArrayList<String>();
	}


	public List<String> getGroupLabels() {
		  return groupLabels;

	}

	public void addGroupLabel(String label){
		if (getGroupLabels()==null)setGroupLabels(new ArrayList<String>());
		getGroupLabels().add(label);
	}

	public void addSeriesLabel(String label){
		if (getSeriesLabels()==null)setSeriesLabels(new ArrayList<String>());
		getSeriesLabels().add(label);
	}


	public List<String> getSeriesLabels() {
	    return seriesLabels;

	}

	public void addGroupValue(List<Double> values){
		if (chartValues==null) setChartValues(new ArrayList<List<Double>>());
		chartValues.add(values);
	}


	public List<List<Double>> getYValues() {
		    return chartValues;
	}

	public void setYValues(List<List<Double>> values) {

	}


	public void setGroupLabels(List<String> groupLabels) {
		this.groupLabels = groupLabels;
	}

	public void setSeriesLabels(List<String> seriesLabels) {
		this.seriesLabels = seriesLabels;
	}

	public void setChartValues(List<List<Double>> chartValues) {
		this.chartValues = chartValues;
	}

	public List<List<Double>> getChartValues() {
		return chartValues;
	}

	public void setPresentation(Presentation presentation) {
	    this.presentation = presentation;
	}

	public Presentation getPresentation() {
	    return presentation;
	}


}
