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
package uk.ac.cardiff.raptorserver.server;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import org.mortbay.jetty.Connector;
import org.mortbay.jetty.Handler;
import org.mortbay.jetty.Server;
import org.mortbay.jetty.bio.SocketConnector;

import org.mortbay.jetty.handler.DefaultHandler;
import org.mortbay.jetty.handler.HandlerCollection;
import org.mortbay.jetty.webapp.WebAppContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.joran.JoranConfigurator;
import ch.qos.logback.core.joran.spi.JoranException;
import ch.qos.logback.core.util.StatusPrinter;

public class RunServer {
    
    /** Class Logger */
    private static final Logger log = LoggerFactory.getLogger(RunServer.class);

    /**
     * Programmatically do the following: 1. Set the Apache CXF logger to use SLF4J 2. Configure the logback logger 3. Start a Jetty Server instance including
     * trust and key stores, and set the web.xml in the configuration directory to initialise the servlet.
     *
     * @param args
     * @throws IOException
     * @throws FileNotFoundException
     * @throws Exception
     */
    public static void main(String args[]) throws FileNotFoundException, IOException {
        System.setProperty("org.apache.cxf.Logger", "org.apache.cxf.common.logging.Slf4jLogger");

        String configurationFiles = System.getProperty("configurationFiles", System.getProperty("user.dir") + "/target/");

        configureLogger(configurationFiles+"conf/logging.xml");

        Properties props = new Properties();
        props.load(new FileInputStream(configurationFiles + "conf/server.properties"));

        int portNumber = Integer.parseInt(props.getProperty("jetty.port", "8112"));
        String webappContextPath = props.getProperty("jetty.webapp.contextPath", "/raptor-web");


        System.out.println("[INFO] Jetty Config: Using Port " + portNumber);
        System.out.println("[INFO] Servlet and Spring Config: Configuration files at " + configurationFiles);

        Server server = new Server();

        SocketConnector connector = new SocketConnector();
        connector.setPort(portNumber);
        connector.setMaxIdleTime(30000);


        server.setConnectors(new Connector[] { connector });

        WebAppContext webappcontext = new WebAppContext();
        webappcontext.setContextPath(webappContextPath);
        webappcontext.setWar(configurationFiles);
        webappcontext.setWar(configurationFiles+"/webapp/raptor-web/");
        System.out.println("[INFO] Configured RaptorWeb To: " + configurationFiles+"/webapp/raptor-web/");

        HandlerCollection handlers = new HandlerCollection();
        handlers.setHandlers(new Handler[] { webappcontext, new DefaultHandler() });

        server.setHandler(handlers);

        try {
              log.info("Starting Jetty Server");
                server.start();
                server.join();
                log.info("Jetty has stopped");
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
