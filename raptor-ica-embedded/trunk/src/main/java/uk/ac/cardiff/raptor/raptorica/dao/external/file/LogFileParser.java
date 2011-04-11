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
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.io.BufferedInputStream;

import uk.ac.cardiff.raptor.raptorica.dao.BaseEventParser;
import uk.ac.cardiff.raptor.raptorica.dao.external.format.Header;
import uk.ac.cardiff.raptor.raptorica.dao.external.format.LineFilter;
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
 * A log file parser, used to parse files on the local filesystem according to a
 * given format and transformed into the given event type.
 *
 * @author phil
 *
 */
public class LogFileParser extends BaseEventParser {

	/** The class logger */
	private final Logger log = LoggerFactory.getLogger(LogFileParser.class);

	/** The specification of the format of this log file */
	private LogFileFormat format;

	/** The fully qualified path of the log file */
	private String logfile;

	/** The fully qualified name of the event type class */
	private String eventType;

	/**
	 * Whether to print out the current position of parsing in the
	 * <code>logFile</code>
	 */
	private boolean printParsingPosition;

	/** A filter to remove a line from the raw log file */
	private LineFilter lineFilter;

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
				boolean parseLine = true;
				if (lineFilter != null) {
					parseLine = lineFilter.parsableLine(inputLine);
				}
			//	log.debug("Parse [{}] - {}",parseLine,inputLine);
				if (parseLine == true) {
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
					//log.debug("Event: " + authE.toString());
					boolean shouldBeIncluded = isIncluded(authE);
					boolean preventAdd = isExcluded(authE);

					if (shouldBeIncluded && !preventAdd) {
						entryHandler.addEntry(authE);
					}
					//System.exit(1);
				}

			}
			in.close();

			log.debug("done, walked {} lines", lineCount);
			log.debug("LogFileParser currently has: {} entries, latestEntry: {}", getEntryHandler().getEntries().size(), getEntryHandler().getLatestEntryTime());
			entryHandler.endTransaction();
		} catch (MalformedURLException e1) {
			throw new ParserException("Could not find the source file [" + logfile + "] for parsing", e1);
		} catch (IOException e2) {
			throw new ParserException("Could not read from the source file [" + logfile + "] during parsing", e2);
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
	 * Populates attributes in the Event class <code>authE</code> (or subclasses
	 * thereof) from the headers defined in <code>format</code>
	 *
	 * @param allvalues values for each field in a single record
	 * @param authE the Event to populate
	 * @throws HeaderException if trying to set a value on a field that does not exist in the list <code>allvalues</code>
	 */
	private void populateField(List<String> allvalues, Event authE) throws HeaderException {
		for (Header header : format.getHeaders()) {
			try {
				if (!(header.getFieldNo() >= allvalues.size())) {
					String value = getFieldValue(allvalues, header);
					value = replace(value, header);
					value = retain(value, header);
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
				throw new HeaderException("Header key is not an integer, needs to be an integer (error could exist in data-access.xml)", header.getFieldNo(), e);

			}
		}
	}

	private String getFieldValue(List<String> allvalues, Header header) {
		StringBuilder output = new StringBuilder();
		output.append(allvalues.get(header.getFieldNo()));
		if (header.getAdditionalFieldNos() != null) {
			for (int fieldNo : header.getAdditionalFieldNos()) {
				output.append(allvalues.get(fieldNo));
			}
		}
		return output.toString();
	}

	/**
	 * Decodes a URL based on the ISO-8859-1 ISO-Latin character set
	 *
	 * @param value the URL to be decoded
	 * @return the <code>value</code> URL decoded
	 */
	private String decode(String value) {
		try {
			String decodedURL = URLDecoder.decode(value, "ISO-8859-1");
			return decodedURL;
		} catch (UnsupportedEncodingException e) {
			return value;
		}
	}

	/**
	 * Replace substrings from <code>value</code> with the given strings in the <code>header</code>
	 * that match the regular expression(s) in the regexReplaceAll from the <code>header</code>
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
	 * variable in the <code>header</code> input.
	 *
	 * @param value the string from which a regex matching group is returned
	 * @param header the <code>Header</code> that contains the regex pattern to match
	 * @return the substring from <code>value</code> that matches the regex pattern in <code>header</code>
	 */
	private String retain(String value, Header header) {
		if (header.getRegexRetain() == null)
			return value;

		Pattern p = Pattern.compile(header.getRegexRetain());
		Matcher match = p.matcher(value);
		ArrayList<String> allFound = new ArrayList<String>();
		while (match.find()) {
			allFound.add(match.group());
		}
		if (allFound.size() > 0) {
			return allFound.get(0);
		}
		return value;
	}

	/**
	 * Determines if the event should not be stored, as determined by a
	 * <fieldname, value> match on the input <code>authE</code> event with those
	 * in the <code>exclusionList</code>.
	 *
	 * @param authE the <code>Event</code> that is tested for exclusion
	 * @return true if any <fieldname,value> pairs in the <code>autheE</code>
	 *         event are found in the exclusion list, false otherwise
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
	 * Determines if the event should stored, as determined by a <fieldname,
	 * value> match on the input <code>authE</code> event with those in the
	 * <code>exclusionList</code>.
	 *
	 * @param authE
	 * @return true if any <fieldname,value> pairs in the <code>autheE</code>
	 *         event are found in the inclusion list, false otherwise
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
	 * Converts the <code>value</code> into a list delimited by the <code> delimeter</code> input,
	 * and adds the list of string values to the <code>fieldName</code> of the <code>object</code> using
	 * Reflection.
	 *
	 * @param value the value to add to the <code>object</code>
	 * @param fieldName the name of the field that the value is added to
	 * @param delimeter the delimeter used to split the <code>value</code> into a list
	 * @param object the object with which to set the <code>value</code> on
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



	/**
	 * Converts the <code>value</code> into a date using the given <code>format</code> and <code>timezone</code>
	 * parameters, and adds the date to the <code>fieldName</code> of the <code>object</code> using
         * Reflection.
	 *
	 * @param value the value to add to the <code>object</code>
	 * @param format the date format used to parse the <code>value</code> into a string
	 * @param timezone sets the timezone on the newly created date
	 * @param fieldName the name of the field that the value is added to
	 * @param object the object with which to set the <code>value</code> on
	 */
	private void addDate(String value, String format, String timezone, String fieldName, Object object) {
		try {
			/*
			 * first check the end of the date, if it has a Z it should have
			 * time zone information e.g. -0800 or +0500 but if it ends in just
			 * Z, there is not valid timezone information supplied, so remove
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

	/**
	 * Adds the string <code>value</code> to the <code>fieldName</code> of the <code>object</code> using
         * Reflection.
	 *
	 * @param value the value to add to the <code>object</code>
	 * @param fieldName the name of the field that the value is added to
	 * @param object the object with which to set the <code>value</code> on
	 */
	private void addString(String value, String fieldName, Object object) {
		try {
			String fieldAsMethod = ReflectionHelper.prepareMethodNameSet(fieldName);
			setValueOnObject(fieldAsMethod, value, object);
		} catch (Exception e) {
			log.error("Illegal String Value: {} for field: {}", new Object[] { value, fieldName, e });
		}

	}

	/**
	 * Converts the <code>value</code> into an Integer, and adds the Integer to the
	 * <code>fieldName</code> of the <code>object</code> using
         * Reflection.
         *
         * @param value the value to add to the <code>object</code>
         * @param fieldName the name of the field that the value is added to
         * @param object the object with which to set the <code>value</code> on
	 */
	private void addInteger(String value, String fieldName, Object object) {
		try {
			String fieldAsMethod = ReflectionHelper.prepareMethodNameSet(fieldName);
			setValueOnObject(fieldAsMethod, value, object);
		} catch (Exception e) {
			log.error("Illegal Integer Value: {} for field: {}", new Object[] { value, fieldName, e });
		}

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

	/**
	 * Sets the <code>param</code> object on the field <code>fieldname</code> of the object <code>object</code>
	 *
	 * @param fieldname the name of the field from which to set the value <code>param</code>
	 * @param param the object to set on the <code>fieldname</code> of the <code>object</code>
	 * @param object the object with which to set the object <code>param</code>
	 */
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
	 * @param eventType
	 *            the eventType to set
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
	 * @param lineFilter
	 *            the lineFilter to set
	 */
	public void setLineFilter(LineFilter lineFilter) {
		this.lineFilter = lineFilter;
	}

	/**
	 * @return the lineFilter
	 */
	public LineFilter getLineFilter() {
		return lineFilter;
	}

}
