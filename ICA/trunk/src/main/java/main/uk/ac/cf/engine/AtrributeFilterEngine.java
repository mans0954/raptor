/**
 *
 */
package main.uk.ac.cf.engine;

import java.util.LinkedHashSet;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import runtimeutils.ReflectionHelper;

import uk.ac.cardiff.model.Entry;

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
