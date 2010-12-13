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
package uk.ac.cardiff.RaptorUA.service.impl;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.apache.log4j.Logger;

import uk.ac.cardiff.RaptorUA.engine.UnitAggregatorEngine;
import uk.ac.cardiff.RaptorUA.service.UAProcess;
import uk.ac.cardiff.model.Entry;

/**
 * @author philsmart
 *
 */
public class UAProcessImpl implements UAProcess {

    /* class logger */
    static Logger log = Logger.getLogger(UAProcessImpl.class);

    /* the central point or engine for this Unit Aggregator */
    private UnitAggregatorEngine aggregatorEngine;

    /* how long any retrieve method should wait before it returns an empt set*/
    private final int getTimeout =10000;

    /*
     * ReentrantLock to prevent both capture and retrieve at the same time
     */
    final Lock lockR = new ReentrantLock();

    /*
     * (non-Javadoc)
     *
     * @see uk.ac.cardiff.RaptorUA.service.UAProcess#poll()
     */
    public void poll() {
	if (lockR.tryLock()) {
	    try {
		log.info("Polling ICAs...");
		aggregatorEngine.poll();
		log.info("Done");
	    } catch (Exception e) {
		// TODO either throw as service output, or deal with here
		log.error(e.getMessage());
		e.printStackTrace();
	    } finally {
		lockR.unlock();
	    }
	}

    }

    public void setAggregatorEngine(UnitAggregatorEngine aggregatorEngine) {
	this.aggregatorEngine = aggregatorEngine;
    }

    public UnitAggregatorEngine getAggregatorEngine() {
	return aggregatorEngine;
    }

    /*
     * (non-Javadoc)
     *
     * @see uk.ac.cardiff.RaptorUA.service.UAProcess#toStdOut()
     */
    public void toStdOut() {
	aggregatorEngine.toStdOut();

    }

    /**
     *
     */
    public Set getAllAuthentications() {
	Set authentications = new LinkedHashSet<Entry>();
	try {
	    if (lockR.tryLock(getTimeout, TimeUnit.MILLISECONDS)) {
		try {
		    log.debug("Retreiving all authentications form UA");
		    authentications = aggregatorEngine.getAllAuthentications();
		    log.debug("Retreiving all authentications form UA...done");
		} finally {
		    lockR.unlock();
		}
	    }
	    else{
		log.debug("Lock not obtained within 10000ms, assuming still parsing, passing back 0 entries");
	    }
	} catch (InterruptedException e) {
	    e.printStackTrace();
	}
	return authentications;

    }

}
