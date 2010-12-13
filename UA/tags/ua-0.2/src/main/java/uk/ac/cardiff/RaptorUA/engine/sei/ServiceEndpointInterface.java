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
package uk.ac.cardiff.RaptorUA.engine.sei;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.cxf.aegis.databinding.AegisDatabinding;
import org.apache.cxf.frontend.ClientProxyFactoryBean;
import org.apache.cxf.interceptor.Fault;
import org.apache.log4j.Logger;

import uk.ac.cardiff.RaptorUA.engine.UnitAggregatorEngine;
import uk.ac.cardiff.model.AuthenticationEntry;
import uk.ac.cardiff.model.Entry;
import uk.ac.cardiff.model.ShibbolethEntry;
import uk.ac.cardiff.model.UsageEntry;
import uk.ac.cardiff.sei.Collector;

/**
 * @author philsmart
 *
 * Instances of this class are responsible for retrieving data from a service endpoint
 *
 */
public class ServiceEndpointInterface {
    static Logger log = Logger.getLogger(ServiceEndpointInterface.class);

    public static Set getAuthentications(String endpoint){
	ClientProxyFactoryBean factory = new ClientProxyFactoryBean();
	factory.setServiceClass(Collector.class);
	//factory.setServiceName(new QName("http://impl.wsinterface.cf.ac.uk.main/", "CollectorImplService"));
	AegisDatabinding databinding = new AegisDatabinding();


        Set<String> overrides = new HashSet();
        overrides.add(ShibbolethEntry.class.getName());
        overrides.add(AuthenticationEntry.class.getName());
        overrides.add(UsageEntry.class.getName());
        databinding.setOverrideTypes(overrides);

	factory.setAddress(endpoint);
	//factory.setWsdlLocation("http://localhost:8080/ICA/Collector?wsdl");
	factory.getServiceFactory().setDataBinding(databinding);

	Collector client = (Collector) factory.create();
	log.debug("Accessing the ICA version "+client.getVersion());
	Set<Entry> auths = client.getAllAuthentications();
	log.debug("Retrieved "+auths.size()+" authentications from the ICA ["+endpoint+"]");
//	for (Entry ent : auths){//
//	    log.debug(ent+" "+ent.getClass());//
//	}
	return auths;
    }

    public static Set getUsages(String endpoint){
	ClientProxyFactoryBean factory = new ClientProxyFactoryBean();
	factory.setServiceClass(Collector.class);
	//factory.setServiceName(new QName("http://impl.wsinterface.cf.ac.uk.main/", "CollectorImplService"));
	AegisDatabinding databinding = new AegisDatabinding();


        Set<String> overrides = new HashSet();
        overrides.add(ShibbolethEntry.class.getName());
        overrides.add(AuthenticationEntry.class.getName());
        overrides.add(UsageEntry.class.getName());
        databinding.setOverrideTypes(overrides);

	factory.setAddress(endpoint);
	//factory.setWsdlLocation("http://localhost:8080/ICA/Collector?wsdl");
	factory.getServiceFactory().setDataBinding(databinding);

	Collector client = (Collector) factory.create();
	log.debug("Accessing the ICA version "+client.getVersion());
	Set<Entry> auths = client.getAllUsages();
	log.debug("Retrieved "+auths.size()+" usages");
//	for (Entry ent : auths){//
//	    log.debug(ent+" "+ent.getClass());//
//	}
	return auths;
    }

    public static void main(String args[]){
	ServiceEndpointInterface.getAuthentications("http://localhost:8080/ICA/Collector");
    }

}
