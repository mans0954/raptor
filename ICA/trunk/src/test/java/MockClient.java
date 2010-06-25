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
import org.apache.cxf.frontend.ClientProxyFactoryBean;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;

import uk.ac.cardiff.sei.Collector;


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
