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
import java.util.TimeZone;

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
import org.mortbay.log.Log;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.joran.JoranConfigurator;
import ch.qos.logback.core.joran.spi.JoranException;
import ch.qos.logback.core.util.StatusPrinter;

import uk.ac.cardiff.raptor.remoting.server.sei.MultiUnitAggregator;
import uk.ac.cardiff.raptormua.wsinterface.impl.MultiUnitAggregatorImpl;

public class RunServer {

	/**
	 * Programmatically do the following:
	 * 1. Set the Apache CXF logger to use SLF4J
	 * 2. Configure the logback logger
	 * 3. Start a Jetty Server instance including trust and key stores, and set the web.xml in the configuration directory to initialise the servlet.
	 *
	 * @param args
	 * @throws IOException
	 * @throws FileNotFoundException
	 * @throws Exception
	 */
	public static void main(String args[]) throws FileNotFoundException, IOException {
		System.setProperty("org.apache.cxf.Logger", "org.apache.cxf.common.logging.Slf4jLogger");
		TimeZone.setDefault(TimeZone.getTimeZone("Etc/UTC"));
		String configurationFiles = System.getProperty("configurationFiles", System.getProperty("user.dir") + "/target/conf");

		configureLogger(configurationFiles+"/logging.xml");

		Properties props = new Properties();
		props.load(new FileInputStream(configurationFiles + "/server.properties"));

		int portNumber = Integer.parseInt(props.getProperty("jetty.port", "8443"));
		String keyStoreLocaion = props.getProperty("jetty.keyStoreLocation", "");
		String keyStorePassword = props.getProperty("jetty.keyStorePassword", "changeit");
		String trustStoreLocaion = props.getProperty("jetty.trustStoreLocation", "");
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

	private static void configureLogger(String logback) {
		LoggerContext lc = (LoggerContext) LoggerFactory.getILoggerFactory();

		try {
			JoranConfigurator configurator = new JoranConfigurator();
			configurator.setContext(lc);
			lc.reset();
			configurator.doConfigure(logback);
		} catch (JoranException je) {
			// StatusPrinter will handle thiss
		}
		StatusPrinter.print(lc);

	}

}
