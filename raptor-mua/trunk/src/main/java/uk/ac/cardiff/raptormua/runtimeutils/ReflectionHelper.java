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

import java.io.File;
import java.io.FileInputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarInputStream;

import org.apache.log4j.Logger;

import sun.misc.Launcher;

/**
 * @author philsmart
 * 
 */
public class ReflectionHelper {
	static Logger log = Logger.getLogger(ReflectionHelper.class);

	/**
	 * This method finds the simple name of the class in the uk.ac.cardiff.model
	 * package that contains the <code>fieldName</code>.
	 * 
	 * @param fieldName
	 * @return
	 */
	public static String findEntrySubclassForMethod(String fieldName) {
		String forPckgName = "uk.ac.cardiff.model";
		String jarFile = getJARFilePath(forPckgName);
		jarFile = jarFile.replace("file:", "");
		List<String> classes = getClasseNamesInPackageJAR(jarFile, forPckgName);
		ArrayList allclasses = new ArrayList();
		for (String classname : classes) {
			try {
				Object o = Class.forName(classname.replace(".class", "")).newInstance();
				if (o instanceof uk.ac.cardiff.model.Entry) {
				    //log.debug("Found classname: "+classname.replace(".class", ""));
					allclasses.add(o);
				}
			} catch (ClassNotFoundException cnfex) {
				log.error(cnfex);
			} catch (InstantiationException iex) {
				// We try to instantiate an interface
				// or an object that does not have a
				// default constructor
			} catch (IllegalAccessException iaex) {
				// The class is not public
			}
		}
		
		Object objectWithMethod = null;
		for (Object object : allclasses) {
			if (hasField(object, fieldName)) {
				objectWithMethod = object;
			}
		}
		if (objectWithMethod != null) {
			log.debug("Object " + objectWithMethod.getClass().getName() + " has method " + fieldName
					+ " returning simple name " + objectWithMethod.getClass().getSimpleName());
			return objectWithMethod.getClass().getSimpleName();
		}

		return null;

	}

	/**
	 * Gets the name, as a string, of the JAR file that contains the package <code>pckgname</code>
	 * @param pckgname
	 * @return
	 */
	private static String getJARFilePath(String pckgname) {
		String name = new String(pckgname);
		if (!name.startsWith("/")) {
			name = "/" + name;
		}
		name = name.replace('.', '/');
		// Get a File object for the package
		log.debug("package name: " + name);
		URL url = ReflectionHelper.class.getResource(name);
		log.debug("URL: " + url.getPath().substring(0, url.getPath().indexOf('!')));
		if (url != null && url.getPath().contains("!"))
			return url.getPath().substring(0, url.getPath().indexOf('!'));
		else if (url != null)
			return url.getPath();
		else
			return null;
	}

	/**
	 * Gets the names of the classes, as strings, in the jar <code>jarName</code> and package
	 * <code>packageName</code>
	 * 
	 * @param jarName
	 * @param packageName
	 * @return
	 */
	public static List<String> getClasseNamesInPackageJAR(String jarName, String packageName) {
		ArrayList<String> classes = new ArrayList<String>();
		packageName = packageName.replaceAll("\\.", "/");
		try {
			JarInputStream jarFile = new JarInputStream(new FileInputStream(jarName));
			JarEntry jarEntry;
			while (true) {
				jarEntry = jarFile.getNextJarEntry();
				if (jarEntry == null) {
					break;
				}
				if ((jarEntry.getName().startsWith(packageName)) && (jarEntry.getName().endsWith(".class"))) {
					classes.add(jarEntry.getName().replaceAll("/", "\\."));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return classes;
	}

	/**
	 * Code taken and adapted from the JWhich project. Finds all subclasses of
	 * the uk.ac.cardiff.model.Entry class in the package <code>pckgname</code> if they
	 * exist outside any JAR libraries, use <code>getClasseNamesInPackageJAR</code>
	 * 
	 * @param pckgname
	 * @return
	 */
	private static Object[] subclassesInDirectory(String pckgname) {
		ArrayList<Object> allclasses = new ArrayList();

		String name = new String(pckgname);
		if (!name.startsWith("/")) {
			name = "/" + name;
		}
		name = name.replace('.', '/');

		// Get a File object for the package
		log.debug("package name: " + name);
		URL url = ReflectionHelper.class.getResource(name);
		log.debug("URL: " + url);
		File directory = new File(url.getFile());

		// New code
		// ======
		if (directory.exists()) {
			// Get the list of the files contained in the package
			String[] files = directory.list();
			for (int i = 0; i < files.length; i++) {

				// we are only interested in .class files
				if (files[i].endsWith(".class")) {
					// removes the .class extension
					String classname = files[i].substring(0, files[i].length() - 6);
					try {
						log.debug("classname: " + classname);
						// Try to create an instance of the object
						Object o = Class.forName(pckgname + "." + classname).newInstance();
						if (o instanceof uk.ac.cardiff.model.Entry) {
							// log.debug("Found classname: "+classname);
							allclasses.add(o);
						}
					} catch (ClassNotFoundException cnfex) {
						log.error(cnfex);
					} catch (InstantiationException iex) {
						// We try to instantiate an interface
						// or an object that does not have a
						// default constructor
					} catch (IllegalAccessException iaex) {
						// The class is not public
					}
				}
			}
		}
		return allclasses.toArray();
	}

	/**
	 * Checks whether the Object <code>object</code> has the field <code>fieldName</code>
	 * 
	 * @param object
	 * @param fieldName
	 * @return
	 */
	private static Boolean hasField(Object object, String fieldName) {
		try {
			Field[] fields = object.getClass().getDeclaredFields();
			for (Field field : fields)
				if (field.getName().equals(fieldName))
					return true;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;

	}

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
			log.error("Field name '" + fieldname + "' does not match internal model attribute");
			e.printStackTrace();
			// System.exit(1);

		}
		return null;
	}

	public static void setValueOnObject(String fieldname, Object param, Object object) {
		try {
			Class id = object.getClass();
			String fieldAsMethod = ReflectionHelper.prepareMethodNameSet(fieldname);
			Method setter = id.getMethod(fieldAsMethod, new Class[] { param.getClass() });
			// log.debug("Trying to Set :"+param)
			setter.invoke(object, new Object[] { param });
		} catch (Throwable e) {
			log.error("Field name '" + fieldname + "' does not match internal model attribute, or parameters are wrong");
			// e.printStackTrace();
			// System.exit(1);

		}
	}

}
