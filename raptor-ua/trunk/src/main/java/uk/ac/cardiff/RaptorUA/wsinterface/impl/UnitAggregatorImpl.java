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
package uk.ac.cardiff.RaptorUA.wsinterface.impl;

import java.util.List;
import java.util.Set;

import javax.jws.WebService;

import uk.ac.cardiff.RaptorUA.engine.UnitAggregatorEngine;
import uk.ac.cardiff.RaptorUA.service.impl.UAProcessImpl;
import uk.ac.cardiff.model.Entry;
import uk.ac.cardiff.model.wsmodel.ICAEntryPush;
import uk.ac.cardiff.sei.UnitAggregator;

/**
 * @author philsmart
 *
 */

@WebService(endpointInterface = "uk.ac.cardiff.sei.UnitAggregator")
public class UnitAggregatorImpl implements UnitAggregator{

    private UAProcessImpl processService;

	/* (non-Javadoc)
	 * @see uk.ac.cardiff.RaptorUA.wsinterface.UnitAggregator#getAllAuthentications()
	 */
	public Set getAllAuthentications() {
		// TODO Auto-generated method stub
		return processService.getAllAuthentications();
	}

	/* (non-Javadoc)
	 * @see uk.ac.cardiff.RaptorUA.wsinterface.UnitAggregator#getVersion()
	 */
	public String getVersion() {
		return "Alpha";
	}

	public void setProcessService(UAProcessImpl processService) {
	    this.processService = processService;
	}

	public UAProcessImpl getProcessService() {
	    return processService;
	}

	@Override
	public void addAuthentications(ICAEntryPush pushed) {
	    processService.addAuthentications(pushed);

	}



}
