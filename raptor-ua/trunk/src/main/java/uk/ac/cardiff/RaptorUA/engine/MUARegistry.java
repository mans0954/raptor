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



import org.apache.log4j.Logger;

import uk.ac.cardiff.RaptorUA.model.ICAEntry;
import uk.ac.cardiff.RaptorUA.model.MUAEntry;

/**
 * @author philsmart handles the list of attached ICAs as injected by Spring
 */
public class MUARegistry {
    static Logger log = Logger.getLogger(MUARegistry.class);

    private List<MUAEntry> MUAEntries;

    public MUARegistry() {
	setMUAEntries(new ArrayList<MUAEntry>());
    }

    public void setMUAEntries(List<MUAEntry> MUAEntries) {
	log.info("Setting ICAs");
	for (MUAEntry entry : MUAEntries)
	    log.info("Registering: " + entry.getClass());
	this.MUAEntries = MUAEntries;
    }

    public List<MUAEntry> getMUAEntries() {
	return MUAEntries;
    }
}
