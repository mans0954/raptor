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
