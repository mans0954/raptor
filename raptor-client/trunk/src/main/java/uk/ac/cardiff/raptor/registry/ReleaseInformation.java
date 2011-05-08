package uk.ac.cardiff.raptor.registry;

import java.util.Set;

import org.joda.time.DateTime;

import uk.ac.cardiff.model.event.Event;

public class ReleaseInformation {
	
	/** The date and time of the last entry sent to this client */
	private DateTime lastReleasedEventTime;
	
	/**
	 * Stores the set of latest unique entries. That is, those with the latest
	 * and same DateTime, but different state (attribute values).
	 */
	private Set<Event> latestEqualEntries;

	/**
	 * @param lastPublishedEntryTime the lastPublishedEntryTime to set
	 */
	public void setLastReleasedEventTime(DateTime lastReleasedEventTime) {
		this.lastReleasedEventTime = lastReleasedEventTime;
	}

	/**
	 * @return the lastPublishedEntryTime if its not null, otherwise a new datetime set
	 * to EPOCH is returned (1970-01-01T00:00)
	 */
	public DateTime getLastReleasedEventTime() {
		if (lastReleasedEventTime==null)
			return new DateTime(0);
		return lastReleasedEventTime;
	}

	/**
	 * @param latestEqualEntries the latestEqualEntries to set
	 */
	public void setLatestEqualEntries(Set<Event> latestEqualEntries) {
		this.latestEqualEntries = latestEqualEntries;
	}

	/**
	 * @return the latestEqualEntries
	 */
	public Set<Event> getLatestEqualEntries() {
		return latestEqualEntries;
	}

}
