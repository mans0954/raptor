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

import java.io.File;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import uk.ac.cardiff.model.event.Event;
import uk.ac.cardiff.raptor.parse.filter.ExclusionList;
import uk.ac.cardiff.raptor.parse.filter.InclusionList;
import uk.ac.cardiff.raptor.store.IncrementalEventHandler;

/**
 * This is really not a base event parser, its specific to log file event parsing.
 * 
 */
public abstract class BaseEventParser {

    /** class logger. */
    private final Logger log = LoggerFactory.getLogger(BaseEventParser.class);

    /** The handler that is responsible for storing events. */
    protected IncrementalEventHandler eventHandler;

    /** A list of <fieldname,value> pairs that should be excluded during parsing. */
    private ExclusionList exclusionList;

    /**
     * A list of <fieldname, value> pairs that should be included during parsing
     */
    private InclusionList inclusionList;

    /** The fully qualified name of the event type class. */
    protected String eventType;

    /** A human readable name of the types of events this parser handles. */
    private String eventTypeFriendlyName;

    /**
     * Whether this event parser is enabled and should be used. Defaults to true.
     */
    private boolean enabled = true;

    /**
     * Default constructor
     */
    public BaseEventParser() {
    }

    /**
     * The method that must be provided to parse events from the <code>bytes</code> parameter
     * 
     * @throws ParserException the logfile to parse as bytes
     */
    public abstract void parse(byte[] bytes) throws ParserException;

    /**
     * The method that must be provided to parse events from the internally configured log file location e.g.
     * <code>logfile</code>
     * 
     * @throws ParserException
     */
    public abstract void parse() throws ParserException;

    /**
     * The method that must be provided to parse events from the input log file location e.g. <code>logfile</code>
     * 
     * @param logFile the logfile to parse
     * @throws ParserException
     */
    public abstract void parse(File logFile) throws ParserException;

    /**
     * Method to reset this event parser back to its initial state.This involves removing all entries from the
     * <code>IncrementalEntryHandler</code> and reseting all references to the last event(s) parsed.
     */
    public void reset() {
        eventHandler.reset();
    }

    /**
     * Creates a new object from a class name. Could be removed to a helper class
     * 
     * @param className the fully qualified class name
     * @return the new object instantiated as the type <code>className</code>
     */
    public Object createObject(String className) {
        Object object = null;
        try {
            Class<?> classDefinition = Class.forName(className);
            object = classDefinition.newInstance();
        } catch (InstantiationException e) {
            log.warn("Major issue, could not instantied the event class,", e);
        } catch (IllegalAccessException e) {
            log.warn("Major issue, could not instantied the event class,", e);
        } catch (ClassNotFoundException e) {
            log.warn("Major issue, could not instantied the event class,", e);
        }
        return object;
    }

    /**
     * Method to remove all entries from the event handler
     */
    public void removeAllEntries() {
        eventHandler.removeAllEvents();

    }

    /**
     * Sets and initialises the entry handler
     * 
     * @param entryHandler
     */
    public void setEventHandler(IncrementalEventHandler eventHandler) {
        this.eventHandler = eventHandler;
        eventHandler.initialise();
    }

    /**
     * Gets the entry handler
     * 
     * @return
     */
    public IncrementalEventHandler getEventHandler() {
        return eventHandler;
    }

    /**
     * Sets the exclusion list
     * 
     * @param exclusionList
     */
    public void setExclusionList(ExclusionList exclusionList) {
        this.exclusionList = exclusionList;
    }

    /**
     * Gets the exclusion list
     * 
     * @return
     */
    public ExclusionList getExclusionList() {
        return exclusionList;
    }

    /**
     * Sets the inclusion list
     * 
     * @param inclusionList
     */
    public void setInclusionList(InclusionList inclusionList) {
        this.inclusionList = inclusionList;
    }

    /**
     * Gets the inclusion list
     * 
     * @return
     */
    public InclusionList getInclusionList() {
        return inclusionList;
    }

    /**
     * Gets all the entries currently stored by this entry handler
     * 
     * @return
     */
    public List<Event> getAuthentications() {
        return eventHandler.getEvents();

    }

    /**
     * @param eventType the eventType to set
     */
    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    /**
     * @return the eventType
     */
    public String getEventType() {
        return eventType;
    }

    /**
     * @param eventTypeFriendlyName the eventTypeFriendlyName to set
     */
    public void setEventTypeFriendlyName(String eventTypeFriendlyName) {
        this.eventTypeFriendlyName = eventTypeFriendlyName;
    }

    /**
     * @return the eventTypeFriendlyName
     */
    public String getEventTypeFriendlyName() {
        return eventTypeFriendlyName;
    }

    /**
     * @param enabled the enabled to set
     */
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    /**
     * @return the enabled
     */
    public boolean isEnabled() {
        return enabled;
    }

}
