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
package uk.ac.cardiff.utility;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collection;

public class StringUtils {

    /**
     * Used to construct a string representation of all field value pairs in the object <code>obj</code> passed in as a parameter to the method. It does this
     * using reflection.
     * 
     * @param obj
     *            the object from which to construct a string representation from.
     * @return a <code>String</code> representation of this object.
     */
    public static String buildToString(Object obj) {
        Method[] methods = obj.getClass().getMethods();
        StringBuilder builder = new StringBuilder();
        builder.append(obj.getClass() + "@[");
        for (Method method : methods) {
            try {
                if (method.getName().startsWith("get") && !method.getName().equals("getClass")) {
                    obj.getClass().getMethod(method.getName(), (Class[]) null);
                    Object object = method.invoke(obj, (Object[]) null);
                    if (object instanceof Collection) {
                        builder.append(method.getName() + " [" + Arrays.asList(object) + "],");
                    } else if (object.getClass().isArray()) {
                        Object[] array = (Object[]) object;
                        builder.append(method.getName() + " [" + Arrays.asList(array) + "],");
                    } else {
                        builder.append(method.getName() + " [" + object + "],");
                    }
                }
            } catch (Exception e) {
                // do nothing
            }
        }
        builder.append("]");
        return builder.toString();
    }

}
