/**
 *
 */
package uk.ac.cardiff.raptor.raptorica.dao.external.format;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * @author philsmart
 *
 */
public class ContainsLineFilter implements LineFilter{

    /** The class logger */
    private final Logger log = LoggerFactory.getLogger(ContainsLineFilter.class);

    private String includeIfContains;

    /* (non-Javadoc)
     * @see uk.ac.cardiff.raptor.raptorica.dao.external.format.LineFilter#remove()
     */
    @Override
    public boolean parsableLine(String line) {
	if (line.contains(includeIfContains))
	    return true;
	return false;
    }

    /**
     * @param includeIfContains the includeIfContains to set
     */
    public void setIncludeIfContains(String includeIfContains) {
	this.includeIfContains = includeIfContains;
    }

    /**
     * @return the includeIfContains
     */
    public String getIncludeIfContains() {
	return includeIfContains;
    }

}
