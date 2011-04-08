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
public class EzproxyAuthenticationEvent extends AuthenticationEvent{

    private String requesterIp;
    private String sessionId;

    public void setRequesterIp(String requesterIp) {
	this.requesterIp = requesterIp;
    }

    public String getRequesterIp() {
	return requesterIp;
    }

    /**
     * @param sessionId the sessionId to set
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
	    } catch (Exception e){
		//do nothing
	    }
	}
	builder.append("]");
	return builder.toString();
    }

}
