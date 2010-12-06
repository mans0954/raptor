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
package uk.ac.cardiff.raptormua.service.impl;

import org.apache.log4j.Logger;

import uk.ac.cardiff.model.Graph.AggregatorGraphModel;
import uk.ac.cardiff.model.wsmodel.Capabilities;
import uk.ac.cardiff.raptormua.engine.MUAEngine;
import uk.ac.cardiff.raptormua.service.MUAProcess;

/**
 * @author philsmart
 *
 */
public class MUAProcessImpl implements MUAProcess{
	static Logger log = Logger.getLogger(MUAProcessImpl.class);

	private MUAEngine engine;

	public void setEngine(MUAEngine engine) {
		this.engine = engine;
	}

	public MUAEngine getEngine() {
		return engine;
	}

	/* (non-Javadoc)
	 * @see uk.ac.cardiff.raptormua.service.MUAProcess#poll()
	 */
	public void poll() {
		engine.poll();

	}

	/* (non-Javadoc)
	 * @see uk.ac.cardiff.raptormua.service.MUAProcess#performStatistics(java.lang.String)
	 */
	public AggregatorGraphModel performStatistic(String statisticName) {
		log.info("WS Call for perform statistic - "+statisticName);
		return engine.performStatistic(statisticName);

	}

	/* (non-Javadoc)
	 * @see uk.ac.cardiff.raptormua.service.MUAProcess#getCapabilities()
	 */
	public Capabilities getCapabilities() {
		log.info("WS call for get capabilities");
		return engine.getCapabilities();
	}

}
