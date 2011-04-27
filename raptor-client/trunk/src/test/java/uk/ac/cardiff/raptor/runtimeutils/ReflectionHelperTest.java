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
package uk.ac.cardiff.raptor.runtimeutils;

import static org.junit.Assert.*;

import org.junit.Test;

import uk.ac.cardiff.model.event.Event;
import uk.ac.cardiff.model.event.ShibbolethIdpAuthenticationEvent;
import uk.ac.cardiff.model.event.auxiliary.PrincipalInformation;

public class ReflectionHelperTest {

	@Test
	public void testFindClassForField(){
			String classForField = ReflectionHelper.findEntrySubclassForMethod("principalName");
			System.out.println("ShibbolethEntry = "+classForField);
			equals(classForField.equals("ShibbolethIdpAuthenticationEvent"));

			classForField = ReflectionHelper.findEntrySubclassForMethod("serviceHost");
			System.out.println("Event = "+classForField);
			equals(classForField.equals("Event"));


	}

	@Test
	public void testAttachObjectToClass(){
	    Event shibbotheIdpAuthenticationEvent = new ShibbolethIdpAuthenticationEvent();
	    Object principalInformation = new PrincipalInformation();
	    ReflectionHelper.attachObjectTo(principalInformation, shibbotheIdpAuthenticationEvent);
	}

}
