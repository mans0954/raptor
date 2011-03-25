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
package main.uk.ac.cf.engine;

import java.util.LinkedHashSet;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import runtimeutils.ReflectionHelper;

import uk.ac.cardiff.model.Event;

import main.uk.ac.cf.model.AttributeFilterPolicy;
import main.uk.ac.cf.model.AttributeRule;

import com.rits.cloning.Cloner;

/**
 * @author philsmart
 *
 */
public class AtrributeFilterEngine {

    /* class logger. */
    static Logger log = LoggerFactory.getLogger(ReflectionHelper.class);


    /**
     * Only runs against the deny rules, and only denies basic:ANY attributes, does not
     * deny individual values.
     *
     * @param attributeFilterPolicy
     * @param entries
     * @return
     */
    public static Set<Event> filter(AttributeFilterPolicy attributeFilterPolicy, Set<Event> entries){
	log.debug("Applying attribute filter policy {} to entry set",attributeFilterPolicy.getPolicyName());
	Set<Event> filteredEntries = clone(entries);

	for (Event entry: filteredEntries){
	    for (AttributeRule attributeRule : attributeFilterPolicy.getAttributeRules()){
		filterAttributes(entry, attributeRule);
	    }
	}


	return filteredEntries;
    }

    /**
     * Only process deny rules on the level of basic:ANY attributes. Hence will not deny individual values
     * @param entry
     * @param attributeRule
     */
    private static void filterAttributes(Event entry, AttributeRule attributeRule){
	String attributeID = attributeRule.getAttributeID();
	if (classHasAttribute(entry, attributeID)){
	    if (attributeRule.getDenyValueRule().isEnabled()){
		nullAttribute(entry, attributeID);
	    }
	}
	else{

	}

    }

    private static void nullAttribute(Event entry, String attributeID){
	ReflectionHelper.nullAttribute(entry, attributeID);
    }

    private static boolean classHasAttribute(Event entry, String attributeID){
	return ReflectionHelper.classHasAttribute(entry, attributeID);
    }

    private static Set<Event> clone(Set<Event> entries){
	Set<Event> clonedSet = new LinkedHashSet<Event>();
	Cloner cloner = new Cloner();
	for (Event entry: entries){
	    Event newEntry = cloner.deepClone(entry);
	    clonedSet.add(newEntry);
	}
	return clonedSet;
    }



}
