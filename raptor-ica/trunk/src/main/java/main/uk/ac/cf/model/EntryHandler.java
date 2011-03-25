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
import java.util.List;
import java.util.Set;

import org.joda.time.DateTime;

import uk.ac.cardiff.model.Event;

/**
 * @author philsmart
 *
 */
public interface EntryHandler {


	public void addEntries(Set<Event> entries);

	public void addEntry(Event entry);

	public boolean isNewerOrEqual(Event authE);

	public Set<Event> getEntries();

	public void removeAllEntries();

	public void endTransaction();

	public boolean isEqual(Event authE);

	public boolean isAfter(Event authE);

	public void setLatestEntryTime(DateTime latestEntryTime);

	public DateTime getLatestEntryTime();

	public void initialise();


}
