/**
 *
 */
package main.uk.ac.cf.dao.external.format;

/**
 * @author philsmart
 *
 */
public class Header {

	public enum Type {STRING, DATE, INTEGER}

	private String fieldName;
	private int fieldNo;
	private Type fieldType;
	//only needed if the object type is a date
	private String dateTimeFormat;

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


}
