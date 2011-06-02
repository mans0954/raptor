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
package uk.ac.cardiff.raptorweb.engine;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import uk.ac.cardiff.raptorweb.model.MUAEntry;

/**
 * @author philsmart
 *
 */
public class MUARegistry {

    /** Class logger */
    private final Logger log = LoggerFactory.getLogger(MUARegistry.class);

    /** List of all MUA's that this ICA has been configured to talk to*/
    private List<MUAEntry> MUAEntries;

    public MUARegistry() {
        setMUAEntries(new ArrayList<MUAEntry>());
    }

    public void setMUAEntries(List<MUAEntry> MUAEntries) {

        for (MUAEntry entry : MUAEntries)
            log.info("Registering the MUA [{}]", entry.getClass());
        this.MUAEntries = MUAEntries;
    }

    public List<MUAEntry> getMUAEntries() {
        return MUAEntries;
    }
}
