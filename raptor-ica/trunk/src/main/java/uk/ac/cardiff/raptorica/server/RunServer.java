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

package uk.ac.cardiff.raptorica.server;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import java.util.TimeZone;

import org.mortbay.jetty.Server;
import org.mortbay.jetty.servlet.Context;
import org.mortbay.jetty.servlet.ServletHolder;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.DispatcherServlet;

import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.joran.JoranConfigurator;
import ch.qos.logback.core.joran.spi.JoranException;
import ch.qos.logback.core.util.StatusPrinter;

public class RunServer {

    /**
     * Programmatically do the following:
     * <ol>
     * <li>Set the Apache CXF logger to use SLF4J</li>
     * <li>Configure the logback logger</li>
     * <li>Start a Jetty Server instance including trust and key stores, and set the web.xml in the configuration
     * directory to initialise the servlet.</li>
     * </ol>
     * 
     * @param args the program arguments
     * @throws IOException
     * @throws FileNotFoundException
     * @throws Exception
     */
    public static void main(final String args[]) throws FileNotFoundException, IOException {
        System.setProperty("org.apache.cxf.Logger", "org.apache.cxf.common.logging.Slf4jLogger");
        TimeZone.setDefault(TimeZone.getTimeZone("Etc/UTC"));
        final String configurationFiles =
                System.getProperty("configurationFiles", System.getProperty("user.dir") + "/target/conf");

        configureLogger(configurationFiles + "/logging.xml");

        final Properties props = new Properties();
        props.load(new FileInputStream(configurationFiles + "/server.properties"));

        final int portNumber = Integer.parseInt(props.getProperty("jetty.port", "8110"));

        System.out.println("[INFO] Jetty Config: Using Port " + portNumber);
        System.out.println("[INFO] Spring Config: Configuration files at " + configurationFiles);

        final Server server = new Server(portNumber);
        final Context context = new Context(server, "/ICA", Context.SESSIONS);

        final DispatcherServlet dispatcherServlet = new DispatcherServlet();
        // dispatcherServlet.setContextConfigLocation("classpath:beans.xml");
        final File springBeans = new File(configurationFiles + "/ica-core.xml");
        System.out.println("[INFO] Dispatcher Servlet Configuration Files at " + springBeans.toURI().toString());
        dispatcherServlet.setContextConfigLocation(springBeans.toURI().toString());

        final ServletHolder servletHolder = new ServletHolder(dispatcherServlet);
        context.addServlet(servletHolder, "/*");

        try {
            server.start();
            server.join();
        } catch (final Exception e) {
            e.printStackTrace();
            System.exit(100);
        }

    }

    /**
     * Configures the logger to use the logging.xml file from the applicants conf/ directory
     * 
     * @param logback the location UNC of the logging.xml file
     */
    private static void configureLogger(final String logback) {
        final LoggerContext lc = (LoggerContext) LoggerFactory.getILoggerFactory();

        try {
            final JoranConfigurator configurator = new JoranConfigurator();
            configurator.setContext(lc);
            lc.reset();
            configurator.doConfigure(logback);
        } catch (final JoranException je) {
            // StatusPrinter will handle this
        }
        StatusPrinter.print(lc);

    }

}
