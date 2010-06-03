package main.uk.ac.cf.service.impl;

import main.uk.ac.cf.engine.CaptureEngine;
import main.uk.ac.cf.service.ICAProcess;

import org.apache.log4j.Logger;

/**
 * @author philsmart
 *
 * main service suite for the ICA
 */
public class ICAProcessImpl implements ICAProcess{
	static Logger log = Logger.getLogger(ICAProcess.class);


	private CaptureEngine engine;

	/* (non-Javadoc)
	 * @see main.uk.ac.cf.service.ICAProcess#capture()
	 */
	public void capture() {
		// TODO Auto-generated method stub
		log.info("Running Capture");
		
	}

	public void setEngine(CaptureEngine engine) {
		log.debug("Setting ICA CORE Engine");
		this.engine = engine;
	}

	public CaptureEngine getEngine() {
		return engine;
	}
	
	
}
