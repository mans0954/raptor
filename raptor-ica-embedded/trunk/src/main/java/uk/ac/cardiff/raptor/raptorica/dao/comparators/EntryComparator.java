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
package uk.ac.cardiff.raptor.raptorica.dao.comparators;

import java.util.Comparator;

import uk.ac.cardiff.model.Event;

/**
 * Not enough to compare on in Entry, hence not used.
 *
 * @author philsmart
 *
 */
public class EntryComparator implements Comparator<Event> {

 
    public int compare(Event entry1, Event entry2) {
	boolean requestHostEqual = entry1.getRequestHost().equals(entry2.getRequestHost());
	boolean serverHostEqual = entry1.getServerHost().equals(entry2.getServerHost());
	return 0;
    }





}
