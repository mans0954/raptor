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
package uk.ac.cardiff.raptormua.runtimeutils;

import java.lang.reflect.Method;

import org.apache.log4j.Logger;

import uk.ac.cardiff.raptormua.engine.statistics.AuthenticationStatistic;

/**
 * @author philsmart
 *
 */
public class ReflectionHelper {
	static Logger log = Logger.getLogger(ReflectionHelper.class);

	public static String prepareMethodNameSet(String method) {
		if (method.length() > 0) {
			String name = "set";
			// now capitalise the first letter of the method name
			Character firstLetter = method.charAt(0);
			firstLetter = Character.toUpperCase(firstLetter);
			String newMethodName = method.substring(1, method.length());
			name += firstLetter + newMethodName;
			// System.out.println("method name: " + name);
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
			// System.out.println("method name: " + name);
			return name;

		}
		return method;
	}

	public static Object getValueFromObject(String fieldname, Object object) {
		try {
			Class id = object.getClass();
			String fieldAsMethod = ReflectionHelper.prepareMethodNameGet(fieldname);
			Method getter = id.getMethod(fieldAsMethod, new Class[] {});
			// log.debug("Trying to Set :"+param)
			Object result = getter.invoke(object, new Object[] {});
			return result;
		} catch (Throwable e) {
			log.error("Field name '" + fieldname
					+ "' does not match internal model attribute");
			e.printStackTrace();
			// System.exit(1);

		}
		return null;
	}

	public static void setValueOnObject(String fieldname, Object param, Object object) {
		try {
			Class id = object.getClass();
			Method setter = id.getMethod(fieldname, new Class[] { param
					.getClass() });
			// log.debug("Trying to Set :"+param)
			setter.invoke(object, new Object[] { param });
		} catch (Throwable e) {
			log.error("Field name '" + fieldname
					+ "' does not match internal model attribute");
			e.printStackTrace();
			// System.exit(1);

		}
	}

}
