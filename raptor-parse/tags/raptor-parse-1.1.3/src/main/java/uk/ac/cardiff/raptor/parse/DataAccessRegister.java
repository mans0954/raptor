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

package uk.ac.cardiff.raptor.parse;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import uk.ac.cardiff.raptor.registry.Endpoint;
import uk.ac.cardiff.raptor.store.IncrementalEventHandler;

/**
 * @author philsmart
 * 
 *         Provides a register for parsing modules to attach to.
 */
public class DataAccessRegister {

    /** Class logger */
    private final Logger log = LoggerFactory.getLogger(DataAccessRegister.class);

    /** List of parsing modules */
    private List<BaseEventParser> parsingModules;

    /**
     * Default constructor that sets an empty list of parsing modules by default.
     */
    public DataAccessRegister() {
        setParsingModules(new ArrayList<BaseEventParser>());
    }

    /**
     * Sets and prints the name of all parsing modules passed as an input parameter
     * 
     * @param parsingModules
     */
    public void setParsingModules(List<BaseEventParser> parsingModules) {
        for (BaseEventParser parser : parsingModules)
            log.info("Registering parsing module {} for Event Type {}", parser.getClass(), parser.getEventType());
        this.parsingModules = parsingModules;
    }

    /**
     * Gets the list of parsing modules currently registered
     * 
     * @return list of parsing modules, all subclasses of the {@link BaseEventParser} class
     */
    public List<BaseEventParser> getParsingModules() {
        return parsingModules;
    }

    /**
     * Gets the list of parsing modules currently registered that are enabled.
     * 
     * @return list of parsing modules, all subclasses of the {@link BaseEventParser} class
     */
    public List<BaseEventParser> getEnabledParsingModules() {
        List<BaseEventParser> eventParsers = new ArrayList<BaseEventParser>();
        for (BaseEventParser eventParser : parsingModules) {
            if (eventParser.isEnabled()) {
                eventParsers.add(eventParser);
            }
        }
        return eventParsers;
    }

    /**
     * Returns the first parsing module whos <code>eventType</code> matches the <code>eventType</code> parameter
     * 
     * @param eventTypeFriendlyName the <code>friendlyName</code> of the event parser to find
     * @return returns the matching parsing module if found, or throws a <code>EventParserNotFoundException</code> if
     *         not found
     */
    public BaseEventParser getParsingModuleForType(String eventTypeFriendlyName) throws EventParserNotFoundException {
        if (eventTypeFriendlyName == null)
            throw new EventParserNotFoundException(
                    "No event type supplied to the data register, hence parsing module could not be found");
        for (BaseEventParser baseEventParser : parsingModules) {
            if (baseEventParser.getEventTypeFriendlyName().equals(eventTypeFriendlyName))
                return baseEventParser;
        }

        throw new EventParserNotFoundException("No parser could be found for the event type " + eventTypeFriendlyName);

    }

    /**
     * Removes events from each parsing module iff they have been released to all registered endpoints
     */
    public void garbageCollect(List<Endpoint> endpoints) {
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
        log.info("EGC. Garbage collection has found all events previous to {} can be removed", earliestReleaseTime);
        for (BaseEventParser parser : parsingModules) {
            if (parser.isEnabled()) {
                IncrementalEventHandler entryHandler = parser.getEventHandler();
                log.info("EGC. Parsing Module {} has {} events before garbage collection", parser,
                        entryHandler.getNumberOfEvents());
                entryHandler.removeEventsBefore(earliestReleaseTime, endpointWithEarliestReleaseTime
                        .getReleaseInformation().getLatestEqualEntries());
                log.info("EGC. Parsing Module {} has {} events after garbage collection", parser,
                        entryHandler.getNumberOfEvents());
            }

        }

    }
}
