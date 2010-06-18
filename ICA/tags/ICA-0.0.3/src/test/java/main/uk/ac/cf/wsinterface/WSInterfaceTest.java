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
package main.uk.ac.cf.wsinterface;

import main.uk.ac.cf.wsinterface.impl.CollectorImpl;
import junit.framework.TestCase;

public class WSInterfaceTest extends TestCase{

	
	public WSInterfaceTest(String name){
		super(name);
	}
	
	public void testVersion() throws Exception{
		CollectorImpl collector  = new CollectorImpl();
		assertFalse(collector.getVersion().equals(""));
		
	}
}
