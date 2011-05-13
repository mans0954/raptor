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
import java.util.Map;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import uk.ac.cardiff.model.event.Event;
import uk.ac.cardiff.raptor.event.expansion.connector.AttributeAssociationException;
import uk.ac.cardiff.raptor.event.expansion.connector.DataConnector;
import uk.ac.cardiff.raptor.runtimeutils.ReflectionHelper;

/**
 * @author philsmart
 *
 */
public class AttributeAssociationEngine {

    /** Class logger. */
    private final Logger log = LoggerFactory.getLogger(AttributeAssociationEngine.class);

    /** Defines which attributes to add, and what principal to attach to*/
    private List<AttributeAssociationDefinition> attributeAssociationDefinitions;

    /**
     * Default Constructor
     */
    public AttributeAssociationEngine(){

    }

    /**
     * Gets associated attributes for the given ...
     */
    public void associateAttributes(List<Event> events) throws AttributeAssociationException{
        for (AttributeAssociationDefinition attributeAssociationDefinition: attributeAssociationDefinitions){
            log.info("Attribute Association Definition [{}], is enabled [{}]",attributeAssociationDefinition.getDefiniationName(), attributeAssociationDefinition.isEnabled());

            if (!attributeAssociationDefinition.isEnabled()){
                continue;
            }

            int attached=0;
            int noPrincipal=0;
            int current=0;
            final int noOfEvents = events.size();
            for (Event event : events){
                printProgressPosition(current, noOfEvents);
                boolean associated = attributeAssociationDefinition.associate(event);
                if (associated){
                    attached++;
                }
                else{
                    noPrincipal++;
                }
                current++;
            }
            log.info("Attribute Association Definition {} finished, associated information to {} events, where {} events where not of a valid class type or did not have the correct principal",
                    new Object[]{attributeAssociationDefinition.getDefiniationName(),attached,noPrincipal});
        }

    }


    /**
     * Prints, as a percentage of the total, the event currently being processed.
     *
     * @param lineCount
     * @param totalNoLines
     */
    private void printProgressPosition(int lineCount, int totalNoLines) {
            double linePercentage = (((double) lineCount / (double) totalNoLines) * 100);
            if (linePercentage % 25 >= 0 && linePercentage % 25 <= 0.003)
                    log.debug("Attribute Association, Complete {}%", linePercentage);
    }

    /**
     * Also initialises the definitions
     *
     * @param attributeAssociationDefinitions the attributeAssociationDefinitions to set
     */
    public void setAttributeAssociationDefinitions(List<AttributeAssociationDefinition> attributeAssociationDefinitions) {
        this.attributeAssociationDefinitions = attributeAssociationDefinitions;
        for (AttributeAssociationDefinition definition : attributeAssociationDefinitions){
              definition.initialise();
        }
    }

    /**
     * @return the attributeAssociationDefinitions
     */
    public List<AttributeAssociationDefinition> getAttributeAssociationDefinitions() {
        return attributeAssociationDefinitions;
    }




}
