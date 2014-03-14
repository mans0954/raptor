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

package uk.ac.cardiff.raptor.event.expansion;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import uk.ac.cardiff.model.event.Event;
import uk.ac.cardiff.raptor.event.expansion.connector.AttributeAssociationException;

/**
 * @author philsmart
 * 
 */
public final class AttributeAssociationEngine {

    /** Class logger. */
    private final Logger log = LoggerFactory.getLogger(AttributeAssociationEngine.class);

    /** Defines which attributes to add, and what principal to attach to. */
    private List<BaseAttributeAssociationDefinition> attributeAssociationDefinitions;

    /**
     * Default Constructor
     */
    public AttributeAssociationEngine() {

    }

    /**
     * Gets associated attributes for the given ...
     */
    public void associateAttributes(final List<Event> events) throws AttributeAssociationException {

        for (BaseAttributeAssociationDefinition attributeAssociationDefinition : attributeAssociationDefinitions) {
            log.info("Attribute Association Definition [{}], is enabled [{}]",
                    attributeAssociationDefinition.getDefiniationName(), attributeAssociationDefinition.isEnabled());

            if (!attributeAssociationDefinition.isEnabled()) {
                continue;
            }

            int attached = 0;
            int failedToAssociate = 0;
            int current = 0;
            final int noOfEvents = events.size();
            for (Event event : events) {
                printProgressPosition(current, noOfEvents);
                boolean associated = attributeAssociationDefinition.associate(event);
                if (associated) {
                    attached++;
                } else {
                    failedToAssociate++;
                }
                current++;
            }
            log.info(
                    "Attribute Association Definition {} finished, associated information to {} events, where {} events had no attributes associated",
                    new Object[] {attributeAssociationDefinition.getDefiniationName(), attached, failedToAssociate});
        }

    }

    /**
     * Prints, as a percentage of the total, the event currently being processed.
     * 
     * @param lineCount
     * @param totalNoLines
     */
    private void printProgressPosition(final int lineCount, final int totalNoLines) {
        double linePercentage = (((double) lineCount / (double) totalNoLines) * 100);
        if (linePercentage % 25 >= 0 && linePercentage % 25 <= 0.003)
            log.debug("Attribute Association, Complete {}%", linePercentage);
    }

    /**
     * Also initialises the definitions
     * 
     * @param attributeAssociationDefinitions the attributeAssociationDefinitions to set
     */
    public void setAttributeAssociationDefinitions(
            final List<BaseAttributeAssociationDefinition> attributeAssociationDefinitions) {
        this.attributeAssociationDefinitions = attributeAssociationDefinitions;
        for (BaseAttributeAssociationDefinition definition : attributeAssociationDefinitions) {
            definition.initialise();
        }
    }

    /**
     * @return the attributeAssociationDefinitions
     */
    public List<BaseAttributeAssociationDefinition> getAttributeAssociationDefinitions() {
        return attributeAssociationDefinitions;
    }

}
