
package uk.ac.cardiff.raptormua.engine.runtimestatistics;

import org.joda.time.DateTime;

/**
 * 
 * Interface that a define period should implement in order to be utalised by runtime statistics. Implementations
 * constructed programatically, but could be constructed by a code template engine form xml definition. Implementations
 * must provide this information dynamically for each call of getStart and getEnd, otherwise information (statistics)
 * derived from this period will not update correctly.
 */
public interface StatisticPeriod {

    /**
     * Get the start date and time of this period.
     * 
     * @return the start date and time.
     */
    public DateTime getStart();

    /**
     * Gets the end date and time of this period.
     * 
     * @return the end date and time.
     */
    public DateTime getEnd();

    /**
     * Name identifier for this period.
     * 
     * @return the name identifier for this period.
     */
    public String getPeriodIdentifier();

}
