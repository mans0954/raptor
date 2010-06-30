/**
 *
 */
package uk.ac.cardiff.raptormua.model;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import uk.ac.cardiff.model.Entry;

/**
 * @author philsmart
 *
 */
public class MemoryEntryHandler implements EntryHandler{
    static Logger log = Logger.getLogger(EntryHandler.class);

	/* list of all entries stored by this EntryHanlder */
	List<Entry> entries;

	public MemoryEntryHandler(){
		entries = new ArrayList<Entry>();
	}

	public void addEntries(List<Entry> entries){
		log.info("Adding "+entries.size()+" entries to the MUA");
		for (Entry entry: entries){
			this.entries.add(entry);
		}
		log.info("MUA now has a total of "+this.entries.size()+" entries");
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
