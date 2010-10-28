/**
 *
 */
package uk.ac.cardiff.raptormua.engine.statistics;

import java.util.List;

import org.apache.log4j.Logger;

import uk.ac.cardiff.model.Entry;

/**
 * @author philsmart
 *
 */
public class SessionLinker implements StatisticsPreProcessing{

	static Logger log = Logger.getLogger(SessionLinker.class);
	/* (non-Javadoc)
	 * @see uk.ac.cardiff.raptormua.engine.statistics.StatisticsPreProcessing#preProcess(java.util.List)
	 */
	@Override
	public List<Entry> preProcess(List<Entry> entries) {
		log.info("Preprocessing with "+this.getClass());

		return entries;
	}



}
