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

package uk.ac.cardiff.raptormua.engine.statistics.helper;

import java.text.Collator;
import java.util.Comparator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import uk.ac.cardiff.raptormua.engine.statistics.records.Group;

/**
 * The Class StringGroupComparator.
 * 
 * @author philsmart
 */
public class StringGroupComparator implements Comparator<Group> {

    /** Class logger. */
    private final Logger log = LoggerFactory.getLogger(StringGroupComparator.class);

    /** The asc. */
    private boolean asc;

    /**
     * Instantiates a new string group comparator.
     * 
     * @param asc the asc
     */
    public StringGroupComparator(boolean asc) {
        this.asc = asc;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
     */
    public int compare(Group arg0, Group arg1) {
        Collator c = Collator.getInstance();
        return c.compare(arg0.getGroupName(), arg1.getGroupName());
    }

}
