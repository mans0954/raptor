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
