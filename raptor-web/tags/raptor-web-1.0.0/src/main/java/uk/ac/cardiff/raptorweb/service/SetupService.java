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
package uk.ac.cardiff.raptorweb.service;

import java.util.List;

import uk.ac.cardiff.model.wsmodel.Capabilities;
import uk.ac.cardiff.raptorweb.model.MUAEntry;
import uk.ac.cardiff.raptorweb.model.SetupModel;
import uk.ac.cardiff.raptorweb.model.WebSession;

/**
 * @author philsmart
 *
 */
public interface SetupService {

	public List getStatisticalServices();

	public void setAttachedEndpoint(WebSession websession);

	/**
	 * performs the operation in place on the SetupModel
	 * @param model
	 */
	public void getCapabilities(WebSession websession);

	public void deleteAllEntriesFromAttachedMUA(WebSession websession);

	/**
	 * Check if there is at least one attached MUA
	 * @return
	 */
	public boolean getHasAttached();

	public Capabilities getAttachedCapabilities();

	public MUAEntry getCurrentlyAttached();
	
	      
	/**
	 * Upload all files currently stored in <code>websession.setupmodel.fileUpload</code> class.
	 * 
	 * @param websession
	 */
        public void batchUpload(WebSession websession);
        
        public void sendResourceClassification();

}
