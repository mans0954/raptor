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
 * NOT USED
 */
package main.uk.ac.cf.model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.Properties;

import org.apache.log4j.Logger;

/**
 * @author philsmart
 *
 */
public class PersistentParserSupport {

    /* class logger */
    static Logger log = Logger.getLogger(PersistentParserSupport.class);

    /* the class that stores state information about the current parsing state*/
    private int lastLineParsed;

    private Properties logFileProperties;

    private String location;

    private URL fileLocationURL;

    public PersistentParserSupport(String location){
	this.location = location;
	boolean loaded = load();
	if (loaded)
	    setLastLineParsed(getStartingLineNumber());

    }

    private boolean load(){
	try {
	    fileLocationURL = PersistentParserSupport.class.getResource(location);
	    InputStream fileInputStream = fileLocationURL.openStream();

	    logFileProperties = new Properties();
	    logFileProperties.load(fileInputStream);
	    return true;
	} catch (Exception e) {
	    log.error("Error loading "+location+" "+e.getMessage());
	    e.printStackTrace();
	    return false;
	}
    }

    public void write(int lastLineParsed){
	try {
	    log.debug("Writing to parsing properties file lastLineParsed = "+lastLineParsed);
	    logFileProperties.setProperty("logfileparser.startingLineToParseFrom", String.valueOf(lastLineParsed));
	    //log.debug("Last Line Set To: "+lastLineParsed+" writing to: "+fileLocationURL.getFile());
	    OutputStream fileOutputStream = new FileOutputStream(fileLocationURL.getFile());
	    logFileProperties.store(fileOutputStream,null);
	} catch (Exception e) {
	    log.error("Error loading "+location+" "+e.getMessage());
	    e.printStackTrace();
	}
    }

    /**
     * <p> Reads the starting line number from the properties file. Resorts to the first line (0) if
     * no such property is set </p>
     * @return
     */
    private int getStartingLineNumber(){
	try{
	    int startingLineNumber = (Integer.parseInt(logFileProperties.getProperty("logfileparser.startingLineToParseFrom")));
	    return startingLineNumber;
	}
	catch(NumberFormatException e){
	    e.printStackTrace();
	    return 0;
	}
    }

    public void setLastLineParsed(int lastLineParsed) {
	this.lastLineParsed = lastLineParsed;
    }

    public int getLastLineParsed() {
	return lastLineParsed;
    }

    public void setLocation(String location) {
	this.location = location;
    }

    public String getLocation() {
	return location;
    }



}
