/**
 *
 */
package main.uk.ac.cf.model;

/**
 * @author philsmart
 *
 */
public class RegexInclusionEntry extends InclusionEntry{

    private String regexMatch;

    /* (non-Javadoc)
     * @see main.uk.ac.cf.model.EntryFilter#filter(java.lang.String)
     */
    @Override
    public boolean filter(String value) {
	return true;
    }

    public void setRegexMatch(String regexMatch) {
	this.regexMatch = regexMatch;
    }

    public String getRegexMatch() {
	return regexMatch;
    }



}
