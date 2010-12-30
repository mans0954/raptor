/**
 *
 */
package main.uk.ac.cf.model;

/**
 * @author philsmart
 *
 */
public abstract class InclusionEntry implements EntryFilter{

    private String fieldName;
    private String match;

    public void setFieldName(String fieldName) {
	this.fieldName = fieldName;
    }
    public String getFieldName() {
	return fieldName;
    }
    public void setMatch(String match) {
	this.match = match;
    }
    public String getMatch() {
	return match;
    }

}
