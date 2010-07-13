package uk.ac.cardiff.model.Graph;

import java.util.List;


/* 
 * based on the Apache Trinidad ChartModel, for compatibility. However, does not extend the Apache Trinidad Model
 * so as to remain view agnostic.
 */

public class AggregatorGraphModel {
	
	private List<String> groupLabels;
	private List<String> seriesLabels;
	
	
	public List<String> getGroupLabels() {		
		return groupLabels;
	}


	public List<String> getSeriesLabels() {
		
		return seriesLabels;
	}


	public List<List<Double>> getYValues() {
		List<List<Double>> chartValues = new ArrayList<List<Double>>();
		// Fill the groups
		for (int i = 0; i < getGroupLabels().size(); i++) {
			List<Double> numbers = new ArrayList<Double>();

			// fill the series per group
			for (int j = 0; j < getSeriesLabels().size(); j++) {
				numbers.add(100* Math.random());
			}
			chartValues.add(numbers);
		}
		return chartValues;

	}

	
}
