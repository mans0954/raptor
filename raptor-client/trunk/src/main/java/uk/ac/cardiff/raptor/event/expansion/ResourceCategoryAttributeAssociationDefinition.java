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


import org.hibernate.exception.DataException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import uk.ac.cardiff.model.event.Event;
import uk.ac.cardiff.model.resource.ResourceMetadata;
import uk.ac.cardiff.raptor.store.dao.RaptorDataConnection;

/**
 * @author philsmart
 *
 */
public class ResourceCategoryAttributeAssociationDefinition extends AttributeAssociationDefinition{


    /** Class logger. */
    private final Logger log = LoggerFactory.getLogger(ResourceCategoryAttributeAssociationDefinition.class);

    /** The data connector used to acquire the attributes*/
    private RaptorDataConnection dataConnection;


    public void initialise(){
    }



    /**
     * @param event
     */
    public boolean associate(Event event) {

       ResourceMetadata resourceMetadata = null;
       Object result =  dataConnection.runQueryUnique("from ResourceMetadata where resourceId=?", new Object[]{event.getResourceId()});
       if (result !=null && result instanceof ResourceMetadata){
           resourceMetadata = (ResourceMetadata) result;
           if (resourceMetadata.isExternal()){
               event.setResourceIdCategory(2);
           }
           else if (resourceMetadata.isInternal()){
               event.setResourceIdCategory(1);
           }
           return true;
       }
       else{
           ResourceMetadata resourceNew = new ResourceMetadata();
           resourceNew.setExternal(true);
           resourceNew.setInternal(false);
           resourceNew.setResourceId(event.getResourceId());
           event.setResourceIdCategory(2);
           try{
               dataConnection.save(resourceNew);
           }
           catch(DataException e){
               log.error("Could not save new resource metadata {}",e.getMessage());
               return false;
           }
           return true;
       }

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



    /**
     * @param dataConnection the dataConnection to set
     */
    public void setDataConnection(RaptorDataConnection dataConnection) {
        this.dataConnection = dataConnection;
    }



    /**
     * @return the dataConnection
     */
    public RaptorDataConnection getDataConnection() {
        return dataConnection;
    }




}
