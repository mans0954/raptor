/**
 *
 */
package uk.ac.cardiff.RaptorUA.model;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;

import uk.ac.cardiff.model.Entry;

/**
 * @author philsmart
 *
 */
public class MemoryEntryHandler implements EntryHandler{
    static Logger log = Logger.getLogger(EntryHandler.class);

	/* set of all entries stored by this EntryHanlder */
	Set<Entry> entries;

	public MemoryEntryHandler(){
		entries = new LinkedHashSet<Entry>();
	}

	public void addEntries(Set<Entry> entries){
		log.info("Adding "+entries.size()+" entries to the UA");
		for (Entry entry: entries){
			this.entries.add(entry);
		}
		log.info("UA now has a total of "+this.entries.size()+" entries");
	}



	/**
	 * @return the list of entries currently stored by the entry handler
	 */
	public Set getEntries() {
	    return entries;

	}

	public void setEntries(Set<Entry> entries){
	    this.entries = entries;
	}

	/**
	 *
	 */
	public void removeAllEntries() {
	    /* remove all entries, but do not reset last entry, as this is still
	     * used not to add previously parsed entries.
	     */
	    entries.clear();

	}
}
