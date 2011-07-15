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
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;

import uk.ac.cardiff.model.event.Event;
import uk.ac.cardiff.model.resource.ResourceMetadata;
import uk.ac.cardiff.raptor.store.EventHandler;

/**
 *
 * @author philsmart
 *
 */
public class ResourceClassificationBackgroundService implements SaveAndApplyResourceClassificationCallbackInterface{

    /** Class logger. */
    private final Logger log = LoggerFactory.getLogger(ResourceClassificationBackgroundService.class);

    /** The MUA's data connection */
    private EventHandler entryHandler;
    
    /** The ID of the currently executing service */
    private int currentExecutionId;
    

    public ResourceClassificationBackgroundService(EventHandler entryHandler){
    	this.entryHandler = entryHandler;        
    }
    

	public void callback(Object result) {
		 log.debug("Resource Classification task completed {}, for transaction id [{}]",result, currentExecutionId);
		
	}



	public void saveResourceMetadataAndApplyAsync(List<ResourceMetadata> resourceMetadata) {
		currentExecutionId = (int)(Math.random()*100000);
		log.info("Resource classification thread started, for transaction id [{}]",currentExecutionId);
		SaveAndApplyResourceClassificationTask storeEntryTask = new SaveAndApplyResourceClassificationTask(entryHandler,resourceMetadata,this);
        ExecutorService es = Executors.newSingleThreadExecutor();
        es.submit(storeEntryTask);
        es.shutdown();
		
	}





}
