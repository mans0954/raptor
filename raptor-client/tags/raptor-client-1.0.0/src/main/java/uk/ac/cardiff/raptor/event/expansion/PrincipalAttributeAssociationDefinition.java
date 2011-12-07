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
 * The Class PrincipalAttributeAssociationDefinition.
 * 
 * @author philsmart
 */
public class PrincipalAttributeAssociationDefinition extends BaseAttributeAssociationDefinition {

    /** Class logger. */
    private final Logger log = LoggerFactory.getLogger(ResourceCategoryAttributeAssociationDefinition.class);

    /** The name of the field the subject's principal should be extracted from. */
    private String subjectPrincipalField;

    /** The list of lookup attributes. */
    private List<AttributeLookup> lookupAttributes;

    /** The data connector used to acquire the attributes. */
    private DataConnector dataConnector;

    /** The search filter template passed to the data connector. */
    private String searchTemplate;

    /**
     * Gets the source attributes as an array.
     * 
     * @return the source attributes as array
     */
    public String[] getSourceAttributesAsArray() {
        String[] sourceAttributes = new String[lookupAttributes.size()];
        int i = 0;
        for (AttributeLookup lookupAttribute : lookupAttributes) {
            sourceAttributes[i++] = lookupAttribute.getSourceAttributeName();
        }
        return sourceAttributes;
    }

    /**
     * 
     * @see uk.ac.cardiff.raptor.event.expansion.BaseAttributeAssociationDefinition#initialise()
     */
    public void initialise() {
        dataConnector.initialise();

    }

    /**
     * Gets the internal attribute name.
     * 
     * @param attributeSourceName the attribute source name
     * @return the internal attribute name
     * @throws AttributeAssociationException the attribute association exception
     */
    public String getInternalAttributeName(final String attributeSourceName) throws AttributeAssociationException {
        for (AttributeLookup lookupAttribute : lookupAttributes) {
            if (lookupAttribute.getSourceAttributeName().equals(attributeSourceName)) {
                return lookupAttribute.getInternalAttributeName();
            }
        }
        throw new AttributeAssociationException("Source attribute " + attributeSourceName
                + " was not found in the attribute association mappings");
    }

    /**
     * Populates fields in the <code>event</code> from the values in <code>attributes</code>
     * 
     * @param attributes the attributes
     * @param event the event
     * @throws InstantiationException the instantiation exception
     * @throws IllegalAccessException the illegal access exception
     */
    private void populate(final Map<String, String> attributes, final Event event) throws InstantiationException,
            IllegalAccessException {

        Object classToPopulate = getClassToAdd().newInstance();

        for (Map.Entry<String, String> entry : attributes.entrySet()) {
            String attributeSourceName = entry.getKey();
            String attributeValue = entry.getValue();
            log.trace("source [{}], value [{}]", attributeSourceName, attributeValue);
            try {
                String internalFieldName = getInternalAttributeName(attributeSourceName);
                ReflectionHelper.setValueOnObject(internalFieldName, attributeValue, classToPopulate);
            } catch (AttributeAssociationException e) {
                log.warn("Error trying to populate internal model. {}", e.getMessage());
            }
        }

        // now attach the object where appropriate on the current <code>Event</code> object
        ReflectionHelper.attachObjectTo(classToPopulate, event);

    }

    @Override
    public boolean associate(final Event event) {

        if (!event.getClass().getCanonicalName().equals(associateWithClass)) {
            return false;
        }

        Object principalObject = ReflectionHelper.getValueFromObject(getSubjectPrincipalField(), event);
        String principal = null;
        if (principalObject instanceof String) {
            principal = (String) principalObject;
        }
        if (principal != null) {
            try {
                dataConnector.setReturnAttributes(getSourceAttributesAsArray());
                dataConnector.setSearchTemplate(searchTemplate);
                Map<String, String> attributes = dataConnector.lookup(principal);
                populate(attributes, event);
                return true;
            } catch (AttributeAssociationException e) {
                log.error("Association error for principal [{}], {}", principal, e.getMessage());
            } catch (InstantiationException e) {
                log.warn("Could not populate event [{}], {}", event, e.getMessage());
            } catch (IllegalAccessException e) {
                log.warn("Could not populate event [{}], {}", event, e.getMessage());
            }
        }
        return false;

    }

    /**
     * Sets the subject principal field.
     * 
     * @param subjectPrincipalField the subjectPrincipalField to set
     */
    public void setSubjectPrincipalField(String subjectPrincipalField) {
        this.subjectPrincipalField = subjectPrincipalField;
    }

    /**
     * Gets the subject principal field.
     * 
     * @return the subjectPrincipalField
     */
    public String getSubjectPrincipalField() {
        return subjectPrincipalField;
    }

    /**
     * Sets the lookup attributes.
     * 
     * @param lookupAttributes the lookupAttributes to set
     */
    public void setLookupAttributes(List<AttributeLookup> lookupAttributes) {
        this.lookupAttributes = lookupAttributes;
    }

    /**
     * Gets the lookup attributes.
     * 
     * @return the lookupAttributes
     */
    public List<AttributeLookup> getLookupAttributes() {
        return lookupAttributes;
    }

    /**
     * Sets the data connector.
     * 
     * @param dataConnector the dataConnector to set
     */
    public void setDataConnector(DataConnector dataConnector) {
        this.dataConnector = dataConnector;
    }

    /**
     * Gets the data connector.
     * 
     * @return the dataConnector
     */
    public DataConnector getDataConnector() {
        return dataConnector;
    }

    /**
     * Sets the class to add.
     * 
     * @param classToAdd the classToAdd to set
     */
    public void setClassToAdd(Class<?> classToAdd) {
        this.classToAdd = classToAdd;
    }

    /**
     * Gets the class to add.
     * 
     * @return the classToAdd
     */
    public Class<?> getClassToAdd() {
        return classToAdd;
    }

    /**
     * Sets the associate with class.
     * 
     * @param associateWithClass the associateWithClass to set
     */
    public void setAssociateWithClass(String associateWithClass) {
        this.associateWithClass = associateWithClass;
    }

    /**
     * Gets the associate with class.
     * 
     * @return the associateWithClass
     */
    public String getAssociateWithClass() {
        return associateWithClass;
    }

    /**
     * @param searchTemplate the searchTemplate to set
     */
    public void setSearchTemplate(String searchTemplate) {
        this.searchTemplate = searchTemplate;
    }

    /**
     * @return the searchTemplate
     */
    public String getSearchTemplate() {
        return searchTemplate;
    }

}
