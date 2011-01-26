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
package uk.ac.cardiff.raptormua.model;

import uk.ac.cardiff.model.Graph.AggregatorGraphModel;

public class Report {
	
	private String reportName;
	private AggregatorGraphModel graphModel;
	
	
	public void setReportName(String reportName) {
		this.reportName = reportName;
	}
	public String getReportName() {
		return reportName;
	}
	public void setGraphModel(AggregatorGraphModel graphModel) {
		this.graphModel = graphModel;
	}
	public AggregatorGraphModel getGraphModel() {
		return graphModel;
	}
	

}
