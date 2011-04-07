package uk.ac.cardiff.raptor.remoting.policy;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import uk.ac.cardiff.model.event.Event;
import uk.ac.cardiff.raptor.registry.EventReleaseEngine;

/**
 * This class combines both the number of events needed before release (
 * <code>pushOnOrAfterNoEntries</code>) with a duration to wait before release.
 * In this way, if the number of events threshold is met before the duration
 * elapses, the <code>lastReleasedTime</code> is updated. In effect, the
 * <code>pushInterval</code> is only effective if the number of entries does not
 * suppress the threshold in the given time interval.
 * 
 * @author philsmart
 * 
 */
public class EntryNoElapsedTimePushPolicy extends PushPolicy {

	/** Class logger */
	private final Logger log = LoggerFactory.getLogger(EntryNoElapsedTimePushPolicy.class);

	/** The time, in milliseconds, that this policy was last evaluated */
	private long lastReleasedTime;

	/** How long, in milliseconds, should elapse before release */
	private long pushInterval;

	/** The threshold on the number of events needed before release */
	private int pushOnOrAfterNoEntries;

	/**
	 * Default constructor, which sets the last evaluated time on initialisation
	 */
	public EntryNoElapsedTimePushPolicy() {
		super();
		lastReleasedTime = System.currentTimeMillis();
	}

	public boolean evaluatePolicy(List<Event> events) {
		long currentTime = System.currentTimeMillis();
		if (pushOnOrAfterNoEntries < events.size()) {
			lastReleasedTime = currentTime;
			return true;
		} else {			
			long difference = currentTime - lastReleasedTime;
			//log.debug("ElapsedTime difference {}, pushInterval {}", difference, pushInterval);
			if (difference >= getPushInterval()) {
				lastReleasedTime = currentTime;
				return true;
			}
		}
		return false;
	}

	public void setPushInterval(long pushInterval) {
		this.pushInterval = pushInterval;
	}

	public long getPushInterval() {
		return pushInterval;
	}

	public void setPushOnOrAfterNoEntries(int pushOnOrAfterNoEntries) {
		this.pushOnOrAfterNoEntries = pushOnOrAfterNoEntries;
	}

	public int getPushOnOrAfterNoEntries() {
		return pushOnOrAfterNoEntries;
	}

}
