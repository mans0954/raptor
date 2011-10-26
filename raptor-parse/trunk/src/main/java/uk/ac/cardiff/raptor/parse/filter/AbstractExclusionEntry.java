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

package uk.ac.cardiff.raptor.parse.filter;

/**
 * The Class ExclusionEntry.
 * 
 * @author philsmart
 */
public abstract class AbstractExclusionEntry implements EventFilter {

    /** The field name. */
    private String fieldName;

    /** The match. */
    private String match;

    /**
     * Sets the field name.
     * 
     * @param fieldName the new field name
     */
    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    /**
     * Gets the field name.
     * 
     * @return the field name
     */
    public String getFieldName() {
        return fieldName;
    }

    /**
     * Sets the match.
     * 
     * @param matchString the new match
     */
    public void setMatch(String matchString) {
        this.match = matchString;
    }

    /**
     * Gets the match.
     * 
     * @return the match
     */
    public String getMatch() {
        return match;
    }

}
