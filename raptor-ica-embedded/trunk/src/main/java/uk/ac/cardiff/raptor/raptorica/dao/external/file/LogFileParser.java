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
package uk.ac.cardiff.raptor.raptorica.dao.external.file;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.TimeZone;
import java.io.BufferedInputStream;

import uk.ac.cardiff.raptor.raptorica.dao.BaseEventParser;
import uk.ac.cardiff.raptor.raptorica.dao.external.format.Header;
import uk.ac.cardiff.raptor.raptorica.dao.external.format.LogFileFormat;
import uk.ac.cardiff.raptor.raptorica.model.exception.HeaderException;
import uk.ac.cardiff.raptor.raptorica.model.exception.ParserException;
import uk.ac.cardiff.raptor.raptorica.model.filter.ExclusionEntry;
import uk.ac.cardiff.raptor.raptorica.model.filter.InclusionEntry;

import org.apache.commons.lang.text.StrTokenizer;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import uk.ac.cardiff.raptor.raptorica.runtimeutils.ReflectionHelper;
import uk.ac.cardiff.model.event.Event;

/**
 * A log file parser, used to parse files on the local filesystem according to a given
 * format and transformed into the given event type.
 *
 * @author phil
 *
 */
public class LogFileParser extends BaseEventParser {

    /** The class logger*/
    private final Logger log = LoggerFactory.getLogger(LogFileParser.class);

    /** The specification of the format of this log file */
    private LogFileFormat format;

    /** The fully qualified path of the log file */
    private String logfile;

    /** The fully qualified name of the event type class */
    private String eventType;

    /** Whether to print out the current position of parsing in the <code>logFile</code> */
    private boolean printParsingPosition;

    /**
     * Default constructor
     */
    public LogFileParser() {
	super();
    }

    @Override
    public void parse() throws ParserException {
	log.info("parsing: {} instance: {}", logfile, this);

	try {
	    URL logfileURL = new URL(logfile);

	    URLConnection logfileconnection = logfileURL.openConnection();

	    BufferedReader in = new BufferedReader(new InputStreamReader(logfileconnection.getInputStream()));
	    int lineCount = 0;
	    String inputLine;
	    int totalNoLines = count(logfileURL);

	    log.debug("Parsing file with {} lines", totalNoLines);
	    while ((inputLine = in.readLine()) != null) {
		lineCount++;

		if (printParsingPosition) {
		    printParsingPosition(lineCount, totalNoLines);
		}

		// TODO TOKENIZE METHOD, then regex
		StrTokenizer tokenizer = new StrTokenizer(inputLine, format.getDelimeter());
		tokenizer.setIgnoreEmptyTokens(false);
		ArrayList<String> allvalues = new ArrayList<String>();
		while (tokenizer.hasNext()) {
		    Object next = tokenizer.next();
		    if (next instanceof String)
			allvalues.add((String) next);
		    else {
			log.error("input column was not a string");
		    }
		}

		Event authE = (Event) this.createObject(eventType);
		try {
		    populateField(allvalues, authE);
		} catch (HeaderException e) {
		    log.error("ERROR: trying to access field {}, when only {} fields in log file", e.getHeaderNo(), allvalues.size());
		}

		boolean shouldBeIncluded = isIncluded(authE);
		boolean preventAdd = isExcluded(authE);

		if (shouldBeIncluded && !preventAdd) {
		    entryHandler.addEntry(authE);
		}

	    }
	    in.close();

	    log.debug("done, walked {} lines", lineCount);
	    log.debug("LogFileParser currently has: {} entries, latestEntry: {}", getEntryHandler().getEntries().size(), getEntryHandler().getLatestEntryTime());
	    entryHandler.endTransaction();
	} catch (MalformedURLException e1) {
	    throw new ParserException("Could not find the source file ["+logfile+"] for parsing",e1);
	} catch (IOException e2) {
	    throw new ParserException("Could not read from the source file ["+logfile+"] during parsing",e2);
	}

	// System.exit(1);

    }

    /**
     * Prints, as a percentage, the line currently being parsed.
     *
     * @param lineCount
     * @param totalNoLines
     */
    private void printParsingPosition(int lineCount, int totalNoLines) {
	double linePercentage = (((double) lineCount / (double) totalNoLines) * 100);
	if (linePercentage % 25 >= 0 && linePercentage % 25 <= 0.0001)
	    log.debug("Complete {}%", linePercentage);
    }

    /**
     * Populates attributes in the Event class <code>authE</code> (or subclasses thereof) from the headers defined in <code>format</code>
     *
     * @param allvalues
     * @param authE
     * @throws HeaderException
     */
    private void populateField(List<String> allvalues, Event authE) throws HeaderException {
	for (Header header : format.getHeaders()) {
	    try {
		if (!(header.getFieldNo() >= allvalues.size())) {
		    String value = allvalues.get(header.getFieldNo());
		    switch (header.getType()) {
			case DATE:
			    addDate(value, header.getDateTimeFormat(), header.getTimeZone(), header.getFieldName(), authE);
			    break;
			case STRING:
			    addString(value, header.getFieldName(), authE);
			    break;
			case INTEGER:
			    addInteger(value, header.getFieldName(), authE);
			    break;
			case STRINGLIST:
			    addStringList(value, header.getFieldName(), authE, header.getListDelimeter());
			    break;
		    }

		} else {
		    StringBuilder exceptionMessage = new StringBuilder();
		    exceptionMessage.append("failed trying to access field ");
		    exceptionMessage.append(header.getFieldNo());
		    throw new HeaderException(exceptionMessage.toString(), header.getFieldNo());
		}
	    } catch (ClassCastException e) {
		throw new HeaderException("Header key is not an integer, needs to be an integer (error could exist in data-access.xml)", header.getFieldNo(), e);

	    }
	}
    }

    /**
     * Determines if the event should not be stored, as determined by a <fieldname, value> match on the input <code>authE</code> event with those in the
     * <code>exclusionList</code>.
     *
     * @param authE
     * @return true if any <fieldname,value> pairs in the <code>autheE</code> event are found in the exclusion list, false otherwise
     */
    private boolean isExcluded(Event authE) {
	if (getExclusionList() != null) {
	    for (ExclusionEntry exclusion : getExclusionList().getExclusionEntries()) {
		String fieldname = exclusion.getFieldName();
		Object valueFromEntry = getValueFromObject(fieldname, authE);
		if (valueFromEntry instanceof String) {
		    String valueFromEntryString = (String) valueFromEntry;
		    if (exclusion.filter(valueFromEntryString)) {
			return true;
		    }
		}
	    }
	}
	return false;
    }

    /**
     * Determines if the event should stored, as determined by a <fieldname, value> match on the input <code>authE</code> event with those in the
     * <code>exclusionList</code>.
     *
     * @param authE
     * @return true if any <fieldname,value> pairs in the <code>autheE</code> event are found in the inclusion list, false otherwise
     */
    private boolean isIncluded(Event authE) {
	if (getInclusionList() != null) {
	    for (InclusionEntry inclusion : getInclusionList().getInclusionEntries()) {
		String fieldname = inclusion.getFieldName();
		Object valueFromEntry = getValueFromObject(fieldname, authE);
		if (valueFromEntry instanceof String) {
		    String valueFromEntryString = (String) valueFromEntry;
		    if (inclusion.filter(valueFromEntryString)) {
			return true;
		    }
		}
	    }
	} else {
	    return true;
	}

	return false;
    }

    /**
     * Efficient method for finding the number of lines in a logfile
     *
     * @param logfileURL
     * @return
     * @throws IOException
     */
    private int count(URL logfileURL) throws IOException {

	URLConnection logfileconnection = logfileURL.openConnection();
	InputStream is = new BufferedInputStream(logfileconnection.getInputStream());
	byte[] c = new byte[1024];
	int count = 0;
	int readChars = 0;
	while ((readChars = is.read(c)) != -1) {
	    for (int i = 0; i < readChars; ++i) {
		if (c[i] == '\n')
		    ++count;
	    }
	}
	return count;
    }

    /**
     * @param value
     * @param fieldName
     * @param authE
     */
    private void addStringList(String value, String fieldName, Object object, String delimeter) {
	try {
	    String[] splitValue = value.split(delimeter);
	    String fieldAsMethod = ReflectionHelper.prepareMethodNameSet(fieldName);
	    setValueOnObject(fieldAsMethod, splitValue, object);
	} catch (Exception e) {
	    log.error("Illegal StringList Value: {} for field: {}", new Object[] { value, fieldName, e });
	}
    }

    private void addDate(String value, String format, String timezone, String fieldName, Object object) {
	try {
	    /*
	     * first check the end of the date, if it has a Z it should have time zone information e.g. -0800 or +0500 but if it ends in just Z, there is not
	     * valid timezone information supplied, so remove
	     */
	    // TODO check this
	    if (value.endsWith("Z"))
		value = value.substring(0, value.length() - 1);

	    DateTimeFormatter dtf = DateTimeFormat.forPattern(format);
	    dtf = dtf.withZone(DateTimeZone.forTimeZone(TimeZone.getTimeZone(timezone)));
	    DateTime dt = dtf.parseDateTime(value.substring(0, value.length()));
	    String fieldAsMethod = ReflectionHelper.prepareMethodNameSet(fieldName);
	    setValueOnObject(fieldAsMethod, dt, object);
	} catch (Exception e) {
	    log.error("Illegal Date Value: {} for field: {}", new Object[] { value, fieldName, e });
	}

    }

    private void addString(String value, String fieldName, Object object) {
	try {
	    String fieldAsMethod = ReflectionHelper.prepareMethodNameSet(fieldName);
	    setValueOnObject(fieldAsMethod, value, object);
	} catch (Exception e) {
	    log.error("Illegal String Value: {} for field: {}", new Object[] { value, fieldName, e });
	}

    }

    private void addInteger(String value, String fieldName, Object object) {
	try {
	    String fieldAsMethod = ReflectionHelper.prepareMethodNameSet(fieldName);
	    setValueOnObject(fieldAsMethod, value, object);
	} catch (Exception e) {
	    log.error("Illegal Integer Value: {} for field: {}", new Object[] { value, fieldName, e });
	}

    }

    private Object getValueFromObject(String fieldname, Object object) {
	try {
	    Class<? extends Object> id = object.getClass();
	    String fieldAsMethod = ReflectionHelper.prepareMethodNameGet(fieldname);
	    Method getter = id.getMethod(fieldAsMethod, new Class[] {});
	    Object result = getter.invoke(object, new Object[] {});
	    return result;
	} catch (Throwable e) {
	    log.error("Field name '{}' does not match internal model attribute", fieldname, e);
	}
	return null;
    }

    private void setValueOnObject(String fieldname, Object param, Object object) {
	try {
	    Class<? extends Object> id = object.getClass();
	    Method setter = id.getMethod(fieldname, new Class[] { param.getClass() });
	    setter.invoke(object, new Object[] { param });
	} catch (Throwable e) {
	    log.error("Field name '{}' does not match internal model attribute", fieldname, e);

	}
    }

    public void setLogfile(String logfile) {
	this.logfile = logfile;
    }

    public String getLogfile() {
	return logfile;
    }

    public void setFormat(LogFileFormat format) {
	this.format = format;
    }

    public LogFileFormat getFormat() {
	return format;
    }

    /**
     * @param printParsingPosition
     *            the printParsingPosition to set
     */
    public void setPrintParsingPosition(boolean printParsingPosition) {
	this.printParsingPosition = printParsingPosition;
    }

    /**
     * @return the printParsingPosition
     */
    public boolean isPrintParsingPosition() {
	return printParsingPosition;
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

}
