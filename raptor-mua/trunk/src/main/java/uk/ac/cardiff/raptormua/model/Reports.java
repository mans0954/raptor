package uk.ac.cardiff.raptormua.model;

import java.util.List;

/**
 * Class to store (which are also persisted) saved reports for an individual
 * <code>RaptorUser</code>
 * 
 * @author philsmart
 *
 */
public class Reports {
	
	private List<Report> savedReports;

	public void setSavedReports(List<Report> savedReports) {
		this.savedReports = savedReports;
	}

	public List<Report> getSavedReports() {
		return savedReports;
	}

}
