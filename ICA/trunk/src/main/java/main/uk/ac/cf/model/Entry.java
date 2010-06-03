/**
 *
 */
package main.uk.ac.cf.model;

import org.joda.time.DateTime;

/**
 * @author philsmart
 *
 */
public class Entry {
	private DateTime eventTime;

	public void setEventTime(DateTime eventTime) {
		this.eventTime = eventTime;
	}

	public DateTime getEventTime() {
		return eventTime;
	}
}
