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
/**
 *
 */
package main.uk.ac.cf.wsinterface;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.cxf.aegis.databinding.AegisDatabinding;
import org.apache.cxf.frontend.ClientProxyFactoryBean;

import uk.ac.cardiff.model.AuthenticationEntry;
import uk.ac.cardiff.model.Entry;
import uk.ac.cardiff.model.ShibbolethEntry;
import uk.ac.cardiff.model.UsageEntry;
import uk.ac.cardiff.sei.Collector;

/**
 * @author philsmart
 *
 */
public class Client {

    public static void main(String args[]){
	ClientProxyFactoryBean factory = new ClientProxyFactoryBean();
	factory.setServiceClass(Collector.class);
	//factory.setServiceName(new QName("http://impl.wsinterface.cf.ac.uk.main/", "CollectorImplService"));
	AegisDatabinding databinding = new AegisDatabinding();


        Set<String> overrides = new HashSet();
        overrides.add(ShibbolethEntry.class.getName());
        overrides.add(AuthenticationEntry.class.getName());
        overrides.add(UsageEntry.class.getName());
        databinding.setOverrideTypes(overrides);

	factory.setAddress("http://localhost:8080/ICA/Collector");
	//factory.setWsdlLocation("http://localhost:8080/ICA/Collector?wsdl");
	factory.getServiceFactory().setDataBinding(databinding);

	Collector client = (Collector) factory.create();
	System.out.println(client.getVersion());
	Set<Entry> auths = client.getAllAuthentications();
	System.out.println("Have "+auths.size());
	for (Entry ent : auths){

		System.out.println(ent+" "+ent.getClass());

	}
    }

}
