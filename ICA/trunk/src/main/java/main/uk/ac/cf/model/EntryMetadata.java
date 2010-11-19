/**
 *
 */
package main.uk.ac.cf.model;

import java.util.LinkedHashSet;
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

    /* stores the set of latest unique entries. That is, those with the latest and same
     *  DateTime, but different state (attribute values). This set is check when adding new
     *  entries, and is not removed when records are removed. Will not typically hold many values
     */
    private Set<Entry> latestEqualEntries;

    public EntryMetadata(){
	latestEqualEntries = new LinkedHashSet<Entry>();
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

    void setLatestEqualEntries(Set<Entry> latestEqualEntries) {
	this.latestEqualEntries = latestEqualEntries;
    }

    Set<Entry> getLatestEqualEntries() {
	return latestEqualEntries;
    }

}
