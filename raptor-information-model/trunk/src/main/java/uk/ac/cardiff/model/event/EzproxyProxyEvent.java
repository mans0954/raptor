/**
 *
 */
package uk.ac.cardiff.model.event;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author philsmart
 *
 */
public class EzproxyProxyEvent extends ProxyEvent{

    private String sessionId;

    /**
     * @param sessionId
     *            the sessionId to set
     */
    public void setSessionId(String sessionId) {
	this.sessionId = sessionId;
    }

    /**
     * @return the sessionId
     */
    public String getSessionId() {
	return sessionId;
    }

    public String toString() {
	Method[] methods = this.getClass().getMethods();
	StringBuilder builder = new StringBuilder();
	builder.append(this.getClass() + "@[");
	for (Method method : methods) {
	    try {
		if (method.getName().startsWith("get") && !method.getName().equals("getClass")) {
		    this.getClass().getMethod(method.getName(), (Class[]) null);
		    Object object = method.invoke(this, (Object[]) null);
		    builder.append(method.getName() + " [" + object + "],");

		}
	    } catch (IllegalArgumentException e) {
		e.printStackTrace();
	    } catch (SecurityException e) {
		e.printStackTrace();
	    } catch (NoSuchMethodException e) {
		e.printStackTrace();
	    } catch (IllegalAccessException e) {
		e.printStackTrace();
	    } catch (InvocationTargetException e) {
		e.printStackTrace();
	    }
	}
	builder.append("]");
	return builder.toString();
    }

}
