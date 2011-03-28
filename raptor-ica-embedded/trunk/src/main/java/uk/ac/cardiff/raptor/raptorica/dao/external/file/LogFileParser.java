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
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.TimeZone;
import java.io.BufferedInputStream;

import uk.ac.cardiff.raptor.raptorica.dao.external.AuthenticationInput;
import uk.ac.cardiff.raptor.raptorica.dao.external.format.Header;
import uk.ac.cardiff.raptor.raptorica.dao.external.format.LogFileFormat;
import uk.ac.cardiff.raptor.raptorica.dao.internal.ICADataConnection;
import uk.ac.cardiff.raptor.raptorica.model.PersistentParserSupport;
import uk.ac.cardiff.raptor.raptorica.model.filter.ExclusionEntry;
import uk.ac.cardiff.raptor.raptorica.model.filter.InclusionEntry;

import org.apache.commons.lang.text.StrTokenizer;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;

import uk.ac.cardiff.raptor.raptorica.runtimeutils.ReflectionHelper;
import uk.ac.cardiff.model.*;
import uk.ac.cardiff.model.event.Event;

/**
 * @author phil
 *
 *         This is the primary log file parser.
 *
 */
public class LogFileParser extends AuthenticationInput {
    static Logger log = LoggerFactory.getLogger(LogFileParser.class);
    private LogFileFormat format;
    private String logfile;
    private String entryType;

    public LogFileParser() {

    }

    public void parse() throws Exception {
	log.info("parsing: {} instance: {}",logfile, this);

	// Must use URL, as java.io does not work in a webapp
	URL logfileURL = new URL(logfile);
	URLConnection logfileconnection = logfileURL.openConnection();
	BufferedReader in = new BufferedReader(new InputStreamReader(logfileconnection.getInputStream()));
	int lineCount = 0;
	String inputLine;

	int totalNoLines =count(logfileURL);

	log.debug("Parsing file with {} lines",totalNoLines);
	while ((inputLine = in.readLine()) != null) {
	    // log.debug(inputLine);
	    lineCount++;
	    double linePercentage = (((double)lineCount/(double)totalNoLines)*100);
	    if (linePercentage%25>=0 && linePercentage%25<=0.0001) log.debug("Complete {}%",linePercentage);

	    StrTokenizer tokenizer = new StrTokenizer(inputLine, format.getDelimeter());
	    tokenizer.setIgnoreEmptyTokens(false);
	    ArrayList<String> allvalues = new ArrayList();
	    while (tokenizer.hasNext()) {
		Object next = tokenizer.next();
		if (next instanceof String)
		    allvalues.add((String) next);
		else {
		    log.error("input column was not a string");
		}
	    }
	    /* first, dynamically construct the supplied entry class, which is subclass of Event */
	    Event authE = (Event) this.createObject(getEntryType());

	    /* now populate its fields */
	    for (Header header : format.getHeaders()) {

		try {
		    if (!(header.getFieldNo() >= allvalues.size())) {
			String value = allvalues.get(header.getFieldNo());
			// log.debug("Field: " + header.getFieldName() +
			// " value: " + value+" Type:"+ header.getType());
			switch (header.getType()) {
			case DATE:
			    addDate(value, header.getDateTimeFormat(), header.getTimeZone(),header.getFieldName(), authE);
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
			log.error("trying to access field " + header.getFieldNo() + ", when only " + allvalues.size() + " fields in log file \n --> " + inputLine);
		    }
		} catch (ClassCastException e) {
		    log.error("Header key is not an integer, needs to be an integer (possibly look at data-access.xml).");
		}
	    }

	    /* check its IN the inclusion list */
	    boolean shouldBeIncluded = false;
	    if (getInclusionList()!=null){
		for (InclusionEntry inclusion : getInclusionList().getInclusionEntries()){
		    String fieldname = inclusion.getFieldName();
		    Object valueFromEntry = getValueFromObject(fieldname, authE);
		    if (valueFromEntry instanceof String) {
			String valueFromEntryString = (String)valueFromEntry;
			if (inclusion.filter(valueFromEntryString)){
			    //log.debug("entry [{}] had match true",valueFromEntryString);
			    shouldBeIncluded = true;
			}
		    }
		}
	    }
	    else{
		//no inclusion list, assume all
		shouldBeIncluded = true;
	    }

	    /* now check its not in the exclusion list */
	    boolean preventAdd = false;
	    if (getExclusionList() != null) {
		for (ExclusionEntry exclusion : getExclusionList().getExclusionEntries()){
		    String fieldname = exclusion.getFieldName();
		    Object valueFromEntry = getValueFromObject(fieldname, authE);
		    if (valueFromEntry instanceof String) {
			String valueFromEntryString = (String)valueFromEntry;
			if (exclusion.filter(valueFromEntryString)){
			    preventAdd=true;
			}
		    }
		}
	    }

	    if (shouldBeIncluded && !preventAdd) {
		/*
		 * do not add the object to the arrayList if is timestamp is
		 * older than the current latest entry this should save heap
		 * space during operation
		 */

		getEntryHandler().addEntry(authE);
	    }

	}

	in.close();

	log.debug("done, walked " + lineCount + " lines");
	// getEntryHandler().addEntries(entries.);
	log.debug("Currently has: " + getEntryHandler().getEntries().size() + " entries, latestEntry: " + getEntryHandler().getLatestEntryTime());
	getEntryHandler().endTransaction();
	//System.exit(1);

	/*
	 * this is important, after one complete transaction, we push the
	 * latestTime entry by 1 millisecond, this stops the last entry of this
	 * transaction (n) being equal to itself in the second (n+1) transaction
	 * and so on. HOWEVER, this behaviour will also prevent a valid new
	 * entry in the n+1 transaction from being stored if it was a unique
	 * entry but at the same time as the last entry of the nth transaction.
	 * WE may therefore loose one or possibly two (more likely one) entry
	 * from the n+1 transactions - revise this. The good thing about this
	 * logic, is that we only need to check timestamps to obtain
	 * psuedo-incremental updates (even though the entire file is read in
	 * again, only newer entries are saved)
	 */
	// entryHandler.endTransaction();

    }

    /**
     * Fast method for finding the number of lines in a logfile
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
	    // for (String s : splitValue)log.debug(s);
	    String fieldAsMethod = ReflectionHelper.prepareMethodNameSet(fieldName);
	    setValueOnObject(fieldAsMethod, splitValue, object);
	} catch (Exception e) {
	    log.error("Illegal StringList Value: " + value + " for field:" + fieldName + " | " + e.getMessage());
	    // e.printStackTrace();
	}
    }

    private void addDate(String value, String format, String timezone, String fieldName, Object object) {
	try {
	    /*
	     * first check the end of the date, if it has a Z it should have
	     * time zone information e.g. -0800 or +0500 but if it ends in just
	     * Z, there is not valid timezone information supplied, so remove
	     */
	    if (value.endsWith("Z"))
		value = value.substring(0, value.length() - 1);

	    DateTimeFormatter dtf = DateTimeFormat.forPattern(format);
	    dtf = dtf.withZone(DateTimeZone.forTimeZone(TimeZone.getTimeZone(timezone)));
	    DateTime dt = dtf.parseDateTime(value.substring(0, value.length()));
	    String fieldAsMethod = ReflectionHelper.prepareMethodNameSet(fieldName);
	    setValueOnObject(fieldAsMethod, dt, object);
	} catch (Exception e) {
	    log.error("Illegal Date Format For: " + value + " and format:" + format + " | " + e.getMessage());
	    // e.printStackTrace();
	}

    }

    private void addString(String value, String fieldName, Object object) {
	try {
	    String fieldAsMethod = ReflectionHelper.prepareMethodNameSet(fieldName);
	    setValueOnObject(fieldAsMethod, value, object);
	} catch (Exception e) {
	    log.error("Illegal String Value: " + value + " for field:" + fieldName + " | " + e.getMessage());
	    // e.printStackTrace();
	}

    }

    private void addInteger(String value, String fieldName, Object object) {
	try {
	    Integer valueAsInt = new Integer(Integer.parseInt(value));
	    String fieldAsMethod = ReflectionHelper.prepareMethodNameSet(fieldName);
	    setValueOnObject(fieldAsMethod, value, object);
	} catch (Exception e) {
	    log.error("Illegal Integer Value: " + value + " for field:" + fieldName + " | " + e.getMessage());
	    // e.printStackTrace();
	}

    }

    private Object getValueFromObject(String fieldname, Object object) {
	try {
	    Class id = object.getClass();
	    String fieldAsMethod = ReflectionHelper.prepareMethodNameGet(fieldname);
	    Method getter = id.getMethod(fieldAsMethod, new Class[] {});
	    // log.debug("Trying to Set :"+param)
	    Object result = getter.invoke(object, new Object[] {});
	    return result;
	} catch (Throwable e) {
	    log.error("Field name '" + fieldname + "' does not match internal model attribute");
	    e.printStackTrace();
	    // System.exit(1);

	}
	return null;
    }

    private void setValueOnObject(String fieldname, Object param, Object object) {
	try {
	    Class id = object.getClass();
	    Method setter = id.getMethod(fieldname, new Class[] { param.getClass() });
	    // log.debug("Trying to Set :"+param)
	    setter.invoke(object, new Object[] { param });
	} catch (Throwable e) {
	    log.error("Field name '" + fieldname + "' does not match internal model attribute");
	    e.printStackTrace();
	    // System.exit(1);

	}
    }

    private void preParse() {

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

    public void setEntryType(String entryType) {
	this.entryType = entryType;
    }

    public String getEntryType() {
	return entryType;
    }

    // public static void main(String args[]) throws IOException{
    // LogFileParser lfp = new LogFileParser();
    // lfp.setLogfile("/tmp/idp-access.log");
    // lfp.setFormat(new LogFileFormat());
    // lfp.parse();
    // }

}
