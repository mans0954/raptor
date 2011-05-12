/**
 * Copyright (C) 2010 Cardiff University, Wales <smartp@cf.ac.uk>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package uk.ac.cardiff.raptor.registry;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import uk.ac.cardiff.model.event.Event;
import uk.ac.cardiff.raptor.runtimeutils.ReflectionHelper;

public class ReleaseInformation {

    /** Class logger */
    private final Logger log = LoggerFactory.getLogger(ReleaseInformation.class);

    /** Unique identifier for the persistance layer*/
    private long persistantId;

	/** The date and time of the last entry sent to this client */
	private DateTime lastReleasedEventTime;

	/**
	 * Stores a set of the latest equal entries hashcodes. That is, those with the latest
	 * and same DateTime, but different state (attribute values).
	 */
	private Set<Integer> latestEqualEntries;

	/**
	 * This field is the primary key of this releaseInformation instance
	 * that is used to store and retrieve this object.
	 */
	private String serviceEndpoint;



	/**
	 * Default constructor. Instantiate <code>latestEqualEntries</code>
	 */
	public ReleaseInformation(){
	    latestEqualEntries = new HashSet<Integer>();

	}

    /**
     * After a release has been performed, this method is called with the events that
     * were released. The <code>lastReleasedEventTime</code> and <code>latestEqualEntries</code> are then set
     * accordingly. The latest events are selected chronologically by the <code>eventTime</code> field of
     * each <code>releasedEvents</code>.
     *
     * @param latestEvent
     */
    public void releasePerformed(List<Event> releasedEvents) {

        for (Event event : releasedEvents){
                if (lastReleasedEventTime==null)
                    lastReleasedEventTime=event.getEventTime();
                else{
                        if (event.getEventTime().isAfter(lastReleasedEventTime)){
                                lastReleasedEventTime = event.getEventTime();
                                latestEqualEntries.clear();
                                latestEqualEntries.add(event.getEventId());
                        }
                        else if (event.getEventTime().isEqual(lastReleasedEventTime)){
                            latestEqualEntries.add(event.getEventId());
                        }
                }
        }


    }


    /**
     * Returns true if the event is either:
     * 1. Chronologically after the lastReleasedEventTime
     * 2. Is equal to the lastReleasedEventTime but is not in the set <code>latestEqualEntries</code>
     *
     * In effect, the event can be released if it occurred after all other events that have already
     * been released to this endpoint, or occurred at the same time but was not an event that was
     * already sent.
     *
     * @param event the event to check for release
     * @return true if the event has not been released before, false otherwise
     */
    public boolean hasNotBeenReleased(Event event) {
        if (event.getEventTime().isAfter(getLastReleasedEventTime()))
            return true;

        else if (event.getEventTime().isEqual(getLastReleasedEventTime())){
            return !latestEqualEntries.contains(event.getEventId());
        }

        return false;
    }


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
    public void setLatestEqualEntries(Set<Integer> latestEqualEntries) {
            this.latestEqualEntries = latestEqualEntries;
    }

    /**
     * @return the latestEqualEntries
     */
    public Set<Integer> getLatestEqualEntries() {
            return latestEqualEntries;
    }

	/**
	 * @param serviceEndpoint the serviceEndpoint to set
	 */
	public void setServiceEndpoint(String serviceEndpoint) {
		this.serviceEndpoint = serviceEndpoint;
	}

	/**
	 * @return the serviceEndpoint
	 */
	public String getServiceEndpoint() {
		return serviceEndpoint;
	}

	/**
	 * @param persistantId the persistantId to set
	 */
	public void setPersistantId(long persistantId) {
		this.persistantId = persistantId;
	}

	/**
	 * @return the persistantId
	 */
	public long getPersistantId() {
		return persistantId;
	}

}
