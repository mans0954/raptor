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
package uk.ac.cardiff.raptor.runtimeutils;

import java.lang.reflect.Method;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import uk.ac.cardiff.model.Event;

/**
 * @author philsmart
 * 
 */
public class ReflectionHelper {

	/** Class logger */
	private static final Logger log = LoggerFactory.getLogger(ReflectionHelper.class);

	public static String prepareMethodNameSet(String method) {
		if (method.length() > 0) {
			String name = "set";
			// now capitalise the first letter of the method name
			Character firstLetter = method.charAt(0);
			firstLetter = Character.toUpperCase(firstLetter);
			String newMethodName = method.substring(1, method.length());
			name += firstLetter + newMethodName;			
			return name;

		}
		return method;

	}

	/**
	 * @param fieldname
	 * @return
	 */
	public static String prepareMethodNameGet(String method) {
		if (method.length() > 0) {
			String name = "get";
			// now capitalise the first letter of the method name
			Character firstLetter = method.charAt(0);
			firstLetter = Character.toUpperCase(firstLetter);
			String newMethodName = method.substring(1, method.length());
			name += firstLetter + newMethodName;			
			return name;

		}
		return method;
	}

	/**
	 * Checks if an attribute exists on a class by finding if it has a 'get'
	 * method associated on the class
	 * 
	 * @param entry
	 * @param attributeID
	 */
	public static boolean classHasAttribute(Object object, String attributeID) {
		try {
			Class id = object.getClass();
			String fieldAsMethod = prepareMethodNameGet(attributeID);
			Method getter = id.getMethod(fieldAsMethod, new Class[] {});
			return true;
		} catch (Throwable e) {
			e.printStackTrace();
			return false;

		}

	}

	/**
	 * Sets the attribute <code>attributeID</code> to null on the class
	 * <code>object</code>. Iterates through the list of methods to find the
	 * setter method, so as the parameter types to the method e.g. using
	 * <code>id.getMethod(..)</code>, do not need to be known.
	 * 
	 * @param object
	 * @param attributeID
	 */
	public static void nullAttribute(Object object, String attributeID) {
		try {
			// log.debug("Setting {} to null on object {}",attributeID,object);
			Class id = object.getClass();
			String fieldAsMethod = prepareMethodNameSet(attributeID);
			Method[] methods = id.getMethods();
			Method setter = null;
			for (Method method : methods) {
				if (method.getName().equals(fieldAsMethod))
					setter = method;
			}
			if (setter != null) {
				Object param = null;
				setter.invoke(object, new Object[] { param });
			} else {
				log.error("Attrubute {} did not exist on object {}", attributeID, object);
			}
		} catch (Throwable e) {
			log.error("Field name '" + attributeID + "' does not match internal model attribute");
			e.printStackTrace();
			System.exit(1);
		}

	}

}
