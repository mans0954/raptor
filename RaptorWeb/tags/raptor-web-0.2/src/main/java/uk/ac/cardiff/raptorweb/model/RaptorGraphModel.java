/**
 *
 */
package uk.ac.cardiff.raptorweb.model;

import java.io.Serializable;
import java.util.List;

import org.apache.myfaces.trinidad.model.ChartModel;

/**
 * @author philsmart
 *
 */
public class RaptorGraphModel extends ChartModel implements Serializable{

	private List<String> groupLabels;
	private List<String> seriesLabels;
	private List<List<Double>> chartValues;


	/* (non-Javadoc)
	 * @see org.apache.myfaces.trinidad.model.ChartModel#getGroupLabels()
	 */
	@Override
	public List<String> getGroupLabels() {
		// TODO Auto-generated method stub
		return groupLabels;
	}

	/* (non-Javadoc)
	 * @see org.apache.myfaces.trinidad.model.ChartModel#getSeriesLabels()
	 */
	@Override
	public List<String> getSeriesLabels() {
		// TODO Auto-generated method stub
		return seriesLabels;
	}

	/* (non-Javadoc)
	 * @see org.apache.myfaces.trinidad.model.ChartModel#getYValues()
	 */
	@Override
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
