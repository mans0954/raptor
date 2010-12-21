/**
 *
 */
package main.uk.ac.cf.model;

/**
 * @author philsmart
 *
 */
public class ExclusionList {

    private ExclusionEntry[] exclusionEntries;

    public void setExclusionEntries(ExclusionEntry[] exclusionEntries) {
	this.exclusionEntries = exclusionEntries;
    }

    public ExclusionEntry[] getExclusionEntries() {
	return exclusionEntries;
    }

}
