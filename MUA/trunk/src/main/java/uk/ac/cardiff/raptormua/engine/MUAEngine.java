/**
 *
 */
package uk.ac.cardiff.raptormua.engine;

import org.apache.log4j.Logger;

import uk.ac.cardiff.raptormua.engine.statistics.StatisticsHandler;
import uk.ac.cardiff.raptormua.model.EntryHandler;
import uk.ac.cardiff.raptormua.model.UAEntry;

/**
 * @author philsmart
 *
 */
public class MUAEngine {
	static Logger log = Logger.getLogger(MUAEngine.class);

	private UARegistry uaRegistry;
	private EntryHandler entryHandler;
	private StatisticsHandler statisticsHandler;

	/**
	 *
	 */
	public void poll() {
		log.info("MultiUnit Aggregator Polling Unit Aggregators");
		for (UAEntry entry : uaRegistry.getUAEntries()){
			entryHandler.addEntries(entry.getAllAuthentications());
		}

	}

	public void setUaRegistry(UARegistry uaRegistry) {
		this.uaRegistry = uaRegistry;
	}

	public UARegistry getUaRegistry() {
		return uaRegistry;
	}

	public void setStatisticsHandler(StatisticsHandler statisticsHandler) {
		this.statisticsHandler = statisticsHandler;
	}

	public StatisticsHandler getStatisticsHandler() {
		return statisticsHandler;
	}

	/**
	 * @param statisticName
	 */
	public void performStatistic(String statisticName) {
		statisticsHandler.peformStatistic(statisticName);

	}

}
