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

package uk.ac.cardiff.raptor.registry;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author philsmart handles the list of attached ICAs as injected by Spring
 */
public class EndpointRegistry {

    /** Class logger. */
    private final Logger log = LoggerFactory.getLogger(EndpointRegistry.class);


    /** List of endpoints for invoking methods on*/
    private List<Endpoint> endpoints;

    public EndpointRegistry() {
	setEndpoints(new ArrayList<Endpoint>());
    }

    public void setEndpoints(List<Endpoint> endpoints) {
	for (Endpoint entry : endpoints)
	    log.info("Registering Service Endpoint: {}", entry.getServiceEndpoint());
	this.endpoints = endpoints;
    }

    public List<Endpoint> getEndpoints() {
	return endpoints;
    }
}
