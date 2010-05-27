package main.uk.ac.cf.service.impl;

import main.uk.ac.cf.engine.CaptureEngine;
import main.uk.ac.cf.service.ICAProcess;

/**
 * @author philsmart
 *
 * main service suite for the ICA
 */
public class ICAProcessImpl implements ICAProcess{

	private CaptureEngine engine;

	/* (non-Javadoc)
	 * @see main.uk.ac.cf.service.ICAProcess#capture()
	 */
	public void capture() {
		// TODO Auto-generated method stub
		System.out.println("Running Capture");
		
	}

	public void setEngine(CaptureEngine engine) {
		System.out.println("Setting ICA CORE Engine");
		this.engine = engine;
	}

	public CaptureEngine getEngine() {
		return engine;
	}
	
	
}
