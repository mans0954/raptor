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
package uk.ac.cardiff.raptor.remoting.policy;

import java.util.List;

import uk.ac.cardiff.model.event.Event;

public class EntryNoPushPolicy extends PushPolicy{
	
	/** The threshold on the number of events needed before release*/
	private int pushOnOrAfterNoEntries;
	
	
	public boolean evaluatePolicy(List<Event> events) {
		if (pushOnOrAfterNoEntries <= events.size()) 
			return true;
		return false;
	}

    public void setPushOnOrAfterNoEntries(int pushOnOrAfterNoEntries) {
	this.pushOnOrAfterNoEntries = pushOnOrAfterNoEntries;
    }

    public int getPushOnOrAfterNoEntries() {
	return pushOnOrAfterNoEntries;
    }

}
