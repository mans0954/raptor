/**
 *
 */
package uk.ac.cardiff.model.wsmodel;

import java.util.Date;
import java.util.Set;

import uk.ac.cardiff.model.Entry;
import uk.ac.cardiff.model.ICAMetadata;

/**
 * @author philsmart
 *
 */
public class ICAEntryPush {

    private Set<Entry> entries;
    private ICAMetadata icaMetaData;
    private Date timeOfPush;

    public void setEntries(Set<Entry> entries) {
	this.entries = entries;
    }
    public Set<Entry> getEntries() {
	return entries;
    }
    public void setIcaMetaData(ICAMetadata icaMetaData) {
	this.icaMetaData = icaMetaData;
    }
    public ICAMetadata getIcaMetaData() {
	return icaMetaData;
    }
    public void setTimeOfPush(Date timeOfPush) {
	this.timeOfPush = timeOfPush;
    }
    public Date getTimeOfPush() {
	return timeOfPush;
    }

}
