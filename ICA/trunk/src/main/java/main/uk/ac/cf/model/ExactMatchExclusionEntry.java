/**
 *
 */
package main.uk.ac.cf.model;

/**
 * @author philsmart
 *
 */
public class ExactMatchExclusionEntry extends ExclusionEntry{

    /* (non-Javadoc)
     * @see main.uk.ac.cf.model.EntryFilter#filter(java.lang.String)
     */
    @Override
    public boolean filter(String value) {
	return (value.equals(this.getMatchString())) ? true : false;
    }

}
