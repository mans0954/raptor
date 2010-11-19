/**
 * Copyright (C) 2010 Cardiff University, Wales <smartp@cf.ac.uk>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
/**
 *
 */
package uk.ac.cardiff.raptormua.engine;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import uk.ac.cardiff.model.Graph.AggregatorGraphModel;
import uk.ac.cardiff.model.wsmodel.Capabilities;
import uk.ac.cardiff.model.wsmodel.StatisticalUnitInformation;
import uk.ac.cardiff.raptormua.engine.statistics.Statistic;
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
		    	Set entries = entry.getAllAuthentications();
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
	public AggregatorGraphModel performStatistic(String statisticName) {
		/* set the current set of entries held by the MUA for processing*/
		statisticsHandler.setEntries(entryHandler.getEntries());
		return statisticsHandler.peformStatistic(statisticName);

	}

	/**
	 * @return
	 */
	public Capabilities getCapabilities() {
		//* retrieve all registered stats from the stats handler*/
		List<Statistic> su = statisticsHandler.getStatisticalUnits();
		/* retrieve information about attached UA */
		List<UAEntry> uaentries = uaRegistry.getUAEntries();

		Capabilities capabilities = new Capabilities();

		ArrayList<String> ua = new ArrayList();
		for (UAEntry entry : uaentries)ua.add(entry.getServiceEndpoint());
		capabilities.setAttached(ua);

		ArrayList<StatisticalUnitInformation> stats = new ArrayList();
		for (Statistic entry : su){
			StatisticalUnitInformation information = new StatisticalUnitInformation();
			information.setField(entry.getField());
			information.setMethodName(entry.getMethodName());
			information.setMethodParams(entry.getMethodParams());
			information.setStatisticalUnitName(entry.getUnitName());

			String format = "dd/MM/yyyy HH:mm:ss";
			information.setDateFormat(format);
			DateTimeFormatter dtf = DateTimeFormat.forPattern(format);
			if (entry.getStartTimeAsDate()!=null)
				information.setStartTime(entry.getStartTimeAsDate().toString(dtf));
			else
				information.setStartTime("First Entry");
			if (entry.getEndTimeAsDate()!=null)
				information.setEndTime(entry.getEndTimeAsDate().toString(dtf));
			else
				information.setEndTime("Last Entry");
			stats.add(information);
		}
		capabilities.setStatisticalServices(stats);




		return capabilities;
	}

}
