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
package uk.ac.cardiff.raptorica.service;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import uk.ac.cardiff.raptorica.engine.ICAEngine;

/**
 * @author philsmart
 *
 *         main service suite for the ICA
 */
public class IcaProcessImpl implements IcaProcess {
	
	/** Class logger */
	private final Logger log = LoggerFactory.getLogger(IcaProcessImpl.class);

	/** The engine responsible for all core ICA functions*/
	private ICAEngine engine;

	/** how long any retrieve method should wait before it returns an empty set */
	private final int TIMEOUT = 10000;

	/**
	 * ReentrantLock to prevent both capture and retrieve at the same time
	 */
	private final Lock lockR = new ReentrantLock();

	/**
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
			} catch (Exception e) {
				log.error("",e);
			} finally {
				lockR.unlock();
			}
		}
		else{
			log.warn("Lock was hit for method [capture]");
		}

	}
	
	/** 
	 * Initiates a process on the <code>engine</code> that removes events from the
	 * <code>entryHandler</code> iff they have been released to all attached 
	 * endpoints. 
	 */
	public void garbageCollect() {
		if (lockR.tryLock()) {
			try {
				log.info("GC. Running Event Garbage Collection");
				long start = System.currentTimeMillis();
				engine.garbageCollect();
				long end = System.currentTimeMillis();
				log.info("GC. Event Garbage Collection Success, taking {} ms", (end - start));
			} catch (Exception e) {
				log.error("",e);
			} finally {
				lockR.unlock();
			}
		}
		else{
			log.warn("Lock was hit for method [garbageCollect]");
		}

	}
	
	public void release(){
		if (lockR.tryLock()) {
			try {
				log.info("Running Event Release");
				boolean released = engine.release();
				log.info("Events released to all listening endpoints {}", released);
			} catch (Exception e) {
				log.error(e.getMessage());
				e.printStackTrace();
			} finally {
				lockR.unlock();
			}
		}
		else{
			log.warn("Lock was hit for method [release]");
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
