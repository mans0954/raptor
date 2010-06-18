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
package runtimeutils;

/**
 * @author philsmart
 *
 */
public class ReflectionHelper {

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

}
