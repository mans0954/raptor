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
package uk.ac.cardiff.raptor.raptorica.engine;

import java.util.LinkedHashSet;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import uk.ac.cardiff.raptor.raptorica.runtimeutils.ReflectionHelper;

import uk.ac.cardiff.model.Entry;

import uk.ac.cardiff.raptor.raptorica.model.AttributeFilterPolicy;
import uk.ac.cardiff.raptor.raptorica.model.AttributeRule;

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
    public static Set<Entry> filter(AttributeFilterPolicy attributeFilterPolicy, Set<Entry> entries){
	log.debug("Applying attribute filter policy {} to entry set",attributeFilterPolicy.getPolicyName());
	Set<Entry> filteredEntries = clone(entries);

	for (Entry entry: filteredEntries){
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
    private static void filterAttributes(Entry entry, AttributeRule attributeRule){
	String attributeID = attributeRule.getAttributeID();
	if (classHasAttribute(entry, attributeID)){
	    if (attributeRule.getDenyValueRule().isEnabled()){
		nullAttribute(entry, attributeID);
	    }
	}
	else{

	}

    }

    private static void nullAttribute(Entry entry, String attributeID){
	ReflectionHelper.nullAttribute(entry, attributeID);
    }

    private static boolean classHasAttribute(Entry entry, String attributeID){
	return ReflectionHelper.classHasAttribute(entry, attributeID);
    }

    private static Set<Entry> clone(Set<Entry> entries){
	Set<Entry> clonedSet = new LinkedHashSet<Entry>();
	Cloner cloner = new Cloner();
	for (Entry entry: entries){
	    Entry newEntry = cloner.deepClone(entry);
	    clonedSet.add(newEntry);
	}
	return clonedSet;
    }



}
