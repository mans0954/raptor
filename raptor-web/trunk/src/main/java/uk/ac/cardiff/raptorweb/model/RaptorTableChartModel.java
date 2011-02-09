/**
 *
 */
package uk.ac.cardiff.raptorweb.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author philsmart
 *
 */
public class RaptorTableChartModel implements Serializable{

    /* generated serialID */
    private static final long serialVersionUID = 1765790673328864045L;

    	private List<TableSeries> tableSeries;

	public RaptorTableChartModel(){
	    setTableSeries(new ArrayList());
	}

	/**
	 * @param tseries
	 */
	public void addTableSeries(TableSeries tseries) {
	   getTableSeries().add(tseries);
	}

	public void setTableSeries(List<TableSeries> tableSeries) {
	    this.tableSeries = tableSeries;
	}

	public List<TableSeries> getTableSeries() {
	    return tableSeries;
	}






}
