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
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import uk.ac.cardiff.model.ServiceMetadata;
import uk.ac.cardiff.model.resource.ResourceMetadata;
import uk.ac.cardiff.model.wsmodel.Capabilities;
import uk.ac.cardiff.model.wsmodel.EventTypeInformation;
import uk.ac.cardiff.model.wsmodel.ProcessorInformation;
import uk.ac.cardiff.model.wsmodel.StatisticalUnitInformation;
import uk.ac.cardiff.model.wsmodel.SuggestionValues;
import uk.ac.cardiff.raptor.runtimeutils.ReflectionHelper;
import uk.ac.cardiff.raptor.store.EventStorageEngine;
import uk.ac.cardiff.raptormua.engine.statistics.BaseStatistic;
import uk.ac.cardiff.raptormua.engine.statistics.StatisticHandler;
import uk.ac.cardiff.raptormua.engine.statistics.StatisticProcessorRegistry;
import uk.ac.cardiff.raptormua.engine.statistics.processor.ProcessorTemplate;
import uk.ac.cardiff.raptormua.runtimeutils.ResourceMetadataComparator;

/**
 * @author philsmart
 * 
 */
public class CapabilitiesConstructor extends BaseCapabilitiesContructor implements ApplicationContextAware {

    /** Class logger. */
    private final Logger log = LoggerFactory.getLogger(CapabilitiesConstructor.class);

    /** Whether to cache capabilities */
    private boolean cacheEnabled;

    private Capabilities cachedCapabilities;

    /** How long the cache is valid for in milliseconds */
    private long cacheTimeoutMs;

    /** The time at which the cache was last reset */
    private long cacheResetTimeMs;

    /** Set containing the names of fields that should not be included in the list of possible field values */
    private Set<String> excludeFieldNames;
    
    /**
     * If set to true, cache timeout is ignored, and new capabilities are constructed from scratch.
     */
    private boolean invalidateCache;

    /** Springs application context */
    private ApplicationContext applicationContext;

    /**
     * Constructos the capabilities of this MUA instance, by computing and storing the following:
     * <ul>
     * <li>Stores the metadata assoicated to this MUA.
     * </ul>
     * 
     * @return the {@link Capabilities} of this MUA.
     */
    public Capabilities constructCapabilities(StatisticHandler statisticsHandler, EventStorageEngine storageEngine,
            ServiceMetadata metadata) {
        log.info("Capabilities constructor called");
        long startTime = System.currentTimeMillis();

        Capabilities capabilities = null;

        checkCacheValidity();

        if (cacheEnabled && cachedCapabilities != null) {
            log.info("Capabilities retrieved from cache, cache will timeout in {} milliseconds", cacheTimeoutMs
                    - (System.currentTimeMillis() - cacheResetTimeMs));
            capabilities = cachedCapabilities;
        }

        if (capabilities == null) {

            capabilities = new Capabilities();
            capabilities.setMetadata(metadata);

            try {
                String version =
                        Version.getMajorVersion() + "." + Version.getMinorVersion() + "." + Version.getMicroVersion();
                capabilities.setMuaVersion(version);
            } catch (Throwable e) {
                capabilities.setMuaVersion("Unknown");
            }

            addStatisticInformation(capabilities, statisticsHandler);
            addSuggestions(capabilities, storageEngine, statisticsHandler);
            addEventTotals(capabilities, storageEngine);
            addEventDateInformation(capabilities, storageEngine);
            addResourceInformation(capabilities, storageEngine);

            if (cacheEnabled) {
                cachedCapabilities = capabilities;
                cacheResetTimeMs = System.currentTimeMillis();
            }
        }
        long endTime = System.currentTimeMillis();
        log.info("Constructed MUA Capabilities for [{}] in {}s", capabilities.getMetadata().getEntityId(),
                ((endTime - startTime) / 1000));

        return capabilities;
    }
    
    public void invalidateCache() {
        invalidateCache =true;        
    }

    public void addStatisticInformation(Capabilities capabilities, StatisticHandler statisticsHandler) {
        log.debug("Adding statistical information to capabilities");
        ArrayList<StatisticalUnitInformation> stats = new ArrayList<StatisticalUnitInformation>();
        List<BaseStatistic> su = statisticsHandler.getStatisticalUnits();
        for (BaseStatistic entry : su) {
            log.debug("Setting statistical unit information as: " + entry.getStatisticParameters().getUnitName());
            StatisticalUnitInformation information = new StatisticalUnitInformation();
            information.setStatisticParameters(entry.getStatisticParameters());
            information.setPostprocessors(entry.getAttachProcessors());
            stats.add(information);
        }
        capabilities.setStatisticalServices(stats);
    }

    private void addSuggestions(Capabilities capabilities, EventStorageEngine storageEngine,
            StatisticHandler statisticsHandler) {
        log.debug("Adding suggestion information to capabilities");
        SuggestionValues suggestionValues = new SuggestionValues();
        suggestionValues.setPossibleFieldNameValues(ReflectionHelper.getFieldsFromEntrySubClasses());
        capabilities.setSuggestionValues(suggestionValues);

        StatisticProcessorRegistry processorRegistry =
                statisticsHandler.getStatisticRegistry().getStatisticProcessorRegistry();

        List<ProcessorTemplate> templates = processorRegistry.getStatisticProcessorTemplates();
        List<ProcessorInformation> possiblePostProcessors = new ArrayList<ProcessorInformation>();
        for (ProcessorTemplate template : templates) {
            ProcessorInformation information = new ProcessorInformation();
            information.setFriendlyName(template.getProcessorFriendlyName());
            information.setProcessorClass(template.getProcessorClass().getCanonicalName());
            information.setMethodParameters(template.getMethodParameters());
            possiblePostProcessors.add(information);

        }

        log.trace("Has set {} possible post processor suggestion values", possiblePostProcessors.size());
        suggestionValues.setPossiblePostProcessors(possiblePostProcessors);

        // set possible values for field names
        List<String> possibleFieldNames = new ArrayList<String>();
        if (excludeFieldNames == null) {
            possibleFieldNames = suggestionValues.getPossibleFieldNameValuesList();
        } else {
            for (String fieldname : suggestionValues.getPossibleFieldNameValuesList()) {
                if (!excludeFieldNames.contains(fieldname)) {
                    possibleFieldNames.add(fieldname);
                }
            }
        }
        log.debug("Capabilities constructor setting possible field values (can cause issues)");
        suggestionValues.setPossibleFieldValues(storageEngine.getPossibleValuesFor(possibleFieldNames));
    }

    private void addEventTotals(Capabilities capabilities, EventStorageEngine storageEngine) {
        log.debug("Adding event totals to capabilities");
        capabilities.setNumberOfAuthenticationsStored(storageEngine.getEventHandler().getNumberOfEvents());
        List<String> eventTypes = ReflectionHelper.getAllEventClasses();
        List<EventTypeInformation> eventsPerType = new ArrayList<EventTypeInformation>();
        for (String eventType : eventTypes) {
            String query = "SELECT count(*) from " + eventType;
            try {
                long count = (Long) storageEngine.getEventHandler().queryUnique(query, null);
                log.debug("EventType {} has {} events", eventType, count);
                eventsPerType.add(new EventTypeInformation(eventType, count));
            } catch (Exception e) {
                log.warn("EventType [{}] has no count (table probably does not exist yet, no problem)", eventType);
            }
        }
        capabilities.setEventsPerType(eventsPerType);
    }

    private void addEventDateInformation(Capabilities capabilities, EventStorageEngine storageEngine) {
        log.debug("Adding event dates to capabilities");
        DateTime latest =
                (DateTime) storageEngine.getEventHandler().queryUnique("SELECT max(eventTime) from Event", null);
        DateTime earliest =
                (DateTime) storageEngine.getEventHandler().queryUnique("SELECT min(eventTime) from Event", null);
        if (latest!=null && earliest !=null){
            capabilities.setLatestEventTime(new Date(latest.getMillis()));
            capabilities.setEarliestEventTime(new Date(earliest.getMillis()));
        }
    }

    private void addResourceInformation(Capabilities capabilities, EventStorageEngine storageEngine) {
        log.debug("Adding resource metadata to capabilities");
        List<ResourceMetadata> resourceMetadata =
                (List<ResourceMetadata>) storageEngine.getEventHandler().query("from ResourceMetadata", null);
        log.debug("Setting {} resource metadata(s)", resourceMetadata.size());
        Collections.sort(resourceMetadata, new ResourceMetadataComparator());
        capabilities.setResourceMetadata(resourceMetadata);
    }

    private void checkCacheValidity() {
        if (cacheTimeoutMs == 0 || !cacheEnabled) {
            return;
        }
        if (invalidateCache){
            log.info("Capabilities cache was forced cleared");
            cachedCapabilities = null;
            invalidateCache=false;
            return;
        }
        long currentTimeMillis = System.currentTimeMillis();
        boolean shouldReset = (currentTimeMillis - cacheResetTimeMs) > cacheTimeoutMs;
        if (shouldReset) {
            log.info("Capabilities cache was cleared, timeout reached");
            cachedCapabilities = null;
        }
    }

    /**
     * Sets whether the cache is enabled, and sets a default timeout of 30 minutes in case one is not specified in the
     * XML
     * 
     * @param cacheEnabled the cacheEnabled to set
     */
    public void setCacheEnabled(boolean cacheEnabled) {
        this.cacheEnabled = cacheEnabled;
        cacheTimeoutMs = 1800000;
    }

    /**
     * @return the cacheEnabled
     */
    public boolean isCacheEnabled() {
        return cacheEnabled;
    }

    /**
     * @param cacheTimeoutMs the cacheTimeoutMs to set
     */
    public void setCacheTimeoutMs(long cacheTimeoutMs) {
        this.cacheTimeoutMs = cacheTimeoutMs;
    }

    /**
     * @return the cacheTimeoutMs
     */
    public long getCacheTimeoutMs() {
        return cacheTimeoutMs;
    }

    /**
     * @param excludeFieldNames the excludeFieldNames to set
     */
    public void setExcludeFieldNames(Set<String> excludeFieldNames) {
        this.excludeFieldNames = excludeFieldNames;
    }

    /**
     * @return the excludeFieldNames
     */
    public Set<String> getExcludeFieldNames() {
        return excludeFieldNames;
    }

    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;

    }


}
