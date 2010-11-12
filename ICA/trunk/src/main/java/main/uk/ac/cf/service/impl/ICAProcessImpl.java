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
package main.uk.ac.cf.service.impl;

import java.io.IOException;
import java.util.List;

import main.uk.ac.cf.engine.CaptureEngine;
import main.uk.ac.cf.service.ICAProcess;

import org.apache.log4j.Logger;

/**
 * @author philsmart
 *
 *         main service suite for the ICA
 */
public class ICAProcessImpl implements ICAProcess {
    static Logger log = Logger.getLogger(ICAProcess.class);

    private CaptureEngine engine;

    /*
     * (non-Javadoc)
     *
     * @see main.uk.ac.cf.service.ICAProcess#capture()
     */
    public void capture() {
	try {
	    log.info("Running Capture");
	    long start = System.currentTimeMillis();
	    engine.capturePerform();
	    long end = System.currentTimeMillis();
	    log.info("Capture Success, taking " + (end - start) + "ms");
	} catch (Exception e) {
	    // TODO either throw as service output, or deal with here
	    log.error(e.getMessage());
	    e.printStackTrace();
	}

    }

    public void setEngine(CaptureEngine engine) {
	log.debug("Setting ICA CORE Engine");
	this.engine = engine;
    }

    public CaptureEngine getEngine() {
	return engine;
    }

    /*
     * (non-Javadoc)
     *
     * @see main.uk.ac.cf.service.ICAProcess#getAllAuthentications()
     */
    public List getAllAuthentications() {
	return engine.getAllAuthentications();
    }

    /*
     * (non-Javadoc)
     *
     * @see main.uk.ac.cf.service.ICAProcess#getAllUsages()
     */
    public List getAllUsages() {
	// TODO Auto-generated method stub
	return null;
    }

}
