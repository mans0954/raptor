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
import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.Assert;

import uk.ac.cardiff.model.AdministrativeFunction;
import uk.ac.cardiff.model.ServiceMetadata;
import uk.ac.cardiff.model.event.Event;
import uk.ac.cardiff.model.report.AggregatorGraphModel;
import uk.ac.cardiff.model.resource.ResourceMetadata;
import uk.ac.cardiff.model.wsmodel.Capabilities;
import uk.ac.cardiff.model.wsmodel.EventPushMessage;
import uk.ac.cardiff.model.wsmodel.LogFileUpload;
import uk.ac.cardiff.model.wsmodel.LogFileUploadResult;
import uk.ac.cardiff.model.wsmodel.StatisticalUnitInformation;
import uk.ac.cardiff.raptor.parse.BaseEventParser;
import uk.ac.cardiff.raptor.parse.DataAccessRegister;
import uk.ac.cardiff.raptor.parse.EventParserNotFoundException;
import uk.ac.cardiff.raptor.parse.ParserException;
import uk.ac.cardiff.raptor.registry.Endpoint;
import uk.ac.cardiff.raptor.remoting.client.EventReleaseClient;
import uk.ac.cardiff.raptor.remoting.client.ReleaseFailureException;
import uk.ac.cardiff.raptor.store.EventStorageEngine;
import uk.ac.cardiff.raptor.store.ResourceStorageEngine;
import uk.ac.cardiff.raptor.store.TransactionInProgressException;
import uk.ac.cardiff.raptormua.engine.statistics.StatisticHandler;
import uk.ac.cardiff.raptormua.upload.BatchFile;

/**
 * The Class MUAEngine.
 * 
 * @author philsmart
 */
public final class MUAEngine implements InitializingBean {

    /** Class logger. */
    private final Logger log = LoggerFactory.getLogger(MUAEngine.class);

    /** Performs all statistics. */
    private StatisticHandler statisticsHandler;

    /**
     * The client that is used to process, filter and send events to another MUA instance.
     */
    private EventReleaseClient eventReleaseClient;

    /** The Storage Engine that handles all storage transactions for events. */
    private EventStorageEngine storageEngine;

    /**
     * The storage engine that handles all storage transactions for resources.
     */
    private ResourceStorageEngine resourceStorageEngine;

    /** Metadata about the this MUA instance. */
    private ServiceMetadata muaMetadata;

    /** Used to parse batch uploads. */
    private DataAccessRegister dataAccessRegister;

    /**
     * The Maximum number of events that can be released (e.g. to another MUA) at any one time.
     */
    private int maxReleaseEventSize;

    /** Constructor for creating and storing this MUAs capabilities. */
    private BaseCapabilitiesContructor capabilitiesConstructor;

    /**
     * Whether to force all event storage to occur synchronously.
     */
    private boolean forceSynchronousEventStorage;

    /**
     * Instantiates a new mUA engine.
     */
    public MUAEngine() {
        log.info("Setup Multi-Unit Aggregator Engine...");
        maxReleaseEventSize = 100;

    }

    /**
     * After properties have been set, check for required dependencies and construct an initial capabilities.
     */
    public void afterPropertiesSet() {
        Assert.notNull(capabilitiesConstructor,
                "No Capabilities constructor found, this is a FATAL error, please add one to the engine in mua-core.xml");
        capabilitiesConstructor.initialiseCapabilities();
        log.info("Mulit-Unit Aggregator Engine is running...");

    }

    /**
     * Sets the statisticalhandler.
     * 
     * @param statisticsHandler the statistichandler to set
     */
    public final void setStatisticsHandler(final StatisticHandler statisticsHandler) {
        this.statisticsHandler = statisticsHandler;
    }

    /**
     * Gets the statistics handler.
     * 
     * @return the statistics handler
     */
    public final StatisticHandler getStatisticsHandler() {
        return statisticsHandler;
    }

    /**
     * Perform statistic.
     * 
     * @param statisticName the statistic name
     * @return the aggregator graph model
     */
    public final AggregatorGraphModel performStatistic(final String statisticName) {
        statisticsHandler.setEventHandler(storageEngine.getEventHandler());
        return statisticsHandler.performStatistic(statisticName);

    }

    /**
     * Release stored events to configured endpoints. First, find the earliest event that needs to be retrieved from the
     * storage engine - which may contain duplicates to those already sent, but these are filtered by the releaseClient
     * later. Then send those events to the event release client.
     * 
     * @return true, if successful
     */
    public final boolean release() {
        if (eventReleaseClient.isEnabled()) {
            List<Endpoint> endpoints = eventReleaseClient.getEndpoints();
            DateTime earliestReleaseTime = null;
            Endpoint endpointWithEarliestReleaseTime = null;
            for (Endpoint endpoint : endpoints) {
                if (earliestReleaseTime == null) {
                    earliestReleaseTime = endpoint.getReleaseInformation().getLastReleasedEventTime();
                    endpointWithEarliestReleaseTime = endpoint;
                }
                if (endpoint.getReleaseInformation().getLastReleasedEventTime().isBefore(earliestReleaseTime)) {
                    earliestReleaseTime = endpoint.getReleaseInformation().getLastReleasedEventTime();
                    endpointWithEarliestReleaseTime = endpoint;
                }
            }

            List<Event> eventsToSend = storageEngine.getEventsOnOrAfter(earliestReleaseTime, maxReleaseEventSize);

            boolean success = false;
            try {
                success = eventReleaseClient.release(eventsToSend, getMuaMetadata());
            } catch (ReleaseFailureException e) {
                log.error("Event Release failed ", e);
            }
            return success;
        }
        return false;
    }

    /**
     * Gets the statistical capabilities of this MUA, also sets some default values and possible values for the calling
     * view component to display to the user.
     * 
     * @return the capabilities of this MUA
     */
    public final Capabilities getCapabilities() {
        if (capabilitiesConstructor != null) {
            return capabilitiesConstructor.getCapabilities();
        } else {
            log.error("No Capabilities constructor found, this is a FATAL error, please add one to the engine in mua-core.xml");
        }
        return null;
    }

    /**
     * Use the configured raptor parsing library to store the incoming <code>uploadFiles</code>.
     * 
     * @param uploadFiles the files to parse and store
     * @return the list
     * @throws TransactionInProgressException the transaction in progress exception
     */
    public final List<LogFileUploadResult> batchParse(final List<LogFileUpload> uploadFiles)
            throws TransactionInProgressException {
        log.info("Going to parse {} batch uploaded files", uploadFiles.size());
        ArrayList<Event> allEvents = new ArrayList<Event>();

        ArrayList<LogFileUploadResult> results = new ArrayList<LogFileUploadResult>();

        for (LogFileUpload logfileUpload : uploadFiles) {
            LogFileUploadResult result = new LogFileUploadResult();
            result.setId(logfileUpload.getId());
            try {
                BaseEventParser parser =
                        dataAccessRegister.getParsingModuleForType(logfileUpload.getEventType().friendlyName);
                log.debug("Parsing {} using parser {} for type {}",
                        new Object[] {logfileUpload.getName(), parser.getClass(), logfileUpload.getEventType()});
                parser.parse(logfileUpload.getData());
                allEvents.addAll(parser.getEventHandler().getEvents());
                parser.reset();
                result.setStatus("Parsed On the MUA");
                result.setProcessed(true);

            } catch (ParserException e) {
                log.error("Error Parsing the batch uploaded log file {}, with reason", logfileUpload.getName(),
                        e.getMessage());

                result.setStatus("Failed To Parse");
                result.setProcessed(false);
            } catch (EventParserNotFoundException e) {
                log.error("Event parser could not be found for {}, with reason {}", logfileUpload.getName(),
                        e.getMessage());

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
     * Batch parse files.
     * 
     * @param uploadFiles the upload files
     * @throws TransactionInProgressException the transaction in progress exception
     */
    public final void batchParseFiles(final List<BatchFile> uploadFiles) throws TransactionInProgressException {
        log.info("Going to parse {} batch uploaded files", uploadFiles.size());

        for (BatchFile batchFile : uploadFiles) {
            try {
                BaseEventParser parser =
                        dataAccessRegister.getParsingModuleForType(batchFile.getEventType().friendlyName);
                log.debug("Parsing {} using parser {} for type {}", new Object[] {batchFile.getLogFile().getName(),
                        parser.getClass(), batchFile.getEventType()});
                parser.parse(batchFile.getLogFile());
                List<Event> events = parser.getEventHandler().getEvents();
                parser.reset();
                log.info("Storing all {} parsed events", events.size());
                int transactionId = (int) (Math.random() * 1000000);
                storageEngine.performSynchronousEntryStoragePipeline(transactionId, events);
                batchFile.getLogFile().delete();

            } catch (ParserException e) {
                log.error("Error Parsing the batch uploaded log file {}, with reason",
                        batchFile.getLogFile().getName(), e.getMessage());
            } catch (EventParserNotFoundException e) {
                log.error("Event parser could not be found for {}, with reason {}", batchFile.getLogFile().getName(),
                        e.getMessage());
            }
        }

    }

    /**
     * Update the statistical unit described in <code>statisticalUnitInformation</code>. Invalidate the capabilities
     * cache, as statistical unit has changed.
     * 
     * @param statisticalUnitInformation the statistical unit information
     */
    public final void updateStatisticalUnit(final StatisticalUnitInformation statisticalUnitInformation) {
        statisticsHandler.updateStatisticalUnit(statisticalUnitInformation);
        capabilitiesConstructor.invalidateCache();

    }

    /**
     * Save and apply resource classification.
     * 
     * @param resourceMetadata the resource metadata
     */
    public void saveAndApplyResourceClassification(List<ResourceMetadata> resourceMetadata) {
        resourceStorageEngine.performAsynchronousResourceStoragePipeline(resourceMetadata);
    }

    /**
     * Perform administrative function described in the <code>function<code> parameter.
     * 
     * @param function information about the administrative function to perform.
     * @return true, iff the administrative function performed successfully, false otherwise
     */
    public final boolean performAdministrativeFunction(final AdministrativeFunction function) {
        switch (function.getAdministrativeFunction()) {
            case REMOVEALL:
                // storageEngine.removeAllEntries();
                break;
            default:
                break;
        }
        return true;
    }

    /**
     * If <code>pushed</code> contains events to add then sent them to the <code>storageEngine</code> for processing,
     * otherwise do nothing.
     * 
     * @param pushed the {@link uk.ac.cardiff.model.wsmodel.EventPushMessage} received from the client.
     * @throws TransactionInProgressException the transaction in progress exception
     */
    public final void addAuthentications(final EventPushMessage pushed) throws TransactionInProgressException {
        if (pushed.getEvents().size() > 0) {
            int transactionId = (int) (Math.random() * 1000000);
            if (!forceSynchronousEventStorage) {
                storageEngine.performAsynchronousEntryStoragePipeline(transactionId, pushed.getEvents());
            } else {
                storageEngine.performSynchronousEntryStoragePipeline(transactionId, pushed.getEvents());
            }

        }
    }

    /**
     * Sets the mua metadata.
     * 
     * @param muaMetadata the new mua metadata
     */
    public final void setMuaMetadata(final ServiceMetadata muaMetadata) {
        this.muaMetadata = muaMetadata;
    }

    /**
     * Gets the mua metadata.
     * 
     * @return the mua metadata
     */
    public final ServiceMetadata getMuaMetadata() {
        return muaMetadata;
    }

    /**
     * Sets the event release client.
     * 
     * @param eventReleaseClient the new event release client
     */
    public final void setEventReleaseClient(final EventReleaseClient eventReleaseClient) {
        this.eventReleaseClient = eventReleaseClient;
    }

    /**
     * Gets the event release client.
     * 
     * @return the event release client
     */
    public final EventReleaseClient getEventReleaseClient() {
        return eventReleaseClient;
    }

    /**
     * Sets the storage engine.
     * 
     * @param storageEngine the storageEngine to set
     */
    public final void setStorageEngine(final EventStorageEngine storageEngine) {
        this.storageEngine = storageEngine;
    }

    /**
     * Gets the storage engine.
     * 
     * @return the storageEngine
     */
    public final EventStorageEngine getStorageEngine() {
        return storageEngine;
    }

    /**
     * Sets the data access register.
     * 
     * @param dataAccessRegister the dataAccessRegister to set
     */
    public final void setDataAccessRegister(final DataAccessRegister dataAccessRegister) {
        this.dataAccessRegister = dataAccessRegister;
    }

    /**
     * Gets the data access register.
     * 
     * @return the dataAccessRegister
     */
    public final DataAccessRegister getDataAccessRegister() {
        return dataAccessRegister;
    }

    /**
     * Sets the max release event size.
     * 
     * @param maxReleaseEventSize the maxReleaseEventSize to set
     */
    public void setMaxReleaseEventSize(int maxReleaseEventSize) {
        if (maxReleaseEventSize > 3000) {
            log.warn("Max Release Event size can not be set higher than 3000, defaulting to 3000");
            this.maxReleaseEventSize = 3000;
        } else {
            this.maxReleaseEventSize = maxReleaseEventSize;
        }
    }

    /**
     * Gets the max release event size.
     * 
     * @return the maxReleaseEventSize
     */
    public int getMaxReleaseEventSize() {
        return maxReleaseEventSize;
    }

    /**
     * Sets the capabilities constructor.
     * 
     * @param capabilitiesConstructor the capabilitiesConstructor to set
     */
    public void setCapabilitiesConstructor(BaseCapabilitiesContructor capabilitiesConstructor) {
        this.capabilitiesConstructor = capabilitiesConstructor;
    }

    /**
     * Gets the capabilities constructor.
     * 
     * @return the capabilitiesConstructor
     */
    public BaseCapabilitiesContructor getCapabilitiesConstructor() {
        return capabilitiesConstructor;
    }

    /**
     * @param resourceStorageEngine the resourceStorageEngine to set
     */
    public void setResourceStorageEngine(ResourceStorageEngine resourceStorageEngine) {
        this.resourceStorageEngine = resourceStorageEngine;
    }

    /**
     * @return the resourceStorageEngine
     */
    public ResourceStorageEngine getResourceStorageEngine() {
        return resourceStorageEngine;
    }

    /**
     * @param forceSynchronousEventStorage the forceSynchronousEventStorage to set
     */
    public void setForceSynchronousEventStorage(boolean forceSynchronousEventStorage) {
        this.forceSynchronousEventStorage = forceSynchronousEventStorage;
    }

    /**
     * @return the forceSynchronousEventStorage
     */
    public boolean isForceSynchronousEventStorage() {
        return forceSynchronousEventStorage;
    }

}
