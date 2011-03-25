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
package uk.ac.cardiff.raptormua.engine.sei;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import javax.xml.ws.soap.SOAPFaultException;

import org.apache.cxf.aegis.databinding.AegisDatabinding;
import org.apache.cxf.frontend.ClientProxyFactoryBean;
import org.apache.log4j.Logger;

import uk.ac.cardiff.model.AuthenticationEntry;
import uk.ac.cardiff.model.Event;
import uk.ac.cardiff.model.ShibbolethEntry;
import uk.ac.cardiff.model.UsageEntry;
import uk.ac.cardiff.sei.UnitAggregator;

/**
 * @author philsmart
 *
 *         Instances of this class are responsible for retrieving data from a service endpoint
 *
 */
public class ServiceEndpointInterface {
    static Logger log = Logger.getLogger(ServiceEndpointInterface.class);

    public static Set getAuthentications(String endpoint) {
	try{
        	ClientProxyFactoryBean factory = new ClientProxyFactoryBean();
        	factory.setServiceClass(UnitAggregator.class);
        	// factory.setServiceName(new QName("http://impl.wsinterface.cf.ac.uk.main/", "CollectorImplService"));
        	AegisDatabinding databinding = new AegisDatabinding();

        	Set<String> overrides = new HashSet();
        	overrides.add(ShibbolethEntry.class.getName());
        	overrides.add(AuthenticationEntry.class.getName());
        	overrides.add(UsageEntry.class.getName());
        	databinding.setOverrideTypes(overrides);

        	factory.setAddress(endpoint);
        	// factory.setWsdlLocation("http://localhost:8080/ICA/Collector?wsdl");
        	factory.getServiceFactory().setDataBinding(databinding);

        	UnitAggregator client = (UnitAggregator) factory.create();

        	log.debug("Accessing the UA version " + client.getVersion());
        	Set<Event> auths = client.getAllAuthentications();
        	log.debug("Retrieved " + auths.size() + " authentications from the UA [" + endpoint + "]");

        	return auths;
	}
	catch(Exception sfe){
	    log.error("Endpoint ["+endpoint+"] is not responding to hails");
	}

	//return empty set if failed
	return new LinkedHashSet();
    }

    public static Set getUsages(String endpoint) {
	ClientProxyFactoryBean factory = new ClientProxyFactoryBean();
	factory.setServiceClass(UnitAggregator.class);
	// factory.setServiceName(new QName("http://impl.wsinterface.cf.ac.uk.main/", "CollectorImplService"));
	AegisDatabinding databinding = new AegisDatabinding();

	Set<String> overrides = new HashSet();
	overrides.add(ShibbolethEntry.class.getName());
	overrides.add(AuthenticationEntry.class.getName());
	overrides.add(UsageEntry.class.getName());
	databinding.setOverrideTypes(overrides);

	factory.setAddress(endpoint);
	// factory.setWsdlLocation("http://localhost:8080/ICA/Collector?wsdl");
	factory.getServiceFactory().setDataBinding(databinding);

	UnitAggregator client = (UnitAggregator) factory.create();
	log.debug("Accessing the ICA version " + client.getVersion());

	return null;
    }

    public static void main(String args[]) {
	ServiceEndpointInterface.getAuthentications("http://localhost:8080/UA/UnitAggregator");
    }

}
