/**
 *
 */
package uk.ac.cardiff.raptorweb.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import uk.ac.cardiff.raptorweb.engine.ChartProcessor;
import uk.ac.cardiff.raptorweb.model.records.Row;

/**
 * @author philsmart
 *
 */
public class TableSeries implements Serializable{

    static Logger log = LoggerFactory.getLogger(TableSeries.class);

    private List<Row> rows;
    private String seriesLabel;

    public TableSeries(){
	rows = new ArrayList<Row>();
    }

    public void setRows(List<Row> rows) {
	this.rows = rows;
    }

    public List<Row> getRows() {
	return rows;
    }

    public void addRow(Row row) {
	getRows().add(row);
    }

    public void setSeriesLabel(String seriesLabel) {
	this.seriesLabel = seriesLabel;
    }

    public String getSeriesLabel() {
	return seriesLabel;
    }

}
