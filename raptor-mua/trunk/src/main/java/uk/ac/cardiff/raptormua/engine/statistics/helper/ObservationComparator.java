/**
 *
 */
package uk.ac.cardiff.raptormua.engine.statistics.helper;

import java.util.Comparator;

import uk.ac.cardiff.raptormua.engine.statistics.records.Observation;

/**
 * @author philsmart
 *
 */
public class ObservationComparator implements Comparator<Observation>{

    private boolean asc;

    public ObservationComparator(boolean asc){
	this.asc = asc;
    }

    /* (non-Javadoc)
     * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
     */
    @Override
    public int compare(Observation arg0, Observation arg1) {
	if (asc)
	    return (int)(arg0.getValue() - arg1.getValue()); //if =0 same, < 0 smaller, >0 bigger
	else
	    return (int)(arg1.getValue() - arg0.getValue());
    }

}
