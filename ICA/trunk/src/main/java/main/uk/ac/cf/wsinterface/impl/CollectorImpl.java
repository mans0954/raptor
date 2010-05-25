package main.uk.ac.cf.wsinterface.impl;

import java.util.List;

import javax.jws.WebService;

import org.apache.maven.shared.runtime.MavenRuntimeException;

import runtimeutils.MavenMetadata;

import main.uk.ac.cf.wsinterface.Collector;


@WebService(endpointInterface = "main.uk.ac.cf.wsinterface.Collector")
public class CollectorImpl implements Collector{

	public String getVersion() {
		System.out.println("Getting Version for "+this);
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
