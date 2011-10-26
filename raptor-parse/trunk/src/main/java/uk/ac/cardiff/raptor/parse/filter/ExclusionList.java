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
 * The Class ExclusionList.
 * 
 * @author philsmart
 */
public class ExclusionList {

    /** The array of exclusion entries. */
    private AbstractExclusionEntry[] exclusionEntries;

    /**
     * Sets the exclusion entries.
     * 
     * @param exclusionEntries the new exclusion entries
     */
    public void setExclusionEntries(AbstractExclusionEntry[] exclusionEntries) {
        this.exclusionEntries = exclusionEntries;
    }

    /**
     * Gets the exclusion entries.
     * 
     * @return the exclusion entries
     */
    public AbstractExclusionEntry[] getExclusionEntries() {
        return exclusionEntries;
    }

}
