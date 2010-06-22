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
package main.uk.ac.cf.wsinterface.impl;

import java.util.List;

import javax.jws.WebService;

import org.apache.log4j.Logger;
import org.apache.maven.shared.runtime.MavenRuntimeException;

import runtimeutils.MavenMetadata;

import main.uk.ac.cf.service.ICAProcess;
import main.uk.ac.cf.wsinterface.Collector;


/**
 * @author philsmart
 * This is the implementation of the service endpoint interface (SEI)
 */
@WebService(endpointInterface = "main.uk.ac.cf.wsinterface.Collector",portName = "CollectorPortType")
public class CollectorImpl implements Collector{
   	Logger  logger = Logger.getLogger(this.getClass().getName());
    	private ICAProcess processEngine;


	public String getVersion() {
		logger.info("Getting Version for "+this+" with engine: "+processEngine);
//		try {
//			MavenMetadata mvn = new MavenMetadata();
//			mvn.printProjects();
//		} catch (MavenRuntimeException e) {
//			System.out.println("ERROR: "+e.getMessage());
//		}
		return "Early Preview";
	}

	/* (non-Javadoc)
	 * @see main.uk.ac.cf.wsinterface.Collector#getAllAuthentications()
	 */
	public List getAllAuthentications() {
	     return processEngine.getAllAuthentications();
	}

	/* (non-Javadoc)
	 * @see main.uk.ac.cf.wsinterface.Collector#getAllUsages()
	 */
	public List getAllUsages() {

	    return processEngine.getAllUsages();
	}

	public void setProcessEngine(ICAProcess processEngine) {
	    this.processEngine = processEngine;
	}

	public ICAProcess getProcessEngine() {
	    return processEngine;
	}



}
