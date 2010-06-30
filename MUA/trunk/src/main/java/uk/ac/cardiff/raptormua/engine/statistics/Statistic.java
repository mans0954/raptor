/**
 *
 */
package uk.ac.cardiff.raptormua.engine.statistics;

import java.util.List;

import uk.ac.cardiff.model.AuthenticationEntry;
import uk.ac.cardiff.model.Entry;

/**
 * @author philsmart
 * Holds a statistics unit or one statistics operation on one piece of data
 */
public class Statistic {

	private List<Entry> entries;
	private String field;
	private String unitName;
	private String methodName;
	private List<String> methodParams;

	public void setEntries(List<Entry> authEntries) {
		this.entries = authEntries;
	}

	public List<Entry> getAuthEntries() {
		return entries;
	}

	public void setField(String field) {
		this.field = field;
	}

	public String getField() {
		return field;
	}

	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}

	public String getMethodName() {
		return methodName;
	}

	public void setMethodParams(List<String> methodParams) {
		this.methodParams = methodParams;
	}

	public List<String> getMethodParams() {
		return methodParams;
	}

	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}

	public String getUnitName() {
		return unitName;
	}


}
