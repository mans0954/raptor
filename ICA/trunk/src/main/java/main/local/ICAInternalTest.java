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
package main.local;

import main.uk.ac.cf.wsinterface.impl.CollectorImpl;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

/**
 * @author philsmart
 * This is a test class, for testing the internal methods of the ICA without needed tomcat deployment
 */
public class ICAInternalTest {
		
	CollectorImpl collector;
	Logger  logger = Logger.getLogger(this.getClass().getName());
	
	public ICAInternalTest(){
		logger.setLevel(Level.ALL);
		collector = new CollectorImpl();
	}
	
	public void testVersion(){
		
		logger.debug("Version reported as: "+collector.getVersion());
	}
	
	public static void main(String args[]){
		
		ICAInternalTest tester = new ICAInternalTest();
		tester.testVersion();
		
	}
}
