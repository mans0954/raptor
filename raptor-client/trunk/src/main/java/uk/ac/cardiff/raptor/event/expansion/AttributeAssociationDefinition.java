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
 * @author philsmart
 *
 */
public abstract class AttributeAssociationDefinition {

    /** Class logger. */
    private final Logger log = LoggerFactory.getLogger(AttributeAssociationDefinition.class);

    /** Human readable name for this definition*/
    private String definiationName;

    /** Whether to apply the attribute association*/
    private boolean enabled;

    /** The class type that these attribute definitions are attached to*/
    protected Class<?> classToAdd;

    /** The class type that this attribute association is applicable for */
    protected String associateWithClass;

    public abstract boolean associate(Event event);

    public abstract void initialise();


    /**
     * @param enabled the enabled to set
     */
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }



    /**
     * @return the enabled
     */
    public boolean isEnabled() {
        return enabled;
    }


    /**
     * @param definiationName the definiationName to set
     */
    public void setDefiniationName(String definiationName) {
        this.definiationName = definiationName;
    }



    /**
     * @return the definiationName
     */
    public String getDefiniationName() {
        return definiationName;
    }

    /**
     * @param classToAdd the classToAdd to set
     */
    public void setClassToAdd(Class<?> classToAdd) {
        this.classToAdd = classToAdd;
    }

    /**
     * @return the classToAdd
     */
    public Class<?> getClassToAdd() {
        return classToAdd;
    }

    /**
     * @param associateWithClass the associateWithClass to set
     */
    public void setAssociateWithClass(String associateWithClass) {
        this.associateWithClass = associateWithClass;
    }

    /**
     * @return the associateWithClass
     */
    public String getAssociateWithClass() {
        return associateWithClass;
    }



}
