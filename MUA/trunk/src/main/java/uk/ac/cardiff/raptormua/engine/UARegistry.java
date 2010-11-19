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

package uk.ac.cardiff.raptormua.engine;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import uk.ac.cardiff.raptormua.model.UAEntry;

/**
 * @author philsmart handles the list of attached ICAs as injected by Spring
 */
public class UARegistry {
    static Logger log = Logger.getLogger(UARegistry.class);

    private List<UAEntry> UAEntries;

    public UARegistry() {
    	setUAEntries(new ArrayList<UAEntry>());
    }

    public void setUAEntries(List<UAEntry> UAEntries) {

    	for (UAEntry entry : UAEntries)
    		log.info("Registering: " + entry.getClass());
		this.UAEntries = UAEntries;
    }

    public List<UAEntry> getUAEntries() {
	return UAEntries;
    }
}
