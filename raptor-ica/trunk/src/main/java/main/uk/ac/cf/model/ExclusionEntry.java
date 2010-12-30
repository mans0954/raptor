/**
 *
 */
package main.uk.ac.cf.model;

/**
 * @author philsmart
 *
 */
public abstract class ExclusionEntry implements EntryFilter{

    private String fieldName;
    private String match;

    public void setFieldName(String fieldName) {
	this.fieldName = fieldName;
    }
    public String getFieldName() {
	return fieldName;
    }
    public void setMatch(String matchString) {
	this.match = matchString;
    }
    public String getMatch() {
	return match;
    }

}
