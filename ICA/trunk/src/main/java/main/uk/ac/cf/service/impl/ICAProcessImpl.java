package main.uk.ac.cf.service.impl;

import java.io.IOException;

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

}
