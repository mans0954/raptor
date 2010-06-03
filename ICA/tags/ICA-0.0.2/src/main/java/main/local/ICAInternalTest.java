package main.local;

import main.uk.ac.cf.wsinterface.impl.CollectorImpl;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

/**
 * @author philsmart
 * This is a test class, for testing the internal methods of the ICA without needed tomcat deployment
 */
public class ICAInternalTest {
		
	CollectorImpl collector;
	Logger  logger = Logger.getLogger(this.getClass().getName());
	
	public ICAInternalTest(){
		logger.setLevel(Level.ALL);
		collector = new CollectorImpl();
	}
	
	public void testVersion(){
		
		logger.debug("Version reported as: "+collector.getVersion());
	}
	
	public static void main(String args[]){
		
		ICAInternalTest tester = new ICAInternalTest();
		tester.testVersion();
		
	}
}
