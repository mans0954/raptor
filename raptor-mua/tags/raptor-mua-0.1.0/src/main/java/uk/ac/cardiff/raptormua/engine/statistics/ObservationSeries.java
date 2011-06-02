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
package uk.ac.cardiff.raptormua.engine.statistics;

import uk.ac.cardiff.raptormua.engine.statistics.records.Observation;

public class ObservationSeries {
	
	private String seriesName;
	
    /*
     * each statistical method produces objects (observations) which are stored in this array variable ready for postprocessing or construction of an
     * <code>AggregatorGraphModel</code>
     */
    private Observation[] observations;

	public void setSeriesName(String seriesName) {
		this.seriesName = seriesName;
	}

	public String getSeriesName() {
		return seriesName;
	}

	public void setObservations(Observation[] observations) {
		this.observations = observations;
	}

	public Observation[] getObservations() {
		return observations;
	}

}
