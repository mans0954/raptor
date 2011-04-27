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
    private AttributeAssociationDefinition attributeAssociationDefinition;

    private DataConnector dataConnector;

    /**
     * Default Constructor
     */
    public AttributeAssociationEngine(){

    }


    /**
     * Gets associated attributes for the given ...
     */
    public void associateAttributes(List<Event> events) throws AttributeAssociationException{
        log.info("Has Attribute Definition [{}]",attributeAssociationDefinition!=null);
        if (attributeAssociationDefinition==null){
            throw new AttributeAssociationException("Attribute association not specified");
        }

        log.info("Attribute Association started for principal field [{}]",attributeAssociationDefinition.getSubjectPrincipalField());

        int attached=0;
        int noPrincipal=0;
        for (Event event : events){
            Object principalObject = ReflectionHelper.getValueFromObject(attributeAssociationDefinition.getSubjectPrincipalField(), event);
            String principal = null;
            if (principalObject instanceof String){
                principal = (String)principalObject;
            }
            if (principal!=null){
                try {
                    dataConnector.setReturnAttributes(attributeAssociationDefinition.getSourceAttributesAsArray());
                    Map<String, String> attributes = dataConnector.lookup(principal);
                    populate(attributes,event);
                    attached++;
                } catch (AttributeAssociationException e) {
                    log.error("Association error for principal [{}]",principal,e);
                } catch (InstantiationException e) {
                    log.warn("Could not populate event [{}], {}",event,e.getMessage());
                } catch (IllegalAccessException e) {
                    log.warn("Could not populate event [{}], {}",event,e.getMessage());
                }
            }
            else{
                noPrincipal++;
            }
        }
        log.info("Associated information to {} events, where {} events did not have a valid principal",attached,noPrincipal);
    }

    private void populate(Map<String,String> attributes, Event event) throws InstantiationException, IllegalAccessException{

        Object classToPopulate = attributeAssociationDefinition.getInternalModelClass().newInstance();

        for (Map.Entry<String,String> entry : attributes.entrySet()){
            String attributeSourceName = entry.getKey();
            String attributeValue = entry.getValue();
            log.trace("source [{}], value [{}]",attributeSourceName,attributeValue);
            try{
                String internalFieldName = attributeAssociationDefinition.getInternalAttributeName(attributeSourceName);
                ReflectionHelper.setValueOnObject(internalFieldName, attributeValue, classToPopulate);
            }
            catch(AttributeAssociationException e){
                log.warn("Error trying to populate internal model. {}",e.getMessage());
            }
        }

        //now attach the object where approppriate on the current <code>Event</code> object
        ReflectionHelper.attachObjectTo(classToPopulate,event);
        
        log.debug("{}",event);
    }

    /**
     * @param attributeAssociationDefinition the attributeAssociationDefinition to set
     */
    public void setAttributeAssociationDefinition(AttributeAssociationDefinition attributeAssociationDefinition) {
        this.attributeAssociationDefinition = attributeAssociationDefinition;
    }

    /**
     * @return the attributeAssociationDefinition
     */
    public AttributeAssociationDefinition getAttributeAssociationDefinition() {
        return attributeAssociationDefinition;
    }


    /**
     * @param dataConnector the dataConnector to set
     */
    public void setDataConnector(DataConnector dataConnector) {
        this.dataConnector = dataConnector;
        dataConnector.initialise();
    }


    /**
     * @return the dataConnector
     */
    public DataConnector getDataConnector() {
        return dataConnector;
    }



}
