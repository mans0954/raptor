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
package uk.ac.cardiff.raptor.raptorica.dao.external.format;

import java.util.Map;

/**
 * @author philsmart
 *
 */
public class Header {

	public enum Type {STRING, DATE, INTEGER, STRINGLIST, URL}

	private String fieldName;
	private int fieldNo;

	/** Used to combined two fields together */
	private int[] additionalFieldNos;


	private Type fieldType;
	private Map<String,String> regexReplaceAll;
	private String regexRetain;

	//only needed if the object type is a date
	private String dateTimeFormat;
	private String timeZone;

	private String listDelimeter;

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}
	public String getFieldName() {
		return fieldName;
	}
	public void setFieldNo(int fieldNo) {
		this.fieldNo = fieldNo;
	}
	public int getFieldNo() {
		return fieldNo;

	}
	public void setFieldType(String typeS) {
		this.fieldType = Type.valueOf(typeS);
	}

	/** returns a string, as Type is null during init from Spring
	 * which causes an error, so the below accessor should be used*/
	public String getFieldType() {
		return fieldType.toString();
	}

	/** USE this method to return the type*/
	public Type getType(){
		return fieldType;
	}

	public void setDateTimeFormat(String dateTimeFormat) {
		this.dateTimeFormat = dateTimeFormat;
	}
	public String getDateTimeFormat() {
		return dateTimeFormat;
	}
	/**
	 * @param listDelimeter the listDelimeter to set
	 */
	public void setListDelimeter(String listDelimeter) {
		this.listDelimeter = listDelimeter;
	}
	/**
	 * @return the listDelimeter
	 */
	public String getListDelimeter() {
		return listDelimeter;
	}
	public void setTimeZone(String timeZone) {
	    this.timeZone = timeZone;
	}

	/**
	 * Gets the timezone, if none specified, UTC is the default
	 * @return
	 */
	public String getTimeZone() {
	    if (timeZone==null)return "UTC";
	    return timeZone;
	}
	/**
	 * @param regexReplaceAll the regexReplaceAll to set
	 */
	public void setRegexReplaceAll(Map<String,String> regexReplaceAll) {
	    this.regexReplaceAll = regexReplaceAll;
	}
	/**
	 * @return the regexReplaceAll
	 */
	public Map<String,String> getRegexReplaceAll() {
	    return regexReplaceAll;
	}
	/**
	 * @param regexRetain the regexRetain to set
	 */
	public void setRegexRetain(String regexRetain) {
	    this.regexRetain = regexRetain;
	}
	/**
	 * @return the regexRetain
	 */
	public String getRegexRetain() {
	    return regexRetain;
	}
	/**
	 * @param additionalFieldNos the additionalFieldNos to set
	 */
	public void setAdditionalFieldNos(int[] additionalFieldNos) {
	    this.additionalFieldNos = additionalFieldNos;
	}
	/**
	 * @return the additionalFieldNos
	 */
	public int[] getAdditionalFieldNos() {
	    return additionalFieldNos;
	}





}
