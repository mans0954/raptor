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

package uk.ac.cardiff.raptor.event.expansion;

/**
 * @author philsmart
 * 
 */
public class AttributeLookup {

    /** The name of the attribute in the source data store. */
    private String sourceAttributeName;

    /** The name of the attribute in the internal model that this attribute maps to. */
    private String internalAttributeName;

    /**
     * @param sourceAttributeName the sourceAttributeName to set
     */
    public void setSourceAttributeName(final String sourceAttributeName) {
        this.sourceAttributeName = sourceAttributeName;
    }

    /**
     * @return the sourceAttributeName
     */
    public String getSourceAttributeName() {
        return sourceAttributeName;
    }

    /**
     * @param internalAttributeName the internalAttributeName to set
     */
    public void setInternalAttributeName(final String internalAttributeName) {
        this.internalAttributeName = internalAttributeName;
    }

    /**
     * @return the internalAttributeName
     */
    public String getInternalAttributeName() {
        return internalAttributeName;
    }

}
