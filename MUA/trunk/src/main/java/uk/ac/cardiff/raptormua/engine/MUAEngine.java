/**
 *
 */
package uk.ac.cardiff.raptormua.engine;

import java.util.List;

import org.apache.log4j.Logger;


import uk.ac.cardiff.raptormua.engine.statistics.StatisticsHandler;
import uk.ac.cardiff.raptormua.model.EntryHandler;
import uk.ac.cardiff.raptormua.model.MemoryEntryHandler;
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
	
	public MUAEngine (){
		log.info("Setup Multi-Unit Aggregator Engine...");
		entryHandler = new MemoryEntryHandler();
		log.info("Mulit-Unit Aggregator Engine is running...");
	}

	/**
	 *
	 */
	public void poll() {
		log.info("MultiUnit Aggregator Polling Unit Aggregators");
		for (UAEntry entry : uaRegistry.getUAEntries()){
			List entries = entry.getAllAuthentications();
			//log.debug("Setting: "+entries.size());
			entryHandler.addEntries(entries);
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
		/* set the current set of entries held by the MUA for processing*/	
		statisticsHandler.setEntries(entryHandler.getEntries());
		statisticsHandler.peformStatistic(statisticName);

	}

}
