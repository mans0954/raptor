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
package main.uk.ac.cf.dao;

import java.util.List;


import org.apache.log4j.Logger;
import org.joda.time.DateTime;

import main.uk.ac.cf.dao.external.file.LogFileParser;
import main.uk.ac.cf.model.EntryHandler;

/**
 * @author philsmart
 *
 */
public abstract class RawData {
	static Logger log = Logger.getLogger(RawData.class);
	public EntryHandler entryHandler;



	public abstract void parse() throws Exception;

	public RawData(){
		entryHandler = new EntryHandler();
	}

	public Object createObject(String className) {
	      Object object = null;
	      try {
	          Class classDefinition = Class.forName(className);
	          object = classDefinition.newInstance();
	          //this.setBeanProperty(object, "applicationContext", this.getApplicationContext());
	      } catch (InstantiationException e) {
	          log.warn(e);
	      } catch (IllegalAccessException e) {
	    	  log.warn(e);
	      } catch (ClassNotFoundException e) {
	    	  log.warn(e);
	      }
	      return object;
	   }


}
