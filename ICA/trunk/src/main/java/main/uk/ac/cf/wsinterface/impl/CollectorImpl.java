package main.uk.ac.cf.wsinterface.impl;

import java.util.List;

import javax.jws.WebService;

import main.uk.ac.cf.wsinterface.Collector;


@WebService(endpointInterface = "main.uk.ac.cf.wsinterface.Collector")
public class CollectorImpl implements Collector{

	public String getVersion() {
	
		return "Early Preview";
	}

	public List parse() {		
		return null;
	}

}
