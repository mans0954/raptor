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
package uk.ac.cardiff.raptormua.engine.statistics.records;

/**
 * @author philsmart
 * this is a generic category, data class to send as a result of a statistical operation for display, may need subclassing
 * in the future e.g. for line charts etc that require more values - This format should be compatible with primeface
 * graphing components.
 */
public class GraphEntry {
	/* the label that is to be displayed for the data entry e.g. a time interval, or a server hostname */
	private String categoryLabel;
	/* the actual value of the data entry e.g. frequency, mean etc. */
	private double value;


	public void setCategoryLabel(String categoryLabel) {
		this.categoryLabel = categoryLabel;
	}
	public String getCategoryLabel() {
		return categoryLabel;
	}
	public void setValue(double value) {
		this.value = value;
	}
	public double getValue() {
		return value;
	}

}
