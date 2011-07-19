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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import uk.ac.cardiff.model.event.Event;

/**
 * The Class AttributeAssociationDefinition.
 * 
 * @author philsmart
 */
public abstract class BaseAttributeAssociationDefinition {

    /** Class logger. */
    private final Logger log = LoggerFactory.getLogger(BaseAttributeAssociationDefinition.class);

    /** Human readable name for this definition. */
    private String definitionName;

    /** Whether to apply the attribute association. */
    private boolean enabled;

    /** The class type that these attribute definitions are attached to. */
    protected Class<?> classToAdd;

    /** The class type that this attribute association is applicable for. */
    protected String associateWithClass;

    /**
     * Associate additional attributes to the <code>event</code>
     * 
     * @param event the event
     * @return true, if successful, false otherwise
     */
    public abstract boolean associate(Event event);

    /**
     * Initialise this definition.
     */
    public abstract void initialise();

    /**
     * Sets whether this definition is enabled.
     * 
     * @param enabled the enabled to set
     */
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    /**
     * Checks if this definition is enabled.
     * 
     * @return the enabled
     */
    public boolean isEnabled() {
        return enabled;
    }

    /**
     * Sets the definition name.
     * 
     * @param definiationName the definiationName to set
     */
    public void setDefiniationName(String definiationName) {
        this.definitionName = definiationName;
    }

    /**
     * Gets the definition name.
     * 
     * @return the definiationName
     */
    public String getDefiniationName() {
        return definitionName;
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

}
