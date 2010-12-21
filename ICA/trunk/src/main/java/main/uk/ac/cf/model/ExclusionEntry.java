/**
 *
 */
package main.uk.ac.cf.model;

/**
 * @author philsmart
 *
 */
public class ExclusionEntry {

    private String fieldName;
    private String matchString;

    public void setFieldName(String fieldName) {
	this.fieldName = fieldName;
    }
    public String getFieldName() {
	return fieldName;
    }
    public void setMatchString(String matchString) {
	this.matchString = matchString;
    }
    public String getMatchString() {
	return matchString;
    }

}
