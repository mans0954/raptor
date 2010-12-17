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
package main.uk.ac.cf.engine.sei;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.cxf.aegis.databinding.AegisDatabinding;
import org.apache.cxf.frontend.ClientProxyFactoryBean;
import org.apache.cxf.interceptor.Fault;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import uk.ac.cardiff.model.AuthenticationEntry;
import uk.ac.cardiff.model.Entry;
import uk.ac.cardiff.model.ShibbolethEntry;
import uk.ac.cardiff.model.UsageEntry;
import uk.ac.cardiff.model.wsmodel.Capabilities;
import uk.ac.cardiff.model.wsmodel.ICAEntryPush;
import uk.ac.cardiff.sei.Collector;
import uk.ac.cardiff.sei.UnitAggregator;

/**
 * @author philsmart
 *
 *         Instances of this class are responsible for retrieving data from a
 *         service endpoint
 *
 */
public class ServiceEndpointInterface {
    static Logger log = LoggerFactory.getLogger(ServiceEndpointInterface.class);

    public static boolean sendAuthentications(ICAEntryPush pushed, String endpoint) {
	try {
	    ClientProxyFactoryBean factory = new ClientProxyFactoryBean();
	    factory.setServiceClass(UnitAggregator.class);
	    AegisDatabinding databinding = new AegisDatabinding();

	    Set<String> overrides = new HashSet();
	    overrides.add(ShibbolethEntry.class.getName());
	    overrides.add(AuthenticationEntry.class.getName());
	    overrides.add(UsageEntry.class.getName());
	    databinding.setOverrideTypes(overrides);

	    factory.setAddress(endpoint);
	    factory.getServiceFactory().setDataBinding(databinding);

	    UnitAggregator client = (UnitAggregator) factory.create();
	    log.debug("Accessing the UA version " + client.getVersion());
	    client.addAuthentications(pushed);
	    log.debug("Sent {} authentications", pushed.getEntries().size());
	    return true;
	} catch (Exception e) {
	    log.error("Could not send to {} ",endpoint,e);
	    return false;
	}

    }

}
