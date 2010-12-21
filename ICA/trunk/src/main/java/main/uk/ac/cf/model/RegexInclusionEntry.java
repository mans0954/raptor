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

    /*
     * Constructs a regex from the <code>match</code> string, and checks if this regex is
     * contained in the input <code>value</code>
     *
     * (non-Javadoc)
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
