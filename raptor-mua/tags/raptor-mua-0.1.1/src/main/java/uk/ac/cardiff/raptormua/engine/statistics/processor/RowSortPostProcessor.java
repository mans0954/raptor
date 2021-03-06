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
package uk.ac.cardiff.raptormua.engine.statistics.processor;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import uk.ac.cardiff.model.event.Event;
import uk.ac.cardiff.raptormua.engine.statistics.StatisticsPostProcessor;
import uk.ac.cardiff.raptormua.engine.statistics.helper.ObservationComparator;
import uk.ac.cardiff.raptormua.engine.statistics.records.Observation;
import uk.ac.cardiff.raptormua.exceptions.PostprocessorException;

/**
 * @author philsmart
 *
 */
public class RowSortPostProcessor implements StatisticsPostProcessor{

	/** class logger */
	private final Logger log = LoggerFactory.getLogger(RowSortPostProcessor.class);

	private boolean ascending;

	public RowSortPostProcessor(){
	    ascending=true;
	}


	/**
	 * <p> performs all actions directly ('live') on the input object, and passes that back as a reference
	 * to conform with the <code>StatisticsPostProcessor</code> interface </p>
	 *
	 * (non-Javadoc)
	 * @see uk.ac.cardiff.raptormua.engine.statistics.StatisticsPostProcessor#postProcess(uk.ac.cardiff.raptormua.engine.statistics.records.Observation[])
	 */
	public Observation[] postProcess(Observation[] observations)	throws PostprocessorException {
		log.debug("Entries into postprocessor: "+observations.length);
		log.info("Post Processor "+this.getClass());
		Arrays.sort(observations,new ObservationComparator(ascending));
		return observations;
	}


	public void setAscending(boolean ascending) {
	    this.ascending = ascending;
	}


	public boolean isAscending() {
	    return ascending;
	}

}
