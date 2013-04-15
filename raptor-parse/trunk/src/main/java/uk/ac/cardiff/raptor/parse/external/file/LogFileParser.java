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

package uk.ac.cardiff.raptor.parse.external.file;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.text.StrTokenizer;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import uk.ac.cardiff.model.event.Event;
import uk.ac.cardiff.raptor.parse.BaseEventFileParser;
import uk.ac.cardiff.raptor.parse.ParserException;
import uk.ac.cardiff.raptor.parse.external.file.format.Header;
import uk.ac.cardiff.raptor.parse.external.file.format.HeaderException;
import uk.ac.cardiff.raptor.parse.external.file.format.LogFileFormat;
import uk.ac.cardiff.raptor.parse.filter.AbstractExclusionEntry;
import uk.ac.cardiff.raptor.parse.filter.AbstractInclusionEntry;
import uk.ac.cardiff.raptor.parse.filter.LineFilterEngine;
import uk.ac.cardiff.raptor.parse.runtimeutils.ReflectionHelper;

/**
 * A log file parser, used to parse files on the local filesystem according to a given format and transformed into the
 * given event type.
 * 
 * 
 */
public class LogFileParser extends BaseEventFileParser {

    /** The class logger */
    private final Logger log = LoggerFactory.getLogger(LogFileParser.class);

    /** The specification of the format of this log file */
    private LogFileFormat format;

    /**
     * The fully qualified path of the log file. Not required if <code>parse(bytes[])</code> is used
     */
    private String logfile;

    /**
     * Whether to print out the current position of parsing in the <code>logFile</code>
     */
    private boolean printParsingPosition;

    /** The filter engine that evaluates lineFilters to test whether a line from the raw log file should be parsed. */
    private LineFilterEngine lineFilterEngine;

    /**
     * Default constructor
     */
    public LogFileParser() {
        super();
    }

    @Override
    public void parse(byte[] bytes) throws ParserException {
        try {
            log.info("Parsing log file as byte array with length {}", bytes.length);
            BufferedReader bf = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(bytes)));
            int totalNoLines = count(bytes);
            doParse(bf, totalNoLines);
        } catch (MalformedURLException e1) {
            throw new ParserException("Could not find the source file [" + logfile + "] for parsing", e1);
        } catch (IOException e2) {
            throw new ParserException("Could not read from the source file [" + logfile + "] during parsing", e2);
        }

    }

    @Override
    public void parse() throws ParserException {
        String usedLogFile = null;
        try {
            DateTime todaysDate = new DateTime();
            usedLogFile = logfile.replace("DATE", todaysDate.toString("yyyyMMdd"));
            log.info("parsing log file: {}", usedLogFile);
            URL logfileURL = new URL(usedLogFile);
            URLConnection logfileconnection = logfileURL.openConnection();
            BufferedReader in = new BufferedReader(new InputStreamReader(logfileconnection.getInputStream()));
            int totalNoLines = count(logfileURL);
            doParse(in, totalNoLines);
        } catch (MalformedURLException e1) {
            throw new ParserException("Could not find the source file [" + usedLogFile + "] for parsing", e1);
        } catch (IOException e2) {
            throw new ParserException("Could not read from the source file [" + usedLogFile + "] during parsing", e2);
        }

    }

    @Override
    public void parse(File logfile) throws ParserException {
        try {
            log.info("parsing log file: {}", logfile.getCanonicalPath());
            URL logfileURL = logfile.toURI().toURL();
            log.debug("URL {}", logfileURL);
            URLConnection logfileconnection = logfileURL.openConnection();
            BufferedReader in = new BufferedReader(new InputStreamReader(logfileconnection.getInputStream()));
            int totalNoLines = count(logfileURL);
            doParse(in, totalNoLines);
        } catch (MalformedURLException e1) {
            throw new ParserException("Could not find the source file [" + logfile + "] for parsing", e1);
        } catch (IOException e2) {
            throw new ParserException("Could not read from the source file [" + logfile + "] during parsing", e2);
        }

    }

    private void doParse(BufferedReader in, int totalNoLines) throws ParserException {
        try {
            int lineCount = 0;
            String inputLine;

            log.debug("Parsing file with {} lines", totalNoLines);
            while ((inputLine = in.readLine()) != null) {
                lineCount++;

                if (printParsingPosition) {
                    printParsingPosition(lineCount, totalNoLines);
                }
                boolean parseLine = true;
                if (lineFilterEngine != null) {
                    parseLine = lineFilterEngine.isParsableLine(inputLine);
                }
                log.trace("Parse [{}] - {}", parseLine, inputLine);
                if (parseLine == true) {
                    StrTokenizer tokenizer = new StrTokenizer(inputLine, format.getDelimeter());
                    tokenizer.setIgnoreEmptyTokens(false);
                    List<String> allvalues = new ArrayList<String>();
                    while (tokenizer.hasNext()) {
                        Object next = tokenizer.next();
                        if (next instanceof String)
                            allvalues.add((String) next);
                        else {
                            log.error("input column was not a string, this should not happen");
                        }
                    }
                    Event authE = (Event) this.createObject(eventType);
                    try {
                        populateField(allvalues, authE);
                    } catch (HeaderException e) {
                        log.error("ERROR: trying to access field {}, when only {} fields in log file", e.getHeaderNo(),
                                allvalues.size());
                    }

                    boolean shouldBeIncluded = isIncluded(authE);
                    boolean preventAdd = isExcluded(authE);

                    log.trace("Included {}, Veoted {}, Event: {}",
                            new Object[] {shouldBeIncluded, preventAdd, authE.toString()});

                    if (shouldBeIncluded && !preventAdd) {
                        boolean added = eventHandler.addEvent(authE);
                        log.trace("Added event to event handler {}", added);
                        // System.exit(1);
                    }

                }

            }
            in.close();

            log.debug("done, walked {} lines", lineCount);
            log.debug("LogFileParser currently has: {} entries, latestEntry: {}", eventHandler.getNumberOfEvents(),
                    eventHandler.getLatestEventTime());
        } catch (IOException e2) {
            throw new ParserException("Could not read from the source file [" + logfile + "] during parsing", e2);
        }

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
     * Populates attributes in the Event class <code>authE</code> (or subclasses thereof) from the headers defined in
     * <code>format</code>
     * 
     * @param allvalues values for each field in a single record
     * @param authE the Event to populate
     * @throws HeaderException if trying to set a value on a field that does not exist in the list
     *             <code>allvalues</code>
     */
    private void populateField(List<String> allvalues, Event authE) throws HeaderException {
        for (Header header : format.getHeaders()) {
            try {
                // catch invalid field header formats.
                if (!(header.getFieldNo() >= allvalues.size())) {
                    String value = getFieldValue(allvalues, header);
                    value = retain(value, header);
                    value = replace(value, header);
                    switch (header.getType()) {
                        case DATE:
                            addDate(value, header.getDateTimeFormat(), header.getTimeZone(), header.getFieldName(),
                                    authE);
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
                        case URL:
                            value = decode(value);
                            addString(value, header.getFieldName(), authE);
                            break;
                    }

                } else {
                    StringBuilder exceptionMessage = new StringBuilder();
                    exceptionMessage.append("failed trying to access field ");
                    exceptionMessage.append(header.getFieldNo());
                    throw new HeaderException(exceptionMessage.toString(), header.getFieldNo());
                }
            } catch (ClassCastException e) {
                throw new HeaderException(
                        "Header key is not an integer, needs to be an integer (error could exist in data-access.xml)",
                        header.getFieldNo(), e);

            }
        }
    }

    private String getFieldValue(List<String> allvalues, Header header) {
        StringBuilder output = new StringBuilder();
        if (header.getAdditionalFieldNos() == null) {
            append(output, allvalues.get(header.getFieldNo()), false);
        } else if (header.getAdditionalFieldNos() != null) {
            append(output, allvalues.get(header.getFieldNo()), header.isPreserveDelimeterOnFieldConcatenation());
        }

        if (header.getAdditionalFieldNos() != null) {
            if (header.getAdditionalFieldNos().length == 1 && header.getAdditionalFieldNos()[0] == -1) {
                // grab all remaining fields from FieldNo.
                for (int i = header.getFieldNo() + 1; i < allvalues.size(); i++) {
                    if (i == allvalues.size() - 1) {
                        append(output, allvalues.get(i), false);
                    } else {
                        append(output, allvalues.get(i), header.isPreserveDelimeterOnFieldConcatenation());
                    }
                }
            } else {
                for (int i = 0; i < header.getAdditionalFieldNos().length; i++) {
                    int fieldNo = header.getAdditionalFieldNos()[i];
                    if (i == header.getAdditionalFieldNos().length - 1) {
                        append(output, allvalues.get(fieldNo), false);
                    } else {
                        append(output, allvalues.get(fieldNo), header.isPreserveDelimeterOnFieldConcatenation());
                    }
                }
            }
        }
        return output.toString();
    }

    private void append(StringBuilder output, String value, boolean preserveDelimeterOnFieldConcatenation) {
        output.append(value);
        if (preserveDelimeterOnFieldConcatenation) {
            output.append(format.getDelimeter());
        }
    }

    /**
     * Replace substrings from <code>value</code> with the given strings in <code>header</code> that match the regular
     * expression(s) in the regexReplaceAll from the <code>header</code>
     * 
     * @param value the value with which to replace certain substrings
     * @param header the header that holds the matching regular expressions and their replacements
     * @return the <code>value</code> from the input after substring replacement
     */
    private String replace(String value, Header header) {
        if (header.getRegexReplaceAll() == null)
            return value;

        Set<String> keys = header.getRegexReplaceAll().keySet();
        for (String replaceRegex : keys) {
            String with = header.getRegexReplaceAll().get(replaceRegex);
            value = value.replaceAll(replaceRegex.trim(), with);
        }
        return value;
    }

    /**
     * Returns only the substring from <code>value</code> that matches the given <code>regexRetain<code>
     * variable in the <code>header</code> input. If no expressions match, then a value of 'error' is returned. If more
     * than one match is found, the first is chosen.
     * 
     * @param value the string from which a regex matching group is returned
     * @param header the <code>Header</code> that contains the regex pattern to match
     * @return the substring from <code>value</code> that matches the regex pattern in <code>header</code>, or 'error'
     *         is no matches are found.
     */
    private String retain(String value, Header header) {
        if (header.getRegexRetain() == null)
            return value;

        Pattern p = null;
        if (header.isRegexRetainCaseInsensitive())
            p = Pattern.compile(header.getRegexRetain(), Pattern.CASE_INSENSITIVE);
        else
            p = Pattern.compile(header.getRegexRetain());

        Matcher match = p.matcher(value);
        ArrayList<String> allFound = new ArrayList<String>();
        while (match.find()) {
            allFound.add(match.group());
        }

        if (allFound.size() > 0) {
            return allFound.get(0);
        }
        return "error";
    }

    /**
     * Determines if the event should not be stored, as determined by a <fieldname, value> match on the input
     * <code>authE</code> event with those in the <code>exclusionList</code>.
     * 
     * @param authE the <code>Event</code> that is tested for exclusion
     * @return true if any <fieldname,value> pairs in the <code>autheE</code> event are found in the exclusion list,
     *         false otherwise
     */
    private boolean isExcluded(Event authE) {
        if (getExclusionList() != null) {
            for (AbstractExclusionEntry exclusion : getExclusionList().getExclusionEntries()) {
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
     * Determines if the event should be stored, as determined by a <fieldname, value> match on the input
     * <code>authE</code> event with those in the <code>exclusionList</code>.
     * 
     * @param authE
     * @return true if any <fieldname,value> pairs in the <code>autheE</code> event are found in the inclusion list,
     *         false otherwise
     */
    private boolean isIncluded(Event authE) {
        if (getInclusionList() != null) {
            for (AbstractInclusionEntry inclusion : getInclusionList().getInclusionEntries()) {
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
     * Efficient method for finding the number of lines in a logfile. Where each line is denoted by the line break
     * character \n
     * 
     * @param logfileURL the location, as a URL, of the logfile that is to be processed
     * @return the number of lines in the <code>logfileURL</code>
     * @throws IOException if the <code>logfileURL</code> is not the location of valid file
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
     * Counts the number of lines (where each line is denoted by the line break character \n) stored by a buffered
     * reader. Maybe not such an efficient method
     * 
     * @param reader the <code>BufferedReader</code> that the line count is performed on
     * @return the number of lines in the <code>reader</code> input parameter
     * @throws IOException
     */
    private int count(byte[] bytes) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(bytes)));
        int lineCount = 0;
        while (reader.readLine() != null) {
            lineCount++;
        }
        return lineCount;

    }

    /**
     * Returns the value of the <code>fieldname</code> parameter from the <code>object</code> Object
     * 
     * @param fieldname the name of the field from which to retrieve the value
     * @param object the object from which to retrieve the value
     * @return an object that represents the value of the <code>fieldname</code> from the given <code>object</code>
     */
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
     * @param printParsingPosition the printParsingPosition to set
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
     * @param lineFilterEngine the lineFilterEngine to set
     */
    public void setLineFilterEngine(LineFilterEngine lineFilterEngine) {
        this.lineFilterEngine = lineFilterEngine;
    }

    /**
     * @return the lineFilterEngine
     */
    public LineFilterEngine getLineFilterEngine() {
        return lineFilterEngine;
    }

}
