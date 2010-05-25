package main.uk.ac.cf.wsinterface;

import main.uk.ac.cf.wsinterface.impl.CollectorImpl;
import junit.framework.TestCase;

public class WSInterfaceTest extends TestCase{

	
	public WSInterfaceTest(String name){
		super(name);
	}
	
	public void testVersion() throws Exception{
		CollectorImpl collector  = new CollectorImpl();
		//assertFalse(collector.getVersion().equals(""));
		
	}
}
