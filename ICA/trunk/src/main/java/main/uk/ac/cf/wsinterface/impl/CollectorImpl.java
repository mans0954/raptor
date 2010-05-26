package main.uk.ac.cf.wsinterface.impl;

import java.util.List;

import javax.jws.WebService;

import org.apache.log4j.Logger;
import org.apache.maven.shared.runtime.MavenRuntimeException;

import runtimeutils.MavenMetadata;

import main.uk.ac.cf.wsinterface.Collector;


/**
 * @author philsmart
 * 
 */
@WebService(endpointInterface = "main.uk.ac.cf.wsinterface.Collector")
public class CollectorImpl implements Collector{
	Logger  logger = Logger.getLogger(this.getClass().getName());

	public String getVersion() {
		logger.info("Getting Version for "+this);
//		try {
//			MavenMetadata mvn = new MavenMetadata();
//			mvn.printProjects();
//		} catch (MavenRuntimeException e) {
//			System.out.println("ERROR: "+e.getMessage());
//		}
		return "Early Preview";
	}

	public List parse() {		
		return null;
	}

}
