import main.uk.ac.cf.wsinterface.Collector;
import main.uk.ac.cf.wsinterface.impl.CollectorImpl;

import org.apache.cxf.frontend.ClientProxyFactoryBean;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;


public class MockClient {
	private static final Logger LOGGER = Logger.getLogger(MockClient.class);

	public static void main(String args[]) {
		BasicConfigurator.configure();
		//Currently this does not work...
		ClientProxyFactoryBean factory = new ClientProxyFactoryBean();
		factory.setServiceClass(Collector.class);
		factory.setAddress("http://localhost:8080/ICA/Collector");
		Collector client = (Collector) factory.create();
		LOGGER.debug("Sending for version: ");
		System.out.println(factory.getWsdlLocation());
		String response = client.getVersion();
		LOGGER.debug("Returned: " + response);
		System.exit(0);
	}

}
