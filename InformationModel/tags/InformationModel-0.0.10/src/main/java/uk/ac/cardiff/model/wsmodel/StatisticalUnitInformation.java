/**
 *
 */
package uk.ac.cardiff.model.wsmodel;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

/**
 * This class only represents information, the dattypes are strings, and the strings maybe formatted
 * As a result, this class is only for human consumption (or presentation to the user)
 * @author philsmart
 *
 */
public class StatisticalUnitInformation implements Serializable{

	private String statisticalUnitName;
	private String methodName;
	private List<String> methodParams;
	private String field;
	private String startTime;
	private String endTime;
	private String dateFormat;


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
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public String getStartTime() {
		return startTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	public String getEndTime() {
		return endTime;
	}

//	public Date getStartTimeAsDate(){
//		DateTimeFormatter dtf = DateTimeFormat.forPattern(getDateFormat());
//		DateTime startTimeAsDate = dtf.parseDateTime(startTime);
//		return new Date(startTimeAsDate.getMillis());
//	}
//
//	public Date getEndTimeAsDate(){
//		DateTimeFormatter dtf = DateTimeFormat.forPattern(getDateFormat());
//		DateTime endTimeAsDate = dtf.parseDateTime(endTime);
//		return new Date(endTimeAsDate.getMillis());
//	}



	public void setDateFormat(String dateFormat) {
		this.dateFormat = dateFormat;
	}
	public String getDateFormat() {
		return dateFormat;
	}

}
