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

import uk.ac.cardiff.model.ServiceMetadata;
import uk.ac.cardiff.model.event.Event;

public class AttrributeFilterEngine {

    /** class logger. */
    private final Logger log = LoggerFactory.getLogger(AttrributeFilterEngine.class);

    /**
     * Only runs against the deny rules, and only denies basic:ANY attributes, does not deny individual values.
     * 
     * @param attributeFilterPolicy the policy used to filter attributes from the set <code>events</code>
     * @param metadata the metadata of the service this attribute filter engine is operating within
     * @param events the list of events to filter
     * @return a cloned set of filtered events
     */
    public List<Event> filter(final AttributeFilterPolicy attributeFilterPolicy, final ServiceMetadata metadata,
            final List<Event> events) {
        log.debug("Applying attribute filter policy {} to event set", attributeFilterPolicy.getPolicyName());
        final List<Event> filteredEntries = clone(events);
        for (final Event event : filteredEntries) {
            for (final BaseAttributeRule attributeRule : attributeFilterPolicy.getAttributeRules()) {
                filterAttributes(event, attributeRule, metadata);

            }
        }
        return filteredEntries;
    }

    /**
     * Only process deny rules on the level of basic:ANY attributes. Hence will not deny individual values
     * 
     * @param event the events whose attributes need filtering
     * @param attributeRule the attribute rule used to filter these events
     * @param metadata the service metadata passed to the attribute rule for use.
     */
    private void filterAttributes(final Event event, final BaseAttributeRule attributeRule,
            final ServiceMetadata metadata) {
        try {
            if (attributeRule.shouldApply(event.getClass())) {
                attributeRule.filterAttribute(event, metadata);
            }
        } catch (final AttributeFilterException e) {
            log.error("Could not filter attribute", e);
        }
    }

    /**
     * Clones the list <code>events</code>
     * 
     * @param events the events to clone
     * @return a deep cloned set of <code>Event</code>s
     */
    private final List<Event> clone(final List<Event> events) {
        final List<Event> clonedSet = new ArrayList<Event>();
        for (final Event event : events) {
            clonedSet.add(event.copy());
        }
        log.trace("The event set of {} events has been cloned into the event set of {} events", events.size(),
                clonedSet.size());
        return clonedSet;
    }

}
