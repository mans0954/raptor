/**
 *
 */
package uk.ac.cardiff.raptormua.engine.statistics;

import java.util.List;

import org.apache.log4j.Logger;

import uk.ac.cardiff.model.AuthenticationEntry;
import uk.ac.cardiff.raptormua.exceptions.StatisticalUnitException;
import uk.ac.cardiff.raptormua.service.impl.MUAProcessImpl;

/**
 * @author philsmart
 *
 */
public class AuthenticationStatistic extends Statistic{
	static Logger log = Logger.getLogger(AuthenticationStatistic.class);



	public void countEntryPerInterval(String timeInterval) throws StatisticalUnitException{
		log.debug("Performing countEntryPerInterval Statistical Operation");
		int timeIntervalInt = Integer.parseInt(timeInterval);
		log.debug("Params for method:  "+this.getField()+", "+this.getMethodName()+", "+this.getUnitName());
		if (this.getAuthEntries()!=null)log.debug("Number of entries: "+this.getAuthEntries().size());
	}







}
