/**
 *
 */
package uk.ac.cardiff.raptorweb.model.records;

import java.io.Serializable;

/**
 * @author philsmart
 *
 */
public class Row implements Serializable{

	private String series;
	private String value;

	public void setSeries(String series) {
		this.series = series;
	}
	public String getSeries() {
		return series;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getValue() {
		return value;
	}

}
