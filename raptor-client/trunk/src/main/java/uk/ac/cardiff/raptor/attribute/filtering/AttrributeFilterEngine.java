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
package uk.ac.cardiff.raptor.attribute.filtering;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.rits.cloning.Cloner;

import uk.ac.cardiff.model.event.Event;
import uk.ac.cardiff.raptor.runtimeutils.ReflectionHelper;

/**
 * @author philsmart
 * 
 */
public class AttrributeFilterEngine {

	/* class logger. */
	private final Logger log = LoggerFactory.getLogger(AttrributeFilterEngine.class);

	/**
	 * Only runs against the deny rules, and only denies basic:ANY attributes,
	 * does not deny individual values.
	 * 
	 * @param attributeFilterPolicy
	 * @param entries
	 * @return
	 */
	public List<Event> filter(AttributeFilterPolicy attributeFilterPolicy, List<Event> events) {
		log.debug("Applying attribute filter policy {} to event set", attributeFilterPolicy.getPolicyName());
		List<Event> filteredEntries = clone(events);

		for (Event entry : filteredEntries) {
			for (AttributeRule attributeRule : attributeFilterPolicy.getAttributeRules()) {
				filterAttributes(entry, attributeRule);
			}
		}

		return filteredEntries;
	}

	/**
	 * Only process deny rules on the level of basic:ANY attributes. Hence will
	 * not deny individual values
	 * 
	 * @param entry
	 * @param attributeRule
	 */
	private void filterAttributes(Event entry, AttributeRule attributeRule) {
		String attributeID = attributeRule.getAttributeID();
		if (classHasAttribute(entry, attributeID)) {
			if (attributeRule.getDenyValueRule().isEnabled()) {
				nullAttribute(entry, attributeID);
			}
		} else {

		}

	}

	private static void nullAttribute(Event event, String attributeID) {
		ReflectionHelper.nullAttribute(event, attributeID);
	}

	private static boolean classHasAttribute(Event entry, String attributeID) {
		return ReflectionHelper.classHasAttribute(entry, attributeID);
	}

	private static List<Event> clone(List<Event> events) {
		List<Event> clonedSet = new ArrayList<Event>();
		Cloner cloner = new Cloner();
		for (Event entry : events) {
			Event newEntry = cloner.deepClone(entry);
			clonedSet.add(newEntry);
		}
		return clonedSet;
	}

}
