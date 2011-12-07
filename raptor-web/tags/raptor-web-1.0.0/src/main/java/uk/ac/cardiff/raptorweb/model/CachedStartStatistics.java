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
package uk.ac.cardiff.raptorweb.model;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



import uk.ac.cardiff.model.wsmodel.Capabilities;

/**
 * Almost exactly the same class as <code>StartModel</code>, but extended for 
 * semantic operational differences 
 * 
 * @author philsmart
 *
 */
public class CachedStartStatistics{

        /** Class logger */
	private final Logger log = LoggerFactory.getLogger(CachedStartStatistics.class);

	/** The cached statistic */
	private StartStatistics cached;
	
	
	public CachedStartStatistics(){
	    cached = new StartStatistics();
	}

	public void setCached(StartStatistics cached) {
	    this.cached = cached;
	}

	public StartStatistics getCached() {
	    return cached;
	}



}
