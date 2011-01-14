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

package uk.ac.cardiff.RaptorUA.engine;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



import uk.ac.cardiff.RaptorUA.model.ICAEntry;

/**
 * @author philsmart handles the list of attached ICAs as injected by Spring
 */
public class ICARegistry {
    static Logger log = LoggerFactory.getLogger(ICARegistry.class);

    private List<ICAEntry> ICAEntries;

    public ICARegistry() {
	setICAEntries(new ArrayList<ICAEntry>());
    }

    public void setICAEntries(List<ICAEntry> ICAEntries) {
	log.info("Setting ICAs");
	for (ICAEntry entry : ICAEntries)
	    log.info("Registering: " + entry.getClass());
	this.ICAEntries = ICAEntries;
    }

    public List<ICAEntry> getICAEntries() {
	return ICAEntries;
    }
}
