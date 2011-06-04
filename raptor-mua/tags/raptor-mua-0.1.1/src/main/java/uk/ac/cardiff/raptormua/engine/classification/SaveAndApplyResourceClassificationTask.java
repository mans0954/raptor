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
package uk.ac.cardiff.raptormua.engine.classification;

import java.util.List;
import java.util.concurrent.Callable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import uk.ac.cardiff.model.event.AuthenticationEvent;
import uk.ac.cardiff.model.event.Event;
import uk.ac.cardiff.model.resource.ResourceMetadata;
import uk.ac.cardiff.raptor.event.expansion.AttributeAssociationEngine;
import uk.ac.cardiff.raptor.event.expansion.connector.AttributeAssociationException;
import uk.ac.cardiff.raptor.store.EntryHandler;
import uk.ac.cardiff.raptor.store.dao.StorageException;



/**
 * @author philsmart
 *
 */
public class SaveAndApplyResourceClassificationTask implements Callable<Boolean>{

	/** class logger */
	private final Logger log = LoggerFactory.getLogger(SaveAndApplyResourceClassificationTask.class);

	/** The entry handler used to store entries (e.g. events)*/
    private EntryHandler entryHandler;

    /** The callback interface that is called once the <code>call</code> method has completed*/
    private SaveAndApplyResourceClassificationCallbackInterface storeCallback;

    /** The list of resource metadata used to attribute resource classifications to stored events*/
    List<ResourceMetadata> resourceMetadata;

    public SaveAndApplyResourceClassificationTask(EntryHandler entryHandler,List<ResourceMetadata> resourceMetadata, SaveAndApplyResourceClassificationCallbackInterface callback){
        this.storeCallback = callback;
        this.entryHandler = entryHandler;
        this.resourceMetadata = resourceMetadata;

    }

    public Boolean call() throws Exception {
    	try{
    		 log.debug("Saving resource metadata");
    		 entryHandler.saveAll(resourceMetadata);  
    		 log.debug("Finsihed saving resource metadata");
    		 
    		 runUpdate();

    	}
    	catch(StorageException e){
    	    log.error("Failed to store events asynchronously {}",e.getMessage(),e);
    	    storeCallback.callback(new Boolean("false"));
    	    return false;
    	}
        storeCallback.callback(new Boolean("true"));
        return true;
    }
    
    
    private void runUpdate(){
    	for (ResourceMetadata resource : resourceMetadata){
    		try {
				//entryHandler.update("update Event set resourceIdCategory =? where resourceId =?",new Object[]{resource.getResourceCategory(),resource.getResourceId()});
    		} catch (Exception e) {
				log.error("Could not update resource category for resource Id {}",resource.getResourceId(),e);
			}
    	}
    }


}
