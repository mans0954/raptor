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
package uk.ac.cardiff.raptor.raptorica.server;

import java.io.File;
import java.net.URL;
import java.security.ProtectionDomain;

import org.mortbay.jetty.Connector;
import org.mortbay.jetty.Server;
import org.mortbay.jetty.bio.SocketConnector;
import org.mortbay.jetty.nio.SelectChannelConnector;
import org.mortbay.jetty.servlet.Context;
import org.mortbay.jetty.servlet.ServletHolder;
import org.mortbay.jetty.webapp.WebAppContext;
import org.springframework.web.servlet.DispatcherServlet;

public class RunServer {

    public static void main(String args[]) throws Exception {
	/** this will setup and allow the ICA to push information, however the CXF servlet needs to be
	 * configured (as in the web.xml) if we need the CXF services to be exposed.
	 */

	String configurationFiles = System.getProperty("configurationFiles", System.getProperty("user.dir")+"/target/conf/ica-core.xml");
	int portNumber = Integer.parseInt(System.getProperty("port", "8089"));

	System.out.println("[INFO] Jetty Config: Using Port "+portNumber);
	System.out.println("[INFO] Spring Config: Configuration files at "+configurationFiles);

	Server server = new Server(portNumber);
	Context context = new Context(server, "/ICA", Context.SESSIONS);

	DispatcherServlet dispatcherServlet = new DispatcherServlet();
	//dispatcherServlet.setContextConfigLocation("classpath:beans.xml");
	File springBeans = new File(configurationFiles);
	dispatcherServlet.setContextConfigLocation("file://"+springBeans.getCanonicalPath());

	ServletHolder servletHolder = new ServletHolder(dispatcherServlet);
	context.addServlet(servletHolder, "/*");

	try {
		System.out.println(">>> STARTING EMBEDDED JETTY SERVER, PRESS ANY KEY TO STOP");
		server.start();
//		System.in.read();
//		System.out.println(">>> STOPPING EMBEDDED JETTY SERVER");
//		server.stop();
//		server.join();
	} catch (Exception e) {
		e.printStackTrace();
		System.exit(100);
	}


    }



}
