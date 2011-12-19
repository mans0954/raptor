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

package uk.ac.cardiff.raptor.parse.filter.match;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import uk.ac.cardiff.raptor.parse.filter.AbstractInclusionEntry;

/**
 * The Class RegexInclusionEntry.
 * 
 */
public class RegexInclusionEntry extends AbstractInclusionEntry {

    /** The regex to match with. */
    private String regexMatch;

    /**
     * Constructs a regex from the <code>match</code> string, and checks if this regex is contained in the input
     * <code>value</code>.
     * 
     * @param value the value to find a match from
     * @return true, if successful
     */
    public boolean filter(String value) {
        Pattern p = Pattern.compile(this.getMatch());
        Matcher m = p.matcher(value);
        return m.find();
    }

    /**
     * Sets the regex match.
     * 
     * @param regexMatch the new regex match
     */
    public void setRegexMatch(String regexMatch) {
        this.regexMatch = regexMatch;
    }

    /**
     * Gets the regex match.
     * 
     * @return the regex match
     */
    public String getRegexMatch() {
        return regexMatch;
    }

}
