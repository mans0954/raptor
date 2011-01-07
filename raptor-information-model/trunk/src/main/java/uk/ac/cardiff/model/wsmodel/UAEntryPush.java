/**
 *
 */
package uk.ac.cardiff.model.wsmodel;

import java.util.Date;
import java.util.Set;

import uk.ac.cardiff.model.Entry;
import uk.ac.cardiff.model.ICAMetadata;
import uk.ac.cardiff.model.UAMetadata;

/**
 * @author philsmart
 *
 */
public class UAEntryPush {

    private Set<Entry> entries;
    private UAMetadata uaMetaData;
    private Date timeOfPush;

    public void setEntries(Set<Entry> entries) {
	this.entries = entries;
    }
    public Set<Entry> getEntries() {
	return entries;
    }

    public void setTimeOfPush(Date timeOfPush) {
	this.timeOfPush = timeOfPush;
    }
    public Date getTimeOfPush() {
	return timeOfPush;
    }
    public void setUaMetaData(UAMetadata uaMetaData) {
	this.uaMetaData = uaMetaData;
    }
    public UAMetadata getUaMetaData() {
	return uaMetaData;
    }

}
