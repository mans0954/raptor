/**
 *
 */
package main.uk.ac.cf.model;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.joda.time.DateTime;

import uk.ac.cardiff.model.Entry;

/**
 * @author philsmart
 *
 */
public class EntryMetadata {

    /* used if a persistant db primary key is required, only one entrymetadata
     * so only id of one needed*/
    private Long persistantId;

    /* pointer to the last recorded entry, for incremental update */
    private DateTime latestEntryTime;

    /* record of the last entry that was sent over SOAP */
    private DateTime lastPublishedEntryTime;

    /* stores the list of latest unique entries as their hash code (for easy persisting). That is, those with the latest and same
     *  DateTime, but different hashcode (attribute values). */
    private List<Integer> latestEqualEntries;

    public EntryMetadata(){
	latestEqualEntries = new ArrayList<Integer>();
    }

    public void setLatestEntryTime(DateTime latestEntryTime) {
	this.latestEntryTime = latestEntryTime;
    }

    public DateTime getLatestEntryTime() {
	return latestEntryTime;
    }

    public void setLastPublishedEntryTime(DateTime lastPublishedEntryTime) {
	this.lastPublishedEntryTime = lastPublishedEntryTime;
    }

    public DateTime getLastPublishedEntryTime() {
	return lastPublishedEntryTime;
    }

    public void setPersistantId(Long persistantId) {
	this.persistantId = persistantId;
    }

    public Long getPersistantId() {
	return persistantId;
    }

    public String toString(){
	return "["+persistantId+":"+latestEntryTime+","+lastPublishedEntryTime+"]";
    }

    public void setLatestEqualEntries(List<Integer> latestEqualEntries) {
	this.latestEqualEntries = latestEqualEntries;
    }

    public List<Integer> getLatestEqualEntries() {
	return latestEqualEntries;
    }

}
