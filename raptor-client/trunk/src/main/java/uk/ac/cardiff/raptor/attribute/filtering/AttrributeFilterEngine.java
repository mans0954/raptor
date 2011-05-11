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
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.rits.cloning.Cloner;
import com.rits.cloning.CloningException;

import uk.ac.cardiff.model.ServiceMetadata;
import uk.ac.cardiff.model.event.Event;

/**
 * @author philsmart
 *
 */
public class AttrributeFilterEngine {

	/** class logger. */
	private final Logger log = LoggerFactory.getLogger(AttrributeFilterEngine.class);

	/**
	 * Only runs against the deny rules, and only denies basic:ANY attributes,
	 * does not deny individual values.
	 *
	 * @param attributeFilterPolicy
	 * @param entries
	 * @return
	 */
	public List<Event> filter(AttributeFilterPolicy attributeFilterPolicy, ServiceMetadata metadata, List<Event> events) {
		log.debug("Applying attribute filter policy {} to event set", attributeFilterPolicy.getPolicyName());
		List<Event> filteredEntries = clone(events);
		for (Event entry : filteredEntries) {
			for (AttributeRule attributeRule : attributeFilterPolicy.getAttributeRules()) {
				filterAttributes(entry, attributeRule, metadata);
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
	 * @param metadata
	 */
	private void filterAttributes(Event event, AttributeRule attributeRule, ServiceMetadata metadata) {
	    try{
		if (attributeRule.getDenyValueRule().isEnabled()) {
			attributeRule.filterAttribute(event, metadata);
		}
	    }
	    catch(AttributeFilterException e){
	        log.error("Could not filter attribute",e);
	    }
	}


	private  List<Event> clone(List<Event> events) {

		List<Event> clonedSet = new ArrayList<Event>();
		
		log.debug("Events cloned");
		for (Event event : events) {			
			clonedSet.add(event.newInstance());
		}
		return clonedSet;
	}

}
