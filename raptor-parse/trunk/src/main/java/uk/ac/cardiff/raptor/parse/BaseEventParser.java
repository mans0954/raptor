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
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.net.URLDecoder;
import java.util.List;
import java.util.TimeZone;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import uk.ac.cardiff.model.event.Event;
import uk.ac.cardiff.raptor.parse.runtimeutils.ReflectionHelper;
import uk.ac.cardiff.raptor.store.IncrementalEventHandler;

/**
 * The raptor base event parser that all concrete event parsers should extend.
 * 
 */
public abstract class BaseEventParser {

    /** class logger. */
    private final Logger log = LoggerFactory.getLogger(BaseEventParser.class);

    /**
     * Whether this event parser is enabled and should be used. Defaults to true.
     */
    private boolean enabled = true;

    /** The fully qualified name of the event type class. */
    protected String eventType;

    /** A human readable name of the types of events this parser handles. */
    private String eventTypeFriendlyName;

    /** The handler that is responsible for storing events. */
    protected IncrementalEventHandler eventHandler;

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
     * Creates a new object from a class name. Could be removed to a helper class
     * 
     * @param className the fully qualified class name
     * @return the new object instantiated as the type <code>className</code>
     */
    protected final Object createObject(String className) {
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
     * Method to reset this event parser back to its initial state.This involves removing all entries from the
     * <code>IncrementalEntryHandler</code> and reseting all references to the last event(s) parsed.
     */
    public void reset() {
        eventHandler.reset();
    }

    /**
     * Decodes a URL (which must use the http(s) protocol) based on the ISO-8859-1 ISO-Latin character set. Keeps
     * decoding (max 5 times) until the literal 'http(s)://' is obtained, as some URL's are double encoded.
     * 
     * @param value the URL to be decoded. Must be a URL using the http protocol.
     * @return the <code>value</code> URL decoded
     */
    protected final String decode(String value) {
        try {
            int maxTries = 0;
            String decodedURL = value;
            while (true) {
                decodedURL = URLDecoder.decode(decodedURL, "ISO-8859-1");
                if (decodedURL.contains("http://") || decodedURL.contains("https://")) {
                    return decodedURL;
                }
                if (maxTries == 5) {
                    log.warn("Maximum tries have been hit to decode the URL [{}]", value);
                    break;
                }
                maxTries++;
            }

        } catch (UnsupportedEncodingException e) {
            return value;
        }
        return value;
    }

    /**
     * Converts the <code>value</code> into a list delimited by the <code> delimeter</code> input, and adds the list of
     * string values to the <code>fieldName</code> of the <code>object</code> using Reflection.
     * 
     * @param value the value to add to the <code>object</code>
     * @param fieldName the name of the field that the value is added to
     * @param delimeter the delimeter used to split the <code>value</code> into a list
     * @param object the object with which to set the <code>value</code> on
     */
    protected final void addStringList(String value, String fieldName, Object object, String delimeter) {
        try {
            String[] splitValue = value.split(delimeter);
            String fieldAsMethod = ReflectionHelper.prepareMethodNameSet(fieldName);
            setValueOnObject(fieldAsMethod, splitValue, object);
        } catch (Exception e) {
            log.error("Illegal StringList Value: {} for field: {}", new Object[] {value, fieldName, e});
        }
    }

    /**
     * Converts the <code>value</code> into a date using the given <code>format</code> and <code>timezone</code>
     * parameters, and adds the date to the <code>fieldName</code> of the <code>object</code> using Reflection.
     * 
     * @param value the value to add to the <code>object</code>
     * @param format the date format used to parse the <code>value</code> into a string
     * @param timezone sets the timezone on the newly created date
     * @param fieldName the name of the field that the value is added to
     * @param object the object with which to set the <code>value</code> on
     */
    protected final void addDate(String value, String format, String timezone, String fieldName, Object object) {
        try {
            DateTimeFormatter dtf = DateTimeFormat.forPattern(format);
            dtf = dtf.withZone(DateTimeZone.forTimeZone(TimeZone.getTimeZone(timezone)));
            DateTime dt = dtf.parseDateTime(value.substring(0, value.length()));
            String fieldAsMethod = ReflectionHelper.prepareMethodNameSet(fieldName);
            setValueOnObject(fieldAsMethod, dt, object);
        } catch (Exception e) {
            log.error("Illegal Date Value: {} for field: {}", new Object[] {value, fieldName, e});
        }

    }

    /**
     * Adds the string <code>value</code> to the <code>fieldName</code> of the <code>object</code> using Reflection.
     * 
     * @param value the value to add to the <code>object</code>
     * @param fieldName the name of the field that the value is added to
     * @param object the object with which to set the <code>value</code> on
     */
    protected final void addString(String value, String fieldName, Object object) {
        try {
            String fieldAsMethod = ReflectionHelper.prepareMethodNameSet(fieldName);
            setValueOnObject(fieldAsMethod, value, object);
        } catch (Exception e) {
            log.error("Illegal String Value: {} for field: {}", new Object[] {value, fieldName, e});
        }

    }

    /**
     * Converts the <code>value</code> into an Integer, and adds the Integer to the <code>fieldName</code> of the
     * <code>object</code> using Reflection.
     * 
     * @param value the value to add to the <code>object</code>
     * @param fieldName the name of the field that the value is added to
     * @param object the object with which to set the <code>value</code> on
     */
    protected final void addInteger(String value, String fieldName, Object object) {
        try {
            String fieldAsMethod = ReflectionHelper.prepareMethodNameSet(fieldName);
            int valueAsInt = Integer.parseInt(value);
            setValueOnObject(fieldAsMethod, valueAsInt, object);
        } catch (Exception e) {
            log.error("Illegal Integer Value: {} for field: {}", new Object[] {value, fieldName, e});
        }

    }

    /**
     * Sets the <code>param</code> object on the field <code>fieldname</code> of the object <code>object</code>
     * 
     * @param fieldname the name of the field from which to set the value <code>param</code>
     * @param param the object to set on the <code>fieldname</code> of the <code>object</code>
     * @param object the object with which to set the object <code>param</code>
     */
    protected final void setValueOnObject(String fieldname, Object param, Object object) {
        try {
            Class<? extends Object> id = object.getClass();
            Method setter = id.getMethod(fieldname, new Class[] {param.getClass()});
            setter.invoke(object, new Object[] {param});
        } catch (Throwable e) {
            log.error("Field name '{}' does not match internal model attribute for type", new Object[] {fieldname,
                    object.getClass()}, e);

        }
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
     * Gets all the entries currently stored by this entry handler
     * 
     * @return
     */
    public List<Event> getAuthentications() {
        return eventHandler.getEvents();

    }

}
