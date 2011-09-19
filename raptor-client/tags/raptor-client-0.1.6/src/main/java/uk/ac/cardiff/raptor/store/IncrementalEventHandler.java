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
 *
 *
 * @author philsmart
 * {@literal} this class manages interaction with the internal model of the ICA
 *
 */

package uk.ac.cardiff.raptor.store;

import java.util.List;
import java.util.Set;

import org.joda.time.DateTime;

import uk.ac.cardiff.model.event.Event;
import uk.ac.cardiff.raptor.store.dao.StorageException;


public interface IncrementalEventHandler {

	/**
	 *
	 * @param entries
	 */
	public void addEntries(List<Event> entries) throws StorageException;

	/**
	 *
	 * @param event
	 */
	public boolean addEntry(Event event);

	/**
	 *
	 * @return
	 */
	public DateTime getLatestEntryTime();

	/**
	 *
	 * @return
	 */
	public List<Event> getEntries();

	/**
	 *
	 * @param entries
	 */
	public void setEntries(Set<Event> entries);

	/**
	 *
	 */
	public void removeAllEntries();

	/**
	 *
	 */
	public void initialise();

	/**
	 *
	 * @return
	 */
	public int getNumberOfEntries();

	public void removeEventsBefore(DateTime earliestReleaseTime, Set<Integer> latestEqualEntries);

	/**
	 * Rests this <code>IncrementalEntryHandler</code> back to its initial state
	 *
	 */
	public void reset();




}
