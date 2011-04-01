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
package uk.ac.cardiff.raptor.raptorica.engine;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import uk.ac.cardiff.raptor.raptorica.dao.BaseEventParser;

/**
 * @author philsmart
 *
 * Provides a register for parsing modules to attach to.
 */
public class DataAccessRegister {

    	/** Class logger */
	private final Logger log = Logger.getLogger(DataAccessRegister.class);

	/** List of parsing modules */
	private List<BaseEventParser> parsingModules;

	/**
	 * Default constructor that sets an empty list of parsing modules by default.
	 */
	public DataAccessRegister(){
	    setParsingModules(new ArrayList<BaseEventParser>());
	}

	/**
	 * Sets and prints the name of all parsing modules passed as an input parameter
	 *
	 * @param parsingModules
	 */
	public void setParsingModules(List<BaseEventParser> parsingModules) {
		for (BaseEventParser parser : parsingModules)
		    log.info("Registering: "+parser.getClass());
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




}
