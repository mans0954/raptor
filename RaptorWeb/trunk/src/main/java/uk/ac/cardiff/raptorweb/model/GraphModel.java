package uk.ac.cardiff.raptorweb.model;

import java.io.Serializable;

public class GraphModel implements Serializable{
	
	private String selectedStatisticalUnit;

	/**
	 * @param selectedStatisticalUnit the selectedStatisticalUnit to set
	 */
	public void setSelectedStatisticalUnit(String selectedStatisticalUnit) {
		this.selectedStatisticalUnit = selectedStatisticalUnit;
	}

	/**
	 * @return the selectedStatisticalUnit
	 */
	public String getSelectedStatisticalUnit() {
		return selectedStatisticalUnit;
	}

}
