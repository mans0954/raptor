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

import java.io.File;
import java.io.FileInputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarInputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import uk.ac.cardiff.model.event.Event;
import uk.ac.cardiff.model.wsmodel.Suggestion;

/*
 * Complete mess of a class, but with operations fundamental to Raptor, so clean 
 * 
 */
public final class ReflectionHelper {

    /** Class logger */
    private static final Logger log = LoggerFactory.getLogger(ReflectionHelper.class);

    private static final String EVENT_PACKAGE_NAME = "uk.ac.cardiff.model.event";

    private static final String JAR_PREFIX = "jar";

    /**
     * Checks if an attribute exists on a class by finding if it has a 'get' method associated on the class
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
            log.error("Could not determine if class has attribute", e);
            return false;

        }

    }

    /**
     * Sets the attribute <code>attributeID</code> to null on the class <code>object</code>. Iterates through the list
     * of methods to find the setter method, so as the parameter types to the method e.g. using
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
                setter.invoke(object, new Object[] {param});
            } else {
                log.error("Attrubute {} did not exist on object {}", attributeID, object);
            }
        } catch (Throwable e) {
            log.error("Field name '{}' does not match internal model attribute", attributeID, e);

        }

    }

    public static String determineSubclassForMethods(String methodOne, String methodTwo) throws ReflectionException {
        Object methodOneObject = findEntrySubclassForMethodAsObject(methodOne);
        Object methodTwoObject = findEntrySubclassForMethodAsObject(methodTwo);
        if (methodOneObject != null && methodTwoObject != null) {
            log.debug("Classes found, methodOne {}, methodTwo {}", methodOneObject.getClass(),
                    methodTwoObject.getClass());
            log.debug("Is methodOne a superclass of methodTwo {}",
                    (methodOneObject.getClass().isAssignableFrom(methodTwoObject.getClass())));
            if (methodTwoObject.getClass().isAssignableFrom(methodOneObject.getClass())) {
                return methodOneObject.getClass().getSimpleName();
            } else {
                return methodTwoObject.getClass().getSimpleName();
            }
        } else {
            log.error("Could not determine which method belongs to the subclass, from {} and {}", methodOne, methodTwo);
            throw new ReflectionException("Could not determine which method belongs to the subclass");
        }

    }

    /**
     * Uses reflection to get all the classes that are in the <code>EVENT_PACKAGE_NAME</code package.
     * 
     * @return a list of simple names of found classes;
     */
    public static List<String> getAllEventClasses() {
        List<String> allClasses = new ArrayList<String>();
        String forPckgName = EVENT_PACKAGE_NAME;
        String jarFile = getJARFilePath(forPckgName);
        jarFile = jarFile.replace("file:", "");
        log.debug("jar {}", jarFile);
        List<String> classes = getClassNamesInJarOrFolder(jarFile, forPckgName);

        for (String classname : classes) {
            try {
                Object o = Class.forName(classname.replace(".class", "")).newInstance();
                if (o != null)
                    if (o instanceof uk.ac.cardiff.model.event.Event) {
                        allClasses.add(o.getClass().getSimpleName());
                    }
            } catch (ClassNotFoundException cnfex) {
                log.error("error getting subclasses of Entry, {}", cnfex);
            } catch (InstantiationException iex) {
                // log.error("{}", iex);
            } catch (IllegalAccessException iaex) {
                // The class is not public
            }
        }
        return allClasses;
    }

    /**
     * This is terrible code. Finds all fieldnames of all classes that are subclasses of the
     * {@link uk.ac.cardiff.model.event.Event} class.
     * 
     * @return
     */
    public static List<Suggestion> getFieldsFromEntrySubClasses() {
        List<Suggestion> allFields = new ArrayList<Suggestion>();
        String forPckgName = EVENT_PACKAGE_NAME;
        String jarFile = getJARFilePath(forPckgName);
        jarFile = jarFile.replace("file:", "");
        log.debug("jar {}", jarFile);
        List<String> classes = getClassNamesInJarOrFolder(jarFile, forPckgName);

        ArrayList allclasses = new ArrayList();
        for (String classname : classes) {
            try {
                Object o = Class.forName(classname.replace(".class", "")).newInstance();
                if (o != null)
                    log.debug("found object {}", o.getClass());
                if (o instanceof uk.ac.cardiff.model.event.Event) {
                    allclasses.add(o);
                }
            } catch (ClassNotFoundException cnfex) {
                log.error("error getting subclasses of Entry, {}", cnfex);
            } catch (InstantiationException iex) {
                // log.error("{}", iex);
            } catch (IllegalAccessException iaex) {
                // The class is not public
            }
        }
        for (Object object : allclasses) {
            Field[] fields = object.getClass().getDeclaredFields();
            for (Field field : fields) {
                // if field is another class in the same package, then expand its methods
                if (field.getType().getCanonicalName().contains(EVENT_PACKAGE_NAME)) {
                    Object o = null;
                    String fieldName = field.getName();
                    try {
                        o = Class.forName(field.getType().getCanonicalName()).newInstance();
                    } catch (Exception e) {
                    }
                    if (o != null) {
                        Field[] newFields = o.getClass().getDeclaredFields();
                        for (Field newField : newFields) {
                            // format it for hibernate
                            allFields.add(new Suggestion(object.getClass().getCanonicalName(), fieldName + "."
                                    + newField.getName()));
                        }

                    }
                } else {
                    allFields.add(new Suggestion(object.getClass().getCanonicalName(), field.getName()));
                }
            }

        }

        return allFields;
    }

    public static Object findEntrySubclassForMethodAsObject(String fieldName) {
        String forPckgName = EVENT_PACKAGE_NAME;
        String jarFile = getJARFilePath(forPckgName);
        jarFile = jarFile.replace("file:", "");
        log.debug("jar {}", jarFile);
        List<String> classes = getClassNamesInJarOrFolder(jarFile, forPckgName);
        ArrayList allclasses = new ArrayList();
        for (String classname : classes) {
            try {
                Object o = Class.forName(classname.replace(".class", "")).newInstance();
                if (o instanceof uk.ac.cardiff.model.event.Event) {
                    allclasses.add(o);
                }
            } catch (ClassNotFoundException cnfex) {
                log.error("{}", cnfex);
            } catch (InstantiationException iex) {
                // do nothing
            } catch (IllegalAccessException iaex) {
                // do nothing
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
            return objectWithMethod;
        }

        return null;

    }

    /**
     * This method finds the simple name of the class in the uk.ac.cardiff.model package that contains the
     * <code>fieldName</code>.
     * 
     * @param fieldName
     * @return
     */
    public static String findEntrySubclassForMethod(String fieldName) {
        String forPckgName = EVENT_PACKAGE_NAME;
        String jarFile = getJARFilePath(forPckgName);
        jarFile = jarFile.replace("file:", "");
        List<String> classes = getClassNamesInJarOrFolder(jarFile, forPckgName);
        ArrayList allclasses = new ArrayList();
        for (String classname : classes) {
            try {
                Object o = Class.forName(classname.replace(".class", "")).newInstance();
                if (o instanceof uk.ac.cardiff.model.event.Event) {
                    // log.debug("Found classname: "+classname.replace(".class",
                    // ""));
                    allclasses.add(o);
                }
            } catch (ClassNotFoundException cnfex) {
                log.error("{}", cnfex);
            } catch (InstantiationException iex) {
                // do nothing
            } catch (IllegalAccessException iaex) {
                // do nothing
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
     * 
     * @param pckgname
     * @return the name, as a <code>String</code>, of the JAR file that contains the package <code>pckgname</code>
     */
    private static String getJARFilePath(String pckgname) {
        String name = new String(pckgname);
        if (!name.startsWith("/")) {
            name = "/" + name;
        }
        name = name.replace('.', '/');
        // log.debug("package name: " + name);
        URL url = ReflectionHelper.class.getResource(name);
        // log.debug("URL: "+url);
        if (url != null && url.getPath().contains("!"))
            return url.getPath().substring(0, url.getPath().indexOf('!'));
        else if (url != null)
            return url.getPath();
        else
            return null;
    }

    /**
     * Gets the names of the classes, as strings, in the jar <code>jarName</code> and package <code>packageName</code>
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

    private static List<String> getClassNamesInJarOrFolder(String jarOrDirectoryName, String packageName) {
        if (jarOrDirectoryName.endsWith(JAR_PREFIX)) {
            return getClasseNamesInPackageJAR(jarOrDirectoryName, packageName);
        } else {
            return getClasseNamesInDirectory(jarOrDirectoryName, packageName);
        }
    }

    /**
     * Gets the names of the classes, as strings, in the directory <code>directoryName</code> and package
     * <code>packageName</code>
     * 
     * @param directoryName the name of the directory to look for classes
     * @param packageName the name of the package the classes belong to
     * @return a list of class names in the current folder
     */
    private static List<String> getClasseNamesInDirectory(String directoryName, String packageName) {
        ArrayList<String> classes = new ArrayList<String>();
        packageName = packageName.replaceAll("\\.", "/");
        try {
            File dir = new File(directoryName);
            // log.debug("Directory {}, is directory {}",dir, dir.isDirectory());
            for (String file : dir.list()) {
                if ((file.endsWith(".class"))) {
                    // log.debug("Found class file, {}",file);
                    classes.add(packageName.replaceAll("/", "\\.") + "." + file);
                }
            }
        } catch (Exception e) {
            log.error("Error in getting classnames in directory {}", directoryName);
        }
        return classes;
    }

    /**
     * Code taken and adapted from the JWhich project. Finds all subclasses of the uk.ac.cardiff.model.Event class in
     * the package <code>pckgname</code> if they exist outside any JAR libraries, use
     * <code>getClasseNamesInPackageJAR</code>
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
                        if (o instanceof uk.ac.cardiff.model.event.Event) {
                            // log.debug("Found classname: "+classname);
                            allclasses.add(o);
                        }
                    } catch (ClassNotFoundException cnfex) {
                        log.error("{}", cnfex);
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
     * @param object the object to check
     * @param fieldName the fieldname to check if present on <code>object</code>
     * @return true iff the object has a field name called <code>fieldName</code>, false otherwise
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
            Character firstLetter = method.charAt(0);
            firstLetter = Character.toUpperCase(firstLetter);
            String newMethodName = method.substring(1, method.length());
            name += firstLetter + newMethodName;
            return name;

        }
        return method;
    }

    public static Object getValueFromObject(String fieldname, Object object) {
        try {
            Class<?> id = object.getClass();
            String fieldAsMethod = ReflectionHelper.prepareMethodNameGet(fieldname);
            Method getter = id.getMethod(fieldAsMethod, new Class[] {});
            Object result = getter.invoke(object, new Object[] {});
            return result;
        } catch (Throwable e) {
            log.error("Field name '" + fieldname + "' does not match internal model attribute");

        }
        return null;
    }

    public static void setValueOnObject(String fieldname, Object param, Object object) {
        try {
            Class<?> id = object.getClass();
            String fieldAsMethod = ReflectionHelper.prepareMethodNameSet(fieldname);
            Method setter = id.getMethod(fieldAsMethod, new Class[] {param.getClass()});
            setter.invoke(object, new Object[] {param});
        } catch (Throwable e) {
            log.error("Field name '" + fieldname + "' does not match internal model attribute, or parameters are wrong");

        }
    }

    /**
     * @param objectToAttach
     * @param event
     */
    public static void attachObjectTo(Object objectToAttach, Event event) {
        Field[] fields = getInheritedPrivateFields(event.getClass()).toArray(new Field[0]);
        Field fieldToSet = null;
        for (Field field : fields) {
            if (field.getType() == objectToAttach.getClass()) {
                fieldToSet = field;
            }
        }
        // now use the public accessor method to set it
        String fieldName = fieldToSet.getName();
        setValueOnObject(fieldName, objectToAttach, event);
    }

    private static List<Field> getInheritedPrivateFields(Class<?> type) {
        List<Field> result = new ArrayList<Field>();

        Class<?> i = type;
        while (i != null && i != Object.class) {
            for (Field field : i.getDeclaredFields()) {
                result.add(field);
            }
            i = i.getSuperclass();
        }

        return result;
    }

    /**
     * @param event
     */
    public static int getHashCodeFromEventOrNull(Event event) {
        try {
            int hashcode = ((Integer) ReflectionHelper.getValueFromObject("hashCode", event));
            return hashcode;
        } catch (Exception e) {
            return 0;
        }

    }

}
