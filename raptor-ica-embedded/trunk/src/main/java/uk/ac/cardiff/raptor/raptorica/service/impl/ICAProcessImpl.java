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
package uk.ac.cardiff.raptor.raptorica.service.impl;

import java.io.IOException;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import uk.ac.cardiff.raptor.raptorica.engine.ICAEngine;
import uk.ac.cardiff.raptor.raptorica.service.ICAProcess;

import uk.ac.cardiff.model.event.Event;

/**
 * @author philsmart
 * 
 *         main service suite for the ICA
 */
public class ICAProcessImpl implements ICAProcess {
	static Logger log = LoggerFactory.getLogger(ICAProcess.class);

	private ICAEngine engine;

	/* how long any retrieve method should wait before it returns an empt set */
	private final int getTimeout = 10000;

	/*
	 * ReentrantLock to prevent both capture and retrieve at the same time
	 */
	final Lock lockR = new ReentrantLock();

	/*
	 * This class initiates the <code>CapturePerform</code> method of the
	 * <code>CaptureEngine</code> once it obtains a lock from the
	 * <code>Lock</code> object. Hence, the processImpl can not both capture and
	 * send entries at the same time. Which prevents concurrency issues.
	 * 
	 * (non-Javadoc)
	 * 
	 * @see main.uk.ac.cf.service.ICAProcess#capture()
	 */
	public void capture() {
		if (lockR.tryLock()) {
			try {
				log.info("Running Capture");
				long start = System.currentTimeMillis();
				engine.capturePerform();
				long end = System.currentTimeMillis();
				log.info("Capture Success, taking {} ms", (end - start));
				log.info("Running Event Release");
				boolean released = engine.release();
				log.info("Events released to all listening UAs {}", released);
			} catch (Exception e) {
				// TODO either throw as service output, or deal with here
				log.error(e.getMessage());
				e.printStackTrace();
			} finally {
				lockR.unlock();
			}
		}

	}

	public void setEngine(ICAEngine engine) {
		log.debug("Setting ICA CORE Engine");
		this.engine = engine;
	}

	public ICAEngine getEngine() {
		return engine;
	}

}
