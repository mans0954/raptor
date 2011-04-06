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
package uk.ac.cardiff.raptormua.server;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.security.ProtectionDomain;
import java.util.Properties;

import javax.xml.ws.Endpoint;

import org.apache.cxf.Bus;
import org.apache.cxf.BusFactory;
import org.apache.cxf.transport.servlet.CXFServlet;
import org.mortbay.jetty.Connector;
import org.mortbay.jetty.Handler;
import org.mortbay.jetty.Server;
import org.mortbay.jetty.bio.SocketConnector;
import org.mortbay.jetty.handler.ContextHandlerCollection;
import org.mortbay.jetty.handler.DefaultHandler;
import org.mortbay.jetty.handler.HandlerCollection;
import org.mortbay.jetty.nio.SelectChannelConnector;
import org.mortbay.jetty.security.SslSocketConnector;
import org.mortbay.jetty.servlet.Context;
import org.mortbay.jetty.servlet.ServletHolder;
import org.mortbay.jetty.webapp.WebAppContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

import uk.ac.cardiff.raptor.remoting.server.sei.MultiUnitAggregator;
import uk.ac.cardiff.raptormua.wsinterface.impl.MultiUnitAggregatorImpl;

public class RunServer {

	/**
	 * Programmatically start a Jetty Server instance, and set the web.xml in
	 * the configuration directory to initialise the servlet.
	 * 
	 * @param args
	 * @throws IOException
	 * @throws FileNotFoundException
	 * @throws Exception
	 */
	public static void main(String args[]) throws FileNotFoundException, IOException {

		String configurationFiles = System.getProperty("configurationFiles", System.getProperty("user.dir") + "/target/conf");

		Properties props = new Properties();
		props.load(new FileInputStream(configurationFiles + "/server.properties"));

		int portNumber = Integer.parseInt(props.getProperty("jetty.port", "8443"));
		String keyStoreLocaion = props.getProperty("jetty.keyStoreLocation", "");
		String keyStorePassword = props.getProperty("jetty.keyStorePassword", "changeit");
		String trustStoreLocaion = props.getProperty("jetty.trustStoreLocaion", "");
		String trustStorePassword = props.getProperty("jetty.trustStorePassword", "changeit");
		String webappContextPath = props.getProperty("jetty.webapp.contextPath", "/MUA");

		System.out.println("[INFO] Jetty Config: Using Port " + portNumber);
		System.out.println("[INFO] Servlet and Spring Config: Configuration files at " + configurationFiles);

		Server server = new Server();

		SslSocketConnector sslConnector = new SslSocketConnector();
		sslConnector.setPort(portNumber);
		sslConnector.setMaxIdleTime(30000);
		sslConnector.setKeystore(keyStoreLocaion);
		sslConnector.setPassword(keyStorePassword);
		sslConnector.setKeyPassword(keyStorePassword);
		sslConnector.setTruststore(trustStoreLocaion);
		sslConnector.setTrustPassword(trustStorePassword);

		// enable mutual authentication
		sslConnector.setNeedClientAuth(true);

		server.setConnectors(new Connector[] { sslConnector });

		WebAppContext webappcontext = new WebAppContext();
		webappcontext.setDescriptor(configurationFiles + "/web.xml");
		webappcontext.setContextPath(webappContextPath);
		webappcontext.setWar(configurationFiles);

		HandlerCollection handlers = new HandlerCollection();
		handlers.setHandlers(new Handler[] { webappcontext, new DefaultHandler() });

		server.setHandler(handlers);

		try {
			server.start();
			server.join();
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(100);
		}

	}

}
