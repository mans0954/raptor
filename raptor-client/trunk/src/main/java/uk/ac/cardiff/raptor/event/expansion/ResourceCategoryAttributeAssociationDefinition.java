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
import org.springframework.dao.DataAccessException;

import uk.ac.cardiff.model.event.Event;
import uk.ac.cardiff.model.resource.ResourceMetadata;
import uk.ac.cardiff.raptor.store.dao.RaptorDataConnection;

/**
 * The Class ResourceCategoryAttributeAssociationDefinition.
 * 
 * @author philsmart
 */
public class ResourceCategoryAttributeAssociationDefinition extends
        BaseAttributeAssociationDefinition {

    /** Class logger. */
    private final Logger log = LoggerFactory
            .getLogger(ResourceCategoryAttributeAssociationDefinition.class);

    /** The data connector used to acquire the attributes. */
    private RaptorDataConnection dataConnection;

    /**
     * @see uk.ac.cardiff.raptor.event.expansion.BaseAttributeAssociationDefinition#initialise()
     */
    @Override
    public void initialise() {
    }

    @Override
    public boolean associate(final Event event) {

        ResourceMetadata resourceMetadata = null;
        Object result = null;
        if (event.getResourceId() != null) {
            result =
                    dataConnection.runQueryUnique(
                            "from ResourceMetadata where resourceId=?",
                            new Object[] {event.getResourceId()});
        }
        if (result != null && result instanceof ResourceMetadata) {
            resourceMetadata = (ResourceMetadata) result;
            if (resourceMetadata.isExternal()) {
                event.setResourceIdCategory(2);
            } else if (resourceMetadata.isInternal()) {
                event.setResourceIdCategory(1);
            }
            return true;
        } else {
            // only set new resource information if has resource id.
            if (event.getResourceId() != null) {
                ResourceMetadata resourceNew = new ResourceMetadata();
                resourceNew.setExternal(true);
                resourceNew.setInternal(false);
                resourceNew.setResourceId(event.getResourceId());
                // set event to default resource category, then save this resourceId.
                event.setResourceIdCategory(2);
                try {
                    dataConnection.save(resourceNew);
                } catch (DataAccessException e) {
                    log.error("Could not save new resource metadata {}", e.getMessage());
                    return false;
                }
                return true;
            } else {
                return false;
            }
        }

    }

    /**
     * Sets the class to add.
     * 
     * @param classToAdd the classToAdd to set
     */
    @Override
    public void setClassToAdd(Class<?> classToAdd) {
        this.classToAdd = classToAdd;
    }

    /**
     * Gets the class to add.
     * 
     * @return the classToAdd
     */
    @Override
    public Class<?> getClassToAdd() {
        return classToAdd;
    }

    /**
     * Sets the associate with class.
     * 
     * @param associateWithClass the associateWithClass to set
     */
    @Override
    public void setAssociateWithClass(String associateWithClass) {
        this.associateWithClass = associateWithClass;
    }

    /**
     * Gets the associate with class.
     * 
     * @return the associateWithClass
     */
    @Override
    public String getAssociateWithClass() {
        return associateWithClass;
    }

    /**
     * Sets the data connection.
     * 
     * @param dataConnection the dataConnection to set
     */
    public void setDataConnection(RaptorDataConnection dataConnection) {
        this.dataConnection = dataConnection;
    }

    /**
     * Gets the data connection.
     * 
     * @return the dataConnection
     */
    public RaptorDataConnection getDataConnection() {
        return dataConnection;
    }

}
