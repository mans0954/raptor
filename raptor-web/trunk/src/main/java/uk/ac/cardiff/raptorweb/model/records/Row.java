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

	private String group;
	private T value;

	public void setValue(T value) {
		this.value = value;
	}
	public T getValue() {
		return value;
	}
	public void setGroup(String group) {
	    this.group = group;
	}
	public String getGroup() {
	    return group;
	}


}
