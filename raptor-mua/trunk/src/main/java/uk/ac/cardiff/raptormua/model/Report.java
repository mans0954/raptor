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
