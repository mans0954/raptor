package uk.ac.cardiff.utility;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collection;

public class StringUtils {

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
