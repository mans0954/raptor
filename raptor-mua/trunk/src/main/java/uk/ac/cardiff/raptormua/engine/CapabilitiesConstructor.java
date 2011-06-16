/**
 * 
 */
package uk.ac.cardiff.raptormua.engine;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import uk.ac.cardiff.model.ServiceMetadata;
import uk.ac.cardiff.model.resource.ResourceMetadata;
import uk.ac.cardiff.model.wsmodel.Capabilities;
import uk.ac.cardiff.model.wsmodel.ProcessorInformation;
import uk.ac.cardiff.model.wsmodel.StatisticalUnitInformation;
import uk.ac.cardiff.model.wsmodel.Suggestion;
import uk.ac.cardiff.model.wsmodel.SuggestionValues;
import uk.ac.cardiff.raptor.runtimeutils.ReflectionHelper;
import uk.ac.cardiff.raptor.store.StorageEngine;
import uk.ac.cardiff.raptormua.engine.statistics.Statistic;
import uk.ac.cardiff.raptormua.engine.statistics.StatisticsHandler;
import uk.ac.cardiff.raptormua.engine.statistics.StatisticsPostProcessor;
import uk.ac.cardiff.raptormua.runtimeutils.ResourceMetadataComparator;

/**
 * @author philsmart
 * 
 */
public class CapabilitiesConstructor implements ApplicationContextAware{

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

    /** Springs application context*/
	private ApplicationContext applicationContext;

    /**
     * @return
     */
    public Capabilities constructCapabilities(StatisticsHandler statisticsHandler, StorageEngine storageEngine, ServiceMetadata metadata) {
        log.info("Capabilities Constructor Called");

        Capabilities capabilities = null;

        checkCacheValidity();

        if (cacheEnabled) {
            log.info("Capabilities retrieved from cache, cache will timeout in {} milliseconds",cacheTimeoutMs-(System.currentTimeMillis()-cacheResetTimeMs));
            capabilities = cachedCapabilities;
        }

        if (capabilities == null) {

            capabilities = new Capabilities();

            List<Statistic> su = statisticsHandler.getStatisticalUnits();

            capabilities.setMetadata(metadata);

            // set possible field names
            SuggestionValues suggestionValues = new SuggestionValues();
            suggestionValues.setPossibleFieldNameValues(ReflectionHelper.getFieldsFromEntrySubClasses());
            capabilities.setSuggestionValues(suggestionValues);
            capabilities.setNumberOfAuthenticationsStored(storageEngine.getEntryHandler().getNumberOfEntries());
            List<Suggestion> possiblePostProcessors = findPostProcessors();
            log.debug("Has set {} possible post processor suggestion values",possiblePostProcessors.size());
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

            suggestionValues.setPossibleFieldValues(storageEngine.getPossibleValuesFor(possibleFieldNames));

            // set resource metadata
            List<ResourceMetadata> resourceMetadata = (List<ResourceMetadata>) storageEngine.getEntryHandler().query("from ResourceMetadata");
            log.debug("Setting {} resource metadata", resourceMetadata.size());
            Collections.sort(resourceMetadata,new ResourceMetadataComparator());
            capabilities.setResourceMetadata(resourceMetadata);

            ArrayList<StatisticalUnitInformation> stats = new ArrayList();
            for (Statistic entry : su) {
                log.debug("Setting statistical unit information as: " + entry.getStatisticParameters().getUnitName());
                StatisticalUnitInformation information = new StatisticalUnitInformation();

                information.setStatisticParameters(entry.getStatisticParameters());

                /* the below should be changed */
                ArrayList<ProcessorInformation> postprocessors = new ArrayList<ProcessorInformation>();
                if (entry.getPostprocessor() != null) {
                    for (StatisticsPostProcessor postprocessor : entry.getPostprocessor()) {
                    	ProcessorInformation processorInformation = new ProcessorInformation();
                    	processorInformation.setBeanName(postprocessor.getClass().getSimpleName());
                        postprocessors.add(processorInformation);
                    }
                }
                information.setPostprocessors(postprocessors);
                stats.add(information);
            }
            capabilities.setStatisticalServices(stats);
            
            if (cacheEnabled) {
                cachedCapabilities = capabilities;
                cacheResetTimeMs = System.currentTimeMillis();
            }
        }
        
        log.info("Constructed MUA Capabilities for [{}]", capabilities.getMetadata().getEntityId());

        return capabilities;
    }
    
    private List<Suggestion> findPostProcessors(){
    	String[] postProcessors = applicationContext.getBeanNamesForType(StatisticsPostProcessor.class);
    	ArrayList<Suggestion> postProcessorSuggestions = new ArrayList<Suggestion>();
    	for (String processor : postProcessors){
    		Suggestion suggestion = new Suggestion();
    		suggestion.setBase("postprocessor");
    		suggestion.setValue(processor);
    		postProcessorSuggestions.add(suggestion);
    		log.trace("Postprocessor, {}",processor);
    	}
    	return postProcessorSuggestions;
    	
    }

    private void checkCacheValidity() {
        if (cacheTimeoutMs == 0 || !cacheEnabled) {
            return;
        }
        long currentTimeMillis = System.currentTimeMillis();
        boolean shouldReset = (currentTimeMillis - cacheResetTimeMs) > cacheTimeoutMs;
        if (shouldReset) {
            log.info("Ldap cache was cleared, timeout reached");
            cachedCapabilities = null;
        }
    }

    /**
     * Sets whether the cache is enabled, and sets a default timeout of 30 minutes
     * in case one is not specified in the XML
     * 
     * @param cacheEnabled
     *            the cacheEnabled to set
     */
    public void setCacheEnabled(boolean cacheEnabled) {
        this.cacheEnabled = cacheEnabled;
        cacheTimeoutMs=1800000;
    }

    /**
     * @return the cacheEnabled
     */
    public boolean isCacheEnabled() {
        return cacheEnabled;
    }

    /**
     * @param cacheTimeoutMs
     *            the cacheTimeoutMs to set
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
     * @param excludeFieldNames
     *            the excludeFieldNames to set
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
