/**
 *
 */
package uk.ac.cardiff.raptorweb.model.records;

import java.io.Serializable;

/**
 * @author philsmart
 *
 */
public class Row<T> implements Serializable{

	private String series;
	private T value;

	public void setSeries(String series) {
		this.series = series;
	}
	public String getSeries() {
		return series;
	}
	public void setValue(T value) {
		this.value = value;
	}
	public T getValue() {
		return value;
	}


}
