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

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import uk.ac.cardiff.model.AdministrativeFunction;
import uk.ac.cardiff.model.ServiceMetadata;
import uk.ac.cardiff.model.event.Event;
import uk.ac.cardiff.model.report.AggregatorGraphModel;
import uk.ac.cardiff.model.wsmodel.Capabilities;
import uk.ac.cardiff.model.wsmodel.EventPushMessage;
import uk.ac.cardiff.model.wsmodel.LogFileUpload;
import uk.ac.cardiff.model.wsmodel.LogFileUploadResult;
import uk.ac.cardiff.model.wsmodel.StatisticalUnitInformation;
import uk.ac.cardiff.model.wsmodel.SuggestionValues;
import uk.ac.cardiff.raptor.parse.BaseEventParser;
import uk.ac.cardiff.raptor.parse.DataAccessRegister;
import uk.ac.cardiff.raptor.parse.EventParserNotFoundException;
import uk.ac.cardiff.raptor.parse.ParserException;
import uk.ac.cardiff.raptor.registry.Endpoint;
import uk.ac.cardiff.raptor.remoting.client.EventReleaseClient;
import uk.ac.cardiff.raptor.remoting.client.ReleaseFailureException;
import uk.ac.cardiff.raptor.runtimeutils.ReflectionHelper;
import uk.ac.cardiff.raptor.store.StorageEngine;
import uk.ac.cardiff.raptor.store.TransactionInProgressException;
import uk.ac.cardiff.raptormua.engine.statistics.Statistic;
import uk.ac.cardiff.raptormua.engine.statistics.StatisticsHandler;
import uk.ac.cardiff.raptormua.engine.statistics.StatisticsPostProcessor;
import uk.ac.cardiff.raptormua.model.Users;

/**
 * @author philsmart
 *
 */
public class MUAEngine {

	/** Class logger. */
	private final Logger log = LoggerFactory.getLogger(MUAEngine.class);

	/** Performs all statistics. */
	private StatisticsHandler statisticsHandler;

	/**
	 * The client that is used to process, filter and send events to another MUA
	 * instance.
	 */
	private EventReleaseClient eventReleaseClient;

	// TODO implement user level control on the MUA?
	/** Support for user classification on the MUA. */
	private Users users;

	/** The Storage Engine that handles all storage transactions. */
	private StorageEngine storageEngine;

	/** Metadata about the this MUA instance. */
	private ServiceMetadata muaMetadata;

	/** Used to parse batch uploads. */
	private DataAccessRegister dataAccessRegister;

	public MUAEngine() {
		log.info("Setup Multi-Unit Aggregator Engine...");
		log.info("Mulit-Unit Aggregator Engine is running...");
	}

	/**
	 * Sets the statisticalhandler.
	 *
	 * @param statisticsHandler
	 *            the statistichandler to set
	 */
	public final void setStatisticsHandler(final StatisticsHandler statisticsHandler) {
		this.statisticsHandler = statisticsHandler;
	}

	public final StatisticsHandler getStatisticsHandler() {
		return statisticsHandler;
	}

	/**
	 * @param statisticName
	 */
	public final AggregatorGraphModel performStatistic(final String statisticName) {
		// TODO we do not need to set this each time
		statisticsHandler.setEntryHandler(storageEngine.getEntryHandler());
		return statisticsHandler.peformStatistic(statisticName);

	}


	    /**
	     * First, find the earliest event that needs to be retrieved from the
	     * storage engine - which may contain duplicates to those already sent,
	     * but these are filtered by the releaseClient later.
	     * Then send those events to the event release client.
	     *
	     * @return
	     */
	 public final boolean release() {
	     List<Endpoint> endpoints = eventReleaseClient.getEndpoints();
	     DateTime earliestReleaseTime = null;
             Endpoint endpointWithEarliestReleaseTime=null;
             for (Endpoint endpoint :endpoints){
                     if (earliestReleaseTime==null){
                             earliestReleaseTime = endpoint.getReleaseInformation().getLastReleasedEventTime();
                             endpointWithEarliestReleaseTime = endpoint;
                     }
                     if (endpoint.getReleaseInformation().getLastReleasedEventTime().isBefore(earliestReleaseTime)){
                             earliestReleaseTime = endpoint.getReleaseInformation().getLastReleasedEventTime();
                             endpointWithEarliestReleaseTime = endpoint;
                     }
             }

	        List<Event> eventsToSend = storageEngine.getEventsOnOrAfter(earliestReleaseTime);

	        boolean success = false;
	        try {
	                success = eventReleaseClient.release(eventsToSend, getMuaMetadata());
	        } catch (ReleaseFailureException e) {
	                log.error("Event Release failed ", e);
	        }
	        return success;
	}


	/**
	 * Gets the capabilities of this MUA, also sets some default values and
	 * possible values for the calling component to use
	 *
	 * @return
	 */
	public final Capabilities getCapabilities() {

		List<Statistic> su = statisticsHandler.getStatisticalUnits();

		Capabilities capabilities = new Capabilities();
		capabilities.setMetadata(this.getMuaMetadata());

		// set possible values
		SuggestionValues suggestionValues = new SuggestionValues();
		suggestionValues.setPossibleFieldNameValues(ReflectionHelper.getFieldsFromEntrySubClasses());
		capabilities.setSuggestionValues(suggestionValues);
		capabilities.setNumberOfAuthenticationsStored(storageEngine.getEntryHandler().getNumberOfEntries());

		ArrayList<StatisticalUnitInformation> stats = new ArrayList();
		for (Statistic entry : su) {
			log.debug("Setting statistical unit information as: " + entry.getStatisticParameters().getUnitName());
			StatisticalUnitInformation information = new StatisticalUnitInformation();

			information.setStatisticParameters(entry.getStatisticParameters());

			ArrayList<String> postprocessors = new ArrayList<String>();
			if (entry.getPostprocessor() != null) {
				for (StatisticsPostProcessor postprocessor : entry.getPostprocessor()) {
					postprocessors.add(postprocessor.getClass().getSimpleName());
				}
			}
			information.setPostprocessors(postprocessors);

			ArrayList<String> preprocessors = new ArrayList<String>();
			if (entry.getPreprocessor() != null) {
				preprocessors.add(entry.getPreprocessor().getClass().getSimpleName());
			}
			information.setPreprocessors(preprocessors);

			stats.add(information);
		}
		capabilities.setStatisticalServices(stats);
		log.debug("Constructed MUA Capabilities, {}", capabilities);
		return capabilities;
	}

	/**
	 * Use the configured raptor parsing library to store the incomming
	 * <code>uploadFiles</code>
	 *
	 * @param uploadFiles
	 *            the files to parse and store
	 * @throws TransactionInProgressException
	 */
	public final List<LogFileUploadResult> batchParse(final List<LogFileUpload> uploadFiles) throws TransactionInProgressException {
		log.info("Going to parse {} batch uploaded files", uploadFiles.size());
		ArrayList<Event> allEvents = new ArrayList<Event>();

		ArrayList<LogFileUploadResult> results = new ArrayList<LogFileUploadResult>();

		for (LogFileUpload logfileUpload : uploadFiles) {
			LogFileUploadResult result = new LogFileUploadResult();
			result.setId(logfileUpload.getId());
			try {
				BaseEventParser parser = dataAccessRegister.getParsingModuleForType(logfileUpload.getEventType().friendlyName);
				log.debug("Parsing {} using parser {} for type {}", new Object[] { logfileUpload.getName(), parser.getClass(), logfileUpload.getEventType() });
				parser.parse(logfileUpload.getData());
				allEvents.addAll(parser.getEntryHandler().getEntries());
				parser.removeAllEntries();
				result.setStatus("Parsed On the MUA");
				result.setProcessed(true);

			} catch (ParserException e) {
				log.error("Error Parsing the batch uploaded log file {}, with reason", logfileUpload.getName(), e.getMessage());

				result.setStatus("Failed To Parse");
				result.setProcessed(false);
			} catch (EventParserNotFoundException e) {
				log.error("Event parser could not be found for {}, with reason {}", logfileUpload.getName(), e.getMessage());

				result.setStatus("Failed To Parse");
				result.setProcessed(false);
			}
			results.add(result);
		}
		log.info("Storing all {} parsed events", allEvents.size());
		int transactionId = (int) (Math.random() * 1000000);
		storageEngine.performAsynchronousEntryStoragePipeline(transactionId, allEvents);

		return results;

	}

	/**
	 * @param statisticalUnitInformation
	 */
	public final void updateStatisticalUnit(final StatisticalUnitInformation statisticalUnitInformation) {
		log.debug("Updating Statistical Unit {}", statisticalUnitInformation.getStatisticParameters().getUnitName());
		statisticsHandler.updateStatisticalUnit(statisticalUnitInformation);

	}

	/**
	 * @param function
	 * @return
	 */
	public final boolean performAdministrativeFunction(final AdministrativeFunction function) {
		switch (function.getAdministrativeFunction()) {
		case REMOVEALL:
			storageEngine.removeAllEntries();
			break;
		default:
			break;
		}
		return true;
	}

	/**
	 * @param pushed
	 * @throws TransactionInProgressException
	 */
	public final void addAuthentications(final EventPushMessage pushed) throws TransactionInProgressException {
		int transactionId = (int) (Math.random() * 1000000);
		storageEngine.performAsynchronousEntryStoragePipeline(transactionId, pushed.getEvents());

	}

	public final void setMuaMetadata(final ServiceMetadata muaMetadata) {
		this.muaMetadata = muaMetadata;
	}

	public final ServiceMetadata getMuaMetadata() {
		return muaMetadata;
	}

	public final void setEventReleaseClient(final EventReleaseClient eventReleaseClient) {
		this.eventReleaseClient = eventReleaseClient;
	}

	public final EventReleaseClient getEventReleaseClient() {
		return eventReleaseClient;
	}

	/**
	 * @param storageEngine
	 *            the storageEngine to set
	 */
	public final void setStorageEngine(final StorageEngine storageEngine) {
		this.storageEngine = storageEngine;
	}

	/**
	 * @return the storageEngine
	 */
	public final StorageEngine getStorageEngine() {
		return storageEngine;
	}

	/**
	 * @param dataAccessRegister
	 *            the dataAccessRegister to set
	 */
	public final void setDataAccessRegister(final DataAccessRegister dataAccessRegister) {
		this.dataAccessRegister = dataAccessRegister;
	}

	/**
	 * @return the dataAccessRegister
	 */
	public final DataAccessRegister getDataAccessRegister() {
		return dataAccessRegister;
	}

}
