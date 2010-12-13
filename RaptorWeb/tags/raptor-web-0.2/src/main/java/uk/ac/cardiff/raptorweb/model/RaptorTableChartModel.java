/**
 *
 */
package uk.ac.cardiff.raptorweb.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import uk.ac.cardiff.raptorweb.model.records.Row;


/**
 * @author philsmart
 *
 */
public class RaptorTableChartModel implements Serializable{

    /* generated serialID */
    private static final long serialVersionUID = 1765790673328864045L;

	private List<Row> rows;

	public RaptorTableChartModel(){
		setRows(new ArrayList());
	}

	public void addRow(Row row){
		getRows().add(row);
	}

	public void setRows(List<Row> rows) {
		this.rows = rows;
	}

	public List<Row> getRows() {
		return rows;
	}

}
