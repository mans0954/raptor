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
 *
 *
 * @author philsmart
 * {@literal} this class manages interaction with the internal model of the ICA
 *
 */

package uk.ac.cardiff.RaptorUA.model;

import java.util.ArrayList;
import java.util.List;

import uk.ac.cardiff.model.Entry;

import org.apache.log4j.Logger;
import org.joda.time.DateTime;


public class EntryHandler {
	static Logger log = Logger.getLogger(EntryHandler.class);

	/* list of all entries stored by this EntryHanlder */
	List<Entry> entries;

	public EntryHandler(){
		entries = new ArrayList<Entry>();
	}

	public void addEntries(List<Entry> entries){
		log.info("Adding "+entries.size()+" entries to the UA");
		for (Entry entry: entries){
			this.entries.add(entry);
		}
		log.info("UA now has a total of "+this.entries.size()+" entries");
	}



	/**
	 * @return the list of entries currently stored by the entry handler
	 */
	public List getEntries() {
	    return entries;

	}

	public void setEntries(List<Entry> entries){
	    this.entries = entries;
	}


}
