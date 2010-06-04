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
	private String requestHost;

	public void setRequestHost(String requestHost) {
		this.requestHost = requestHost;
	}
	public String getRequestHost() {
		return requestHost;
	}

	public void setEventTime(DateTime eventTime) {
		this.eventTime = eventTime;
	}

	public DateTime getEventTime() {
		return eventTime;
	}
}
