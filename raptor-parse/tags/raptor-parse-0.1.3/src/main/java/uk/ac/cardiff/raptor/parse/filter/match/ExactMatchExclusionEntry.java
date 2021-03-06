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

import uk.ac.cardiff.raptor.parse.filter.ExclusionEntry;

/**
 * @author philsmart
 * 
 */
public class ExactMatchExclusionEntry extends ExclusionEntry {

	public boolean filter(String value) {
		return (value.equals(this.getMatch())) ? true : false;
	}

}
