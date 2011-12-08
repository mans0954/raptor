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

package uk.ac.cardiff.raptormua.engine.statistics.records;

/**
 * The Class ObservationSeries.
 */
public class ObservationSeries {

    /** The series name. */
    private String seriesName;

    /**
     * Each statistical method produces objects (observations) which are stored in this array variable ready for
     * postprocessing or construction of an <code>AggregatorGraphModel</code>.
     */
    private Observation[] observations;

    /**
     * Sets the series name.
     * 
     * @param seriesName the new series name
     */
    public void setSeriesName(String seriesName) {
        this.seriesName = seriesName;
    }

    /**
     * Gets the series name.
     * 
     * @return the series name
     */
    public String getSeriesName() {
        return seriesName;
    }

    /**
     * Sets the observations.
     * 
     * @param observations the new observations
     */
    public void setObservations(Observation[] observations) {
        this.observations = observations;
    }

    /**
     * Gets the observations.
     * 
     * @return the observations
     */
    public Observation[] getObservations() {
        return observations;
    }

}
