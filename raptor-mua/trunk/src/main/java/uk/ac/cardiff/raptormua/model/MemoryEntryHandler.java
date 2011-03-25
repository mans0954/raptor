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
package uk.ac.cardiff.raptormua.model;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import uk.ac.cardiff.model.Event;

/**
 * @author philsmart
 *
 */
public class MemoryEntryHandler implements EntryHandler{

	/* class level logger */
    	static Logger log = LoggerFactory.getLogger(MemoryEntryHandler.class);

	/* list of all entries stored by this EntryHandler */
    	Set<Event> entries;

	public MemoryEntryHandler(){

	}

	public void addEntries(Set<Event> entries){
		log.info("Adding {} entries to the MUA",entries.size());
		for (Event entry: entries){
			this.entries.add(entry);
		}
		log.info("MUA now has a total of "+this.entries.size()+" entries");
	}

	public void initialise(){
		log.info("Memory entry handler [{}] initialising",this);
		entries = new LinkedHashSet<Event>();
		log.info("Memory entry handler [{}] started",this);
	}



	/**
	 * @return the list of entries currently stored by the entry handler
	 */
	public Set getEntries() {
	    return entries;

	}

	public void setEntries(Set<Event> entries){
	    this.entries = entries;
	}

	/* (non-Javadoc)
	 * @see uk.ac.cardiff.raptormua.model.EntryHandler#endTransaction()
	 */
	@Override
	public void endTransaction() {
	    // TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see uk.ac.cardiff.raptormua.model.EntryHandler#removeAllEntries()
	 */
	@Override
	public void removeAllEntries() {
	    // TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see uk.ac.cardiff.raptormua.model.EntryHandler#query(java.lang.String)
	 */
	@Override
	public List query(String query) {
	    // TODO Auto-generated method stub
	    return null;
	}

	/* (non-Javadoc)
	 * @see uk.ac.cardiff.raptormua.model.EntryHandler#queryUnique(java.lang.String)
	 */
	@Override
	public Object queryUnique(String query) {
	    // TODO Auto-generated method stub
	    return null;
	}

	/* (non-Javadoc)
	 * @see uk.ac.cardiff.raptormua.model.EntryHandler#getNumberOfEntries()
	 */
	@Override
	public int getNumberOfEntries() {
	   return entries.size();
	}

	/* (non-Javadoc)
	 * @see uk.ac.cardiff.raptormua.model.EntryHandler#queryUnique(java.lang.String, java.lang.Object[])
	 */
	@Override
	public Object queryUnique(String query, Object[] parameters) {
	    // TODO Auto-generated method stub
	    return null;
	}
}
