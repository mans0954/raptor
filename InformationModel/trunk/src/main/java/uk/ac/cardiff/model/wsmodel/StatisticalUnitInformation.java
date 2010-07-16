/**
 *
 */
package uk.ac.cardiff.model.wsmodel;

import java.io.Serializable;
import java.util.List;

/**
 * @author philsmart
 *
 */
public class StatisticalUnitInformation implements Serializable{

	private String statisticalUnitName;
	private String methodName;
	private List<String> methodParams;
	private String field;


	public void setStatisticalUnitName(String statisticalUnitName) {
		this.statisticalUnitName = statisticalUnitName;
	}
	public String getStatisticalUnitName() {
		return statisticalUnitName;
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
	public void setField(String field) {
		this.field = field;
	}
	public String getField() {
		return field;
	}

}
