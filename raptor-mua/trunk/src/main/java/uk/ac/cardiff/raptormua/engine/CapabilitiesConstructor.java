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

import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import uk.ac.cardiff.model.ServiceMetadata;
import uk.ac.cardiff.model.wsmodel.Capabilities;
import uk.ac.cardiff.raptor.store.EventStorageEngine;
import uk.ac.cardiff.raptormua.engine.statistics.StatisticHandler;

/**
 * @author philsmart
 * 
 */
public class CapabilitiesConstructor extends BaseCapabilitiesContructor implements CallbackInterface<Capabilities> {

    /** Class logger. */
    private final Logger log = LoggerFactory.getLogger(CapabilitiesConstructor.class);

    /** Whether to cache capabilities. Not used, always true. */
    private boolean cacheEnabled;

    /** The capabilites currently constructed. Will be reconstructed on cache timeout or cache invalidation. */
    private Capabilities cachedCapabilities;

    /** How long the cache is valid for in milliseconds */
    private long cacheTimeoutMs;

    /** The time at which the cache was last reset */
    private long cacheResetTimeMs;

    /** Timer that checks if the cache has expired */
    private Timer cacheTimer;

    /** Set containing the names of fields that should not be included in the list of possible field values */
    private Set<String> excludeFieldNames;

    /** The single thread pool responsible for queing reconstruction of the <code>cached</code> capabilities. */
    private final ExecutorService capabilitiesConstructionService;

    /** The statiscs handler used to extract statistical units to perform. */
    private StatisticHandler statisticsHandler;

    /** The storage engine used to extract information about events stored. */
    private EventStorageEngine storageEngine;

    /** The service metadata used to embedded in the capabilities. */
    private ServiceMetadata metadata;

    /**
     * Default constructor. Does the following:
     * <ol>
     * <li>Creates a new single thread executor service.</li>
     * <li>Sets the default timeout to 1800000ms.</li>
     * <li>Starts a timer task which checks the cache every minute.</li>
     * </ol>
     */
    public CapabilitiesConstructor() {
        capabilitiesConstructionService = Executors.newSingleThreadExecutor();
        cacheTimeoutMs = 1800000;
        cacheTimer = new Timer();
        cacheTimer.schedule(new TimerTask() {
            public void run() {
                log.trace("Cache will timeout in {} milliseconds", cacheTimeoutMs
                        - (System.currentTimeMillis() - cacheResetTimeMs));
                if ((System.currentTimeMillis() - cacheResetTimeMs) > cacheTimeoutMs) {
                    log.info("Capabilities cache was cleared, timeout reached");
                    queueCapabilitiesConstruction();
                }
            }
        }, 60000, 60000);
    }

    /**
     * The result of executing a {@link CapabilitiesConstructorTask} through the
     * <code>capabilitiesConstructionService</code> single threaded executor is returned here once complete.
     * 
     */
    public void returnResult(Capabilities newCapabilities) {
        log.info("Capabilities have been reconstructed {}", newCapabilities);

        cachedCapabilities = newCapabilities;
        cacheResetTimeMs = System.currentTimeMillis();

    }

    /**
     * Queues to create the initial <code>cachedCapabilities</code>.
     */
    public void initialiseCapabilities() {
        queueCapabilitiesConstruction();
    }

    private void queueCapabilitiesConstruction() {
        log.info("Capabilities constructor called, adding request to thread queue");
        if (statisticsHandler != null && storageEngine != null && metadata != null) {
            CapabilitiesConstructorTask constructor =
                    new CapabilitiesConstructorTask(this, statisticsHandler, storageEngine, metadata);
            constructor.setExcludeFieldNames(excludeFieldNames);
            capabilitiesConstructionService.submit(constructor);
        } else {
            log.error("No capabilites constructed, one-of statisticsHandler, storageEngine or metadata not set");
        }
    }

    /**
     * Constructors the capabilities of this MUA instance, by computing and storing the following:
     * <ul>
     * <li>Stores the metadata assoicated to this MUA.
     * </ul>
     * 
     * @return the {@link Capabilities} of this MUA.
     */
    public Capabilities getCapabilities() {

        log.info("Capabilities retrieved from cache, cache will timeout in {} milliseconds",
                cacheTimeoutMs - (System.currentTimeMillis() - cacheResetTimeMs));
        return cachedCapabilities;

    }

    /**
     * If called, cache timeout is reset, and a call to construct new capabilities is placed on the queue.
     */
    public void invalidateCache() {
        queueCapabilitiesConstruction();
        cacheResetTimeMs = System.currentTimeMillis();
    }

    /**
     * Deprecated, has no effect, only here not to break XML config, capabilities are always cached.
     * 
     * @param cacheEnabled if the cache should be used - ignored.
     */
    @Deprecated
    public void setCacheEnabled(boolean cacheEnabled) {
        this.cacheEnabled = cacheEnabled;

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

    /**
     * @return the statisticsHandler
     */
    public StatisticHandler getStatisticsHandler() {
        return statisticsHandler;
    }

    /**
     * @return the storageEngine
     */
    public EventStorageEngine getStorageEngine() {
        return storageEngine;
    }

    /**
     * @return the metadata
     */
    public ServiceMetadata getMetadata() {
        return metadata;
    }

    /**
     * @param statisticsHandler the statisticsHandler to set
     */
    public void setStatisticsHandler(StatisticHandler statisticsHandler) {
        this.statisticsHandler = statisticsHandler;
    }

    /**
     * @param storageEngine the storageEngine to set
     */
    public void setStorageEngine(EventStorageEngine storageEngine) {
        this.storageEngine = storageEngine;
    }

    /**
     * @param metadata the metadata to set
     */
    public void setMetadata(ServiceMetadata metadata) {
        this.metadata = metadata;
    }

}
