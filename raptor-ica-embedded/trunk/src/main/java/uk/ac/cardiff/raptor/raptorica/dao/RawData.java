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
package uk.ac.cardiff.raptor.raptorica.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import uk.ac.cardiff.raptor.raptorica.model.EntryHandler;



/**
 * @author philsmart
 *
 */
public abstract class RawData {
	/* class logger */
    static Logger log = LoggerFactory.getLogger(RawData.class);
    private EntryHandler entryHandler;

    public abstract void parse() throws Exception;

    public RawData() {
	//setEntryHandler(new MemoryEntryHandler());
    }

    public Object createObject(String className) {
	Object object = null;
	try {
	    Class classDefinition = Class.forName(className);
	    object = classDefinition.newInstance();
	    // this.setBeanProperty(object, "applicationContext",
	    // this.getApplicationContext());
	} catch (InstantiationException e) {
	    log.warn(e.getMessage());
	} catch (IllegalAccessException e) {
	    log.warn(e.getMessage());
	} catch (ClassNotFoundException e) {
	    log.warn(e.getMessage());
	}
	return object;
    }

    /**
     * removes all entries from the entry handler
     */
    public void removeAllEntries() {
	getEntryHandler().removeAllEntries();

    }

    public void setEntryHandler(EntryHandler entryHandler) {
	this.entryHandler = entryHandler;
	entryHandler.initialise();
    }

    public EntryHandler getEntryHandler() {
	return entryHandler;
    }

}
