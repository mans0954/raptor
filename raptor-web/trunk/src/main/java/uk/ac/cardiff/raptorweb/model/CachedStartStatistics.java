/**
 *
 */
package uk.ac.cardiff.raptorweb.model;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



import uk.ac.cardiff.model.wsmodel.Capabilities;

/**
 * Almost exactly the same class as <code>StartModel</code>, but extended for 
 * semantic operational differences 
 * 
 * @author philsmart
 *
 */
public class CachedStartStatistics{

        /** Class logger */
	private final Logger log = LoggerFactory.getLogger(CachedStartStatistics.class);

	/** The cached statistic */
	private StartStatistics cached;
	
	
	public CachedStartStatistics(){
	    cached = new StartStatistics();
	}

	public void setCached(StartStatistics cached) {
	    this.cached = cached;
	}

	public StartStatistics getCached() {
	    return cached;
	}



}
