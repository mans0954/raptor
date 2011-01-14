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

import java.util.Comparator;

import uk.ac.cardiff.raptormua.engine.statistics.records.Observation;

/**
 * @author philsmart
 *
 */
public class ObservationComparator implements Comparator<Observation>{

    private boolean asc;

    public ObservationComparator(boolean asc){
	this.asc = asc;
    }

    /* (non-Javadoc)
     * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
     */
    @Override
    public int compare(Observation arg0, Observation arg1) {
	if (asc)
	    return (int)(arg0.getValue() - arg1.getValue()); //if =0 same, < 0 smaller, >0 bigger
	else
	    return (int)(arg1.getValue() - arg0.getValue());
    }

}
