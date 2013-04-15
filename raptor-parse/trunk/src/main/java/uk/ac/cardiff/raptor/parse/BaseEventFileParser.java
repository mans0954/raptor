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

package uk.ac.cardiff.raptor.parse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import uk.ac.cardiff.raptor.parse.filter.ExclusionList;
import uk.ac.cardiff.raptor.parse.filter.InclusionList;

/**
 *
 */
public abstract class BaseEventFileParser extends BaseEventParser {

    /** class logger. */
    private final Logger log = LoggerFactory.getLogger(BaseEventFileParser.class);

    /** A list of <fieldname,value> pairs that should be excluded during parsing. */
    private ExclusionList exclusionList;

    /**
     * A list of <fieldname, value> pairs that should be included during parsing
     */
    private InclusionList inclusionList;

    /**
     * Default constructor
     */
    public BaseEventFileParser() {
        super();
    }

    /**
     * Method to remove all entries from the event handler
     */
    public void removeAllEntries() {
        eventHandler.removeAllEvents();

    }

    /**
     * Sets the exclusion list
     * 
     * @param exclusionList
     */
    public void setExclusionList(ExclusionList exclusionList) {
        this.exclusionList = exclusionList;
    }

    /**
     * Gets the exclusion list
     * 
     * @return
     */
    public ExclusionList getExclusionList() {
        return exclusionList;
    }

    /**
     * Sets the inclusion list
     * 
     * @param inclusionList
     */
    public void setInclusionList(InclusionList inclusionList) {
        this.inclusionList = inclusionList;
    }

    /**
     * Gets the inclusion list
     * 
     * @return
     */
    public InclusionList getInclusionList() {
        return inclusionList;
    }

}
