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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import uk.ac.cardiff.raptorweb.model.records.Row;

/**
 * @author philsmart
 *
 */
public class RaptorTableChartModel implements Serializable {
    static Logger log = LoggerFactory.getLogger(RaptorTableChartModel.class);

    /* generated serialID */
    private static final long serialVersionUID = 1765790673328864045L;

    private List<TableSeries> tableSeries;

    private List<ManyRow> rowsForView;

    private List<String> series;
    private List<Object[]> rowList;

    public RaptorTableChartModel() {
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

    /**
     * This could be cached, as opposed to created on the fly e.g. created after the construction of he tableSeries
     *
     * @return
     */
    public void constructTableForView() {
	if (rowsForView == null)
	    rowsForView = new ArrayList<ManyRow>();
	if (series == null)
	    series = new ArrayList<String>();


	series.add("Group");
	for (TableSeries tseries : tableSeries) {
	    series.add(tseries.getSeriesLabel());

	}


	if (tableSeries.size() > 0) {
	    for (Row row : tableSeries.get(0).getRows()) {
		ManyRow manyRow = new ManyRow();
		manyRow.setGroupLabel(row.getGroup());
		getRowsForView().add(manyRow);

	    }

	    for (TableSeries series : tableSeries) {
		for (Row row : series.getRows()) {
		    for (ManyRow mrow : getRowsForView()) {
			if (mrow.getGroupLabel().equals(row.getGroup())) {
			    mrow.addValue(row.getValue());
			}
		    }
		}
	    }
	    setRowList(new ArrayList<Object[]>());

	    for (ManyRow mrow : rowsForView) {
		Object[] completeRow = new Object[series.size()];
		for (int i = 0; i < series.size(); i++) {
		    if (i==0){
			completeRow[0] = mrow.getGroupLabel();
		    }
		    else if (mrow.getValues().size() >= i) {
			completeRow[i] = mrow.getValues().get(i-1);
		    } else {
			completeRow[i] = "";
		    }
		}
		rowList.add(completeRow);
	    }
	}

    }

    /**
     * @param currentTableGraph
     * @return
     */
    private int maxNoRows() {
	int maxRows = 0;
	for (TableSeries tseries : tableSeries) {
	    if (tseries.getRows().size() > maxRows)
		maxRows = tseries.getRows().size();
	}
	return maxRows;
    }

    public void setRowsForView(List<ManyRow> rowsForView) {
	this.rowsForView = rowsForView;
    }

    public List<ManyRow> getRowsForView() {
	return rowsForView;
    }

    public void setSeries(List<String> series) {
	this.series = series;
    }

    public List<String> getSeries() {
	return series;
    }

    public void setRowList(List<Object[]> rowList) {
	this.rowList = rowList;
    }

    public List<Object[]> getRowList() {
	return rowList;
    }

}
