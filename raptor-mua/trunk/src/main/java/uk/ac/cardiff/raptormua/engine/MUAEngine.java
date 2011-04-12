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
import uk.ac.cardiff.model.ClientMetadata;
import uk.ac.cardiff.model.ServerMetadata;
import uk.ac.cardiff.model.report.AggregatorGraphModel;
import uk.ac.cardiff.model.report.Series;
import uk.ac.cardiff.model.wsmodel.Capabilities;
import uk.ac.cardiff.model.wsmodel.EventPushMessage;
import uk.ac.cardiff.model.wsmodel.StatisticalUnitInformation;
import uk.ac.cardiff.model.wsmodel.SuggestionValues;
import uk.ac.cardiff.raptor.event.expansion.AttributeAssociationEngine;
import uk.ac.cardiff.raptor.remoting.client.EventReleaseClient;
import uk.ac.cardiff.raptor.runtimeutils.ReflectionHelper;
import uk.ac.cardiff.raptor.store.EntryHandler;
import uk.ac.cardiff.raptor.store.StorageEngine;
import uk.ac.cardiff.raptormua.engine.statistics.Statistic;
import uk.ac.cardiff.raptormua.engine.statistics.StatisticsHandler;
import uk.ac.cardiff.raptormua.engine.statistics.StatisticsPostProcessor;
import uk.ac.cardiff.raptormua.model.Users;


/**
 * @author philsmart
 *
 */
public class MUAEngine {

        /** Class logger*/
	private final Logger log = LoggerFactory.getLogger(MUAEngine.class);

	/** Performs all statistics*/
	private StatisticsHandler statisticsHandler;

	/** The client that is used to process, filter and send events to another MUA instance*/
	private EventReleaseClient eventReleaseClient;

	/** Engine used to associate attributes to existing events in the MUA */
	private AttributeAssociationEngine attributeAssociationEngine;

	//TODO implement user level control on the MUA?
	private Users users;

	/** The Storage Engine that handles all storage transactions*/
	private StorageEngine storageEngine;

	/** Metadata about the this MUA instance */
	private ServerMetadata muaMetadata;

	public MUAEngine() {
		log.info("Setup Multi-Unit Aggregator Engine...");
		log.info("Mulit-Unit Aggregator Engine is running...");
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
		//TODO we do not need to set this each time
		statisticsHandler.setEntryHandler(storageEngine.getEntryHandler());
		return statisticsHandler.peformStatistic(statisticName);

	}

	/**
	 * Gets the capabilities of this MUA, also sets some default values and possible values for the calling component to use
	 * @return
	 */
	public Capabilities getCapabilities() {

		List<Statistic> su = statisticsHandler.getStatisticalUnits();

		Capabilities capabilities = new Capabilities();
		capabilities.setMetadata(this.getMuaMetadata());


		//set possible values
		SuggestionValues suggestionValues = new SuggestionValues();
		suggestionValues.setPossibleFieldNameValues(ReflectionHelper.getFieldsFromEntrySubClasses());
		capabilities.setSuggestionValues(suggestionValues);

		log.debug("Possible values set");

		ArrayList<StatisticalUnitInformation> stats = new ArrayList();
		for (Statistic entry : su) {
			log.debug("Setting statistical unit information as: "+entry.getStatisticParameters().getUnitName());
			StatisticalUnitInformation information = new StatisticalUnitInformation();

			information.setStatisticParameters(entry.getStatisticParameters());

			ArrayList<String> postprocessors = new ArrayList();
			if (entry.getPostprocessor() != null) {
				for (StatisticsPostProcessor postprocessor : entry.getPostprocessor()) {
					postprocessors.add(postprocessor.getClass().getSimpleName());
				}
			}
			information.setPostprocessors(postprocessors);

			ArrayList<String> preprocessors = new ArrayList();
			if (entry.getPreprocessor() != null)
				preprocessors.add(entry.getPreprocessor().getClass().getSimpleName());
			information.setPreprocessors(preprocessors);

			stats.add(information);
		}
		capabilities.setStatisticalServices(stats);
		log.debug("Constructed MUA Capabilities, {}",capabilities);
		return capabilities;
	}


	/**
	 * @param statisticalUnitInformation
	 */
	public void updateStatisticalUnit(StatisticalUnitInformation statisticalUnitInformation) {
		log.debug("Updating Statistical Unit {}", statisticalUnitInformation.getStatisticParameters().getUnitName());
		statisticsHandler.updateStatisticalUnit(statisticalUnitInformation);

	}


	/**
	 * @param function
	 * @return
	 */
	public boolean performAdministrativeFunction(AdministrativeFunction function) {
		switch (function.getAdministrativeFunction()) {
		case REMOVEALL:
			storageEngine.removeAllEntries();
			break;
		}
		return true;
	}

	/**
	 * @param pushed
	 */
	public void addAuthentications(EventPushMessage pushed) {
	        int transactionId = (int)(Math.random()*1000000);
		log.info("Committing {} entries to the storage engine, with transaction id [{}]", pushed.getEvents().size(),transactionId);
		storageEngine.addEntriesAsynchronous(transactionId,pushed.getEvents());

	}

	public void setMuaMetadata(ServerMetadata muaMetadata) {
		this.muaMetadata = muaMetadata;
	}

	public ServerMetadata getMuaMetadata() {
		return muaMetadata;
	}

	public void setEventReleaseClient(EventReleaseClient eventReleaseClient) {
	    this.eventReleaseClient = eventReleaseClient;
	}

	public EventReleaseClient getEventReleaseClient() {
	    return eventReleaseClient;
	}

    /**
     * @param attributeAssociationEngine the attributeAssociationEngine to set
     */
    public void setAttributeAssociationEngine(AttributeAssociationEngine attributeAssociationEngine) {
        this.attributeAssociationEngine = attributeAssociationEngine;
    }

    /**
     * @return the attributeAssociationEngine
     */
    public AttributeAssociationEngine getAttributeAssociationEngine() {
        return attributeAssociationEngine;
    }

    /**
     * @param storageEngine the storageEngine to set
     */
    public void setStorageEngine(StorageEngine storageEngine) {
        this.storageEngine = storageEngine;
    }

    /**
     * @return the storageEngine
     */
    public StorageEngine getStorageEngine() {
        return storageEngine;
    }

}
