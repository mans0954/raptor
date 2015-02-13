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

package uk.ac.cardiff.raptormua.engine;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Callable;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import uk.ac.cardiff.model.ServiceMetadata;
import uk.ac.cardiff.model.resource.ResourceMetadata;
import uk.ac.cardiff.model.wsmodel.Capabilities;
import uk.ac.cardiff.model.wsmodel.EventTypeInformation;
import uk.ac.cardiff.model.wsmodel.ProcessorInformation;
import uk.ac.cardiff.model.wsmodel.StatisticFunctionType;
import uk.ac.cardiff.model.wsmodel.StatisticalUnitInformation;
import uk.ac.cardiff.model.wsmodel.SuggestionValues;
import uk.ac.cardiff.raptor.runtimeutils.ReflectionHelper;
import uk.ac.cardiff.raptor.store.EventStorageEngine;
import uk.ac.cardiff.raptormua.engine.BaseCapabilitiesContructor.ItemsToCompute;
import uk.ac.cardiff.raptormua.engine.statistics.BaseStatistic;
import uk.ac.cardiff.raptormua.engine.statistics.StatisticHandler;
import uk.ac.cardiff.raptormua.engine.statistics.StatisticProcessorRegistry;
import uk.ac.cardiff.raptormua.engine.statistics.processor.ProcessorTemplate;
import uk.ac.cardiff.raptormua.runtimeutils.ResourceMetadataComparator;

/**
 * Task that constructs capabilities in a background worker thread, before returning control back to the callback class.
 * 
 */
public final class CapabilitiesConstructorTask implements Callable<Boolean> {

    /** Class logger. */
    private final Logger log = LoggerFactory.getLogger(CapabilitiesConstructorTask.class);

    /** What to call when the operations is complete **/
    private final CallbackInterface<Capabilities> callback;

    /** The statiscs handler used to extract statistical units to perform. */
    private final StatisticHandler statisticsHandler;

    /** The storage engine used to extract information about events stored. */
    private final EventStorageEngine storageEngine;

    /** The service metadata used to embedded in the capabilities. */
    private final ServiceMetadata metadata;

    private Capabilities capabilities;
    
    private Capabilities oldCachedCapabilities;
    
    /**
     * Which aspects of the cache should be recomputed.
     */
    private ItemsToCompute toCompute;

    /** Set containing the names of fields that should not be included in the list of possible field values */
    private Set<String> excludeFieldNames;

    public CapabilitiesConstructorTask(CallbackInterface<Capabilities> callbackInterface,
            StatisticHandler statisticsHandlerToUse, EventStorageEngine storageEngineToUse,
            ServiceMetadata metadataToUse, ItemsToCompute toCompute, Capabilities oldCachedCapabilities) {
        callback = callbackInterface;
        statisticsHandler = statisticsHandlerToUse;
        storageEngine = storageEngineToUse;
        metadata = metadataToUse;
        this.toCompute = toCompute;
        this.oldCachedCapabilities = oldCachedCapabilities;
    }

    @Override
    public Boolean call() throws Exception {
        try {
            doConstructCapabilities();
        } catch (Throwable e) {
            log.error("Could not construct capabilities", e);
        }
        callback.returnResult(capabilities);
        return null;
    }
    
    private void doFullCapabilitiesConstruction(){
    	capabilities = new Capabilities();
        capabilities.setMetadata(metadata);
        addStatisticInformation();
        addStatisticTypeInformation();
        addSuggestions();
        addEventTotals();
        addEventDateInformation();
        addResourceInformation();
    }
    
    private void doStatisticalInformationCapabilitiesConstruction(){
    	capabilities = new Capabilities();
        capabilities.setMetadata(metadata);
        addStatisticInformation();
        addStatisticTypeInformation();
        //of note, a reference to the existing object is fine here - we want the old one.
        capabilities.setSuggestionValues(oldCachedCapabilities.getSuggestionValues());
        capabilities.setEventsPerType(oldCachedCapabilities.getEventsPerType());
        capabilities.setLatestEventTime(oldCachedCapabilities.getLatestEventTime());
        capabilities.setEarliestEventTime(oldCachedCapabilities.getEarliestEventTime());
        capabilities.setResourceMetadata(oldCachedCapabilities.getResourceMetadata());

    }

    private void doConstructCapabilities() {
        long startTime = System.currentTimeMillis();
        
        if (toCompute==ItemsToCompute.ALL){
        	log.info("Computing ALL aspects of this MUAs capabilities");
        	doFullCapabilitiesConstruction();
        }else if (toCompute==ItemsToCompute.STATISTICS){
        	log.info("Computing only the statistical information aspects of this MUAs capabilities");
        	doStatisticalInformationCapabilitiesConstruction();
        }
        
        try {
            String version =
                    Version.getMajorVersion() + "." + Version.getMinorVersion() + "." + Version.getMicroVersion();
            capabilities.setMuaVersion(version);
        } catch (Throwable e) {
            capabilities.setMuaVersion("Unknown");
        }

        long endTime = System.currentTimeMillis();
        log.info("Constructed MUA Capabilities for [{}] in {}s", metadata.getEntityId(), ((endTime - startTime) / 1000));

    }

    public void addStatisticInformation() {
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

    public void addStatisticTypeInformation() {
        log.debug("Adding statistical type information to capabilities");
        List<StatisticFunctionType> st = statisticsHandler.getStatisticTypeRegistry().getStatisticTypes();
        capabilities.setStatisticFunctionTypes(st);
    }

    private void addSuggestions() {
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

    private void addEventTotals() {
        log.debug("Adding event totals to capabilities");
        capabilities.setNumberOfAuthenticationsStored(storageEngine.getEventHandler().getNumberOfEvents());
        List<Class<?>> allEventTypes = ReflectionHelper.getAllEventClassTypes();
        List<EventTypeInformation> eventsPerType = new ArrayList<EventTypeInformation>();
        for (Class<?> foundClass : allEventTypes) {
            String className = foundClass.getCanonicalName();
            String query = "SELECT count(*) from " + className;
            try {
                long count = (Long) storageEngine.getEventHandler().queryUnique(query, null);
                log.debug("EventType [{}] has {} events", className, count);
                eventsPerType.add(new EventTypeInformation(className, count));
            } catch (Exception e) {
                log.warn("EventType [{}] has no count (table probably does not exist yet, no problem)", className);
            }
        }
        capabilities.setEventsPerType(eventsPerType);
    }

    private void addEventDateInformation() {
        log.debug("Adding event dates to capabilities");
        DateTime latest =
                (DateTime) storageEngine.getEventHandler().queryUnique("SELECT max(eventTime) from Event", null);
        DateTime earliest =
                (DateTime) storageEngine.getEventHandler().queryUnique("SELECT min(eventTime) from Event", null);
        if (latest != null && earliest != null) {
            capabilities.setLatestEventTime(new Date(latest.getMillis()));
            capabilities.setEarliestEventTime(new Date(earliest.getMillis()));
        }
    }

    private void addResourceInformation() {
        log.debug("Adding resource metadata to capabilities");
        List<ResourceMetadata> resourceMetadata = storageEngine.getEventHandler().query("from ResourceMetadata", null);
        log.debug("Setting {} resource metadata(s)", resourceMetadata.size());
        Collections.sort(resourceMetadata, new ResourceMetadataComparator());
        capabilities.setResourceMetadata(resourceMetadata);
    }

    /**
     * @param excludeFieldNames the excludeFieldNames to set
     */
    public void setExcludeFieldNames(Set<String> excludeFieldNames) {
        this.excludeFieldNames = excludeFieldNames;
    }

}
