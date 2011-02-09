/**
 *
 */
package uk.ac.cardiff.raptorweb.model;

import java.io.Serializable;
import java.util.List;

/**
 * @author philsmart
 *
 */
public class RaptorGraphModel implements Serializable{

	private List<String> groupLabels;
	private List<String> seriesLabels;
	private List<List<Double>> chartValues;



	public List<String> getGroupLabels() {
		// TODO Auto-generated method stub
		return groupLabels;
	}

	public List<String> getSeriesLabels() {
		// TODO Auto-generated method stub
		return seriesLabels;
	}

	public List<List<Double>> getYValues() {
		// TODO Auto-generated method stub
		return chartValues;
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



}
