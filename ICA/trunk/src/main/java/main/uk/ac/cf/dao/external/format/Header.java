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
package main.uk.ac.cf.dao.external.format;

/**
 * @author philsmart
 *
 */
public class Header {

	public enum Type {STRING, DATE, INTEGER, STRINGLIST}

	private String fieldName;
	private int fieldNo;
	private Type fieldType;

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


}
