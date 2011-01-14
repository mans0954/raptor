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

import static org.junit.Assert.*;

import org.junit.Test;

import uk.ac.cardiff.raptormua.runtimeutils.ReflectionHelper;

public class ReflectionHelperTest {
	
	@Test
	public void testFindClassForField(){		
			String classForField = ReflectionHelper.findEntrySubclassForMethod("principleName");
			System.out.println("ShibbolethEntry = "+classForField);
			equals(classForField.equals("ShibbolethEntry"));
			
			classForField = ReflectionHelper.findEntrySubclassForMethod("requestHost");
			System.out.println("Entry = "+classForField);
			equals(classForField.equals("Entry"));
			
			
	}

}
