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
package main.uk.ac.cf.dao.external.file;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

import main.uk.ac.cf.dao.external.AuthenticationInput;
import main.uk.ac.cf.dao.external.format.Header;
import main.uk.ac.cf.dao.external.format.LogFileFormat;
import main.uk.ac.cf.model.AuthenticationEntry;

import org.apache.commons.lang.text.StrTokenizer;
import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import runtimeutils.ReflectionHelper;

/**
 * @author phil
 *
 *         This is the primary log file parser.
 *
 */
public class LogFileParser implements AuthenticationInput {
	static Logger log = Logger.getLogger(LogFileParser.class);
	private LogFileFormat format;
	private String logfile;

	public void parse() throws Exception {

		log.debug("parsing: " + logfile);
		// Must use URL, as java.io does not work in a webapp

		URL logfileURL = new URL(logfile);
		URLConnection logfileconnection = logfileURL.openConnection();
		BufferedReader in = new BufferedReader(new InputStreamReader(
				logfileconnection.getInputStream()));
		String inputLine;
		ArrayList<AuthenticationEntry> authenticationEntries = new ArrayList();
		while ((inputLine = in.readLine()) != null) {
			// log.debug(inputLine);
			StrTokenizer tokenizer = new StrTokenizer(inputLine, format.getDelimeter());
			ArrayList<String> allvalues = new ArrayList();
			while (tokenizer.hasNext()) {
				Object next = tokenizer.next();
				if (next instanceof String)
					allvalues.add((String) next);
				else {
					log.error("input column was not a string");
				}
			}
			// now pickout the headers with which to populate the class
			AuthenticationEntry authE = new AuthenticationEntry();

			for (Header header : format.getHeaders()) {

				try {
					if (!(header.getFieldNo() >= allvalues.size())) {
						String value = allvalues.get(header.getFieldNo());
						log.debug("Field: " + header.getFieldName()	+ " value: " + value+" Type:"+ header.getType());
						switch (header.getType()){
							case DATE:addDate(value, header.getDateTimeFormat(),header.getFieldName(),authE); break;
							case STRING:addString(value,header.getFieldName(),authE);break;
							case INTEGER:addInteger(value,header.getFieldName(),authE);break;
						}

					} else {
						log.error("trying to access field "	+ header.getFieldNo() + ", when only "	+ allvalues.size() + " fields in log file");
					}
				} catch (ClassCastException e) {
					log.error("Header Key is not an integer, needs to be an integer.");
				}
			}
			log.debug("AuthObject: "+authE);
			authenticationEntries.add(authE);
		}

		in.close();

		log.debug("done");

	}

	private void addDate(String value, String format, String fieldName, Object object){
		try{
			//first check the end of the date, if it has a Z it should have time zone information e.g. -0800 or +0500
			//but if it ends in just Z, there is not valid timezone information supplied, so remove
			if (value.endsWith("Z")) value = value.substring(0,value.length()-1);

			DateTimeFormatter dtf = DateTimeFormat.forPattern(format);
			DateTime dt = dtf.parseDateTime(value.substring(0,value.length()));
			String fieldAsMethod = ReflectionHelper.prepareMethodNameSet(fieldName);
			setValueOnObject(fieldAsMethod, dt, object);
		}
		catch(Exception e){
			log.error("Illegal Date Format For: "+value+" and format:"+format+" | "+e.getMessage());
			//e.printStackTrace();
		}

	}
	private void addString(String value,String fieldName, Object object){
		try{
			String fieldAsMethod = ReflectionHelper.prepareMethodNameSet(fieldName);
			setValueOnObject(fieldAsMethod, value, object);
		}
		catch(Exception e){
			log.error("Illegal String Value: "+value+" for field:"+fieldName+" | "+e.getMessage());
			//e.printStackTrace();
		}

	}
	private void addInteger(String value, String fieldName, Object object){
		try{
			Integer valueAsInt = new Integer(Integer.parseInt(value));
			String fieldAsMethod = ReflectionHelper.prepareMethodNameSet(fieldName);
			setValueOnObject(fieldAsMethod, value, object);
		}
		catch(Exception e){
			log.error("Illegal Integer Value: "+value+" for field:"+fieldName+" | "+e.getMessage());
			//e.printStackTrace();
		}

	}

	private void setValueOnObject(String fieldname, Object param, Object object){
		try {
			Class id = object.getClass();
			Method setter = id.getMethod(fieldname,new Class[]{param.getClass()});
			setter.invoke(object, new Object[]{param});
		} catch (Throwable e) {
			log.error("Field name '"+ fieldname	+ "' does not match internal model attribute");
			e.printStackTrace();
			System.exit(1);

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

	// public static void main(String args[]) throws IOException{
	// LogFileParser lfp = new LogFileParser();
	// lfp.setLogfile("/tmp/idp-access.log");
	// lfp.setFormat(new LogFileFormat());
	// lfp.parse();
	// }

}
