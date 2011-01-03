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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import uk.ac.cardiff.model.AdministrativeFunction;
import uk.ac.cardiff.model.MUAMetadata;
import uk.ac.cardiff.model.Graph.AggregatorGraphModel;
import uk.ac.cardiff.model.wsmodel.Capabilities;
import uk.ac.cardiff.model.wsmodel.StatisticalUnitInformation;
import uk.ac.cardiff.raptormua.engine.statistics.Statistic;
import uk.ac.cardiff.raptormua.engine.statistics.StatisticsHandler;
import uk.ac.cardiff.raptormua.engine.statistics.StatisticsPostProcessor;
import uk.ac.cardiff.raptormua.model.EntryHandler;
import uk.ac.cardiff.raptormua.model.UAEntry;

/**
 * @author philsmart
 * 
 */
public class MUAEngine {
	static Logger log = LoggerFactory.getLogger(MUAEngine.class);

	private UARegistry uaRegistry;
	private EntryHandler entryHandler;
	private StatisticsHandler statisticsHandler;

	/* holds metadata about the <code>MUAEngine</code> e.g. name etc. */
	private MUAMetadata muaMetadata;

	public MUAEngine() {
		log.info("Setup Multi-Unit Aggregator Engine...");
		log.info("Mulit-Unit Aggregator Engine is running...");
	}

	/**
	 *
	 */
	public void poll() {
		log.info("MultiUnit Aggregator Polling Unit Aggregators");
		for (UAEntry entry : uaRegistry.getUAEntries()) {
			Set entries = entry.getAllAuthentications();
			// log.debug("Setting: "+entries.size());
			getEntryHandler().addEntries(entries);
		}
		getEntryHandler().endTransaction();

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
		/* set the current set of entries held by the MUA for processing */
		statisticsHandler.setEntries(getEntryHandler().getEntries());
		return statisticsHandler.peformStatistic(statisticName);

	}

	/**
	 * @return
	 */
	public Capabilities getCapabilities() {
		// * retrieve all registered stats from the stats handler*/
		List<Statistic> su = statisticsHandler.getStatisticalUnits();
		/* retrieve information about attached UA */
		List<UAEntry> uaentries = uaRegistry.getUAEntries();

		Capabilities capabilities = new Capabilities();

		capabilities.setMuaMetadata(muaMetadata);

		ArrayList<String> ua = new ArrayList();
		for (UAEntry entry : uaentries)
			ua.add(entry.getServiceEndpoint());
		capabilities.setAttached(ua);

		ArrayList<StatisticalUnitInformation> stats = new ArrayList();
		for (Statistic entry : su) {
			StatisticalUnitInformation information = new StatisticalUnitInformation();
			information.setStatisticParameters(entry.getStatisticParameters());

			ArrayList<String> postprocessors = new ArrayList();
			if (entry.getPostprocessor() != null) {
				for (StatisticsPostProcessor postprocessor : entry
						.getPostprocessor()) {
					postprocessors
							.add(postprocessor.getClass().getSimpleName());
				}
			}
			information.setPostprocessors(postprocessors);

			ArrayList<String> preprocessors = new ArrayList();
			if (entry.getPreprocessor() != null)
				preprocessors.add(entry.getPreprocessor().getClass()
						.getSimpleName());
			information.setPreprocessors(preprocessors);

			stats.add(information);
		}
		capabilities.setStatisticalServices(stats);

		return capabilities;
	}

	/**
	 * Sets the configured entry handler. Must also then initialise that entry handler
	 * @param entryHandler
	 */
	public void setEntryHandler(EntryHandler entryHandler) {
		this.entryHandler = entryHandler;
		entryHandler.initialise();
	}

	public EntryHandler getEntryHandler() {
		return entryHandler;
	}

	/**
	 * @param statisticalUnitInformation
	 */
	public void updateStatisticalUnit(
			StatisticalUnitInformation statisticalUnitInformation) {
		log.debug("Updating Statistical Unit {}", statisticalUnitInformation
				.getStatisticParameters().getUnitName());
		statisticsHandler.updateStatisticalUnit(statisticalUnitInformation);

	}

	/**
	 * @param muaMetadata
	 *            the muaMetadata to set
	 */
	public void setMuaMetadata(MUAMetadata muaMetadata) {
		this.muaMetadata = muaMetadata;
	}

	/**
	 * @return the muaMetadata
	 */
	public MUAMetadata getMuaMetadata() {
		return muaMetadata;
	}

	/**
	 * @param function
	 * @return
	 */
	public boolean performAdministrativeFunction(AdministrativeFunction function) {
		switch (function.getAdministrativeFunction()) {
		case REMOVEALL:
			entryHandler.removeAllEntries();
			break;
		}
		return true;
	}

}
