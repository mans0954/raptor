/**
 *
 */
package main.uk.ac.cf.dao.external.format;

/**
 * @author philsmart
 *
 */
public class Header {

	private String fieldName;
	private int fieldNo;
	private Object type;

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
	public void setType(Object type) {
		this.type = type;
	}
	public Object getType() {
		return type;
	}

}
