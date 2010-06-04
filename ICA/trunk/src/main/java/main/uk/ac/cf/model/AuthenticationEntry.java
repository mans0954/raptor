/**
 *
 */
package main.uk.ac.cf.model;

/**
 * @author philsmart
 *
 */
public class AuthenticationEntry extends Entry{

	private String serverHost;
	private String requestPath;

	public void setServerHost(String serverHost) {
		this.serverHost = serverHost;
	}
	public String getServerHost() {
		return serverHost;
	}
	public void setRequestPath(String requestPath) {
		this.requestPath = requestPath;
	}
	public String getRequestPath() {
		return requestPath;
	}

	public String toString(){
		return "["+this.getEventTime()+","+this.getRequestHost()+","+this.getServerHost()+"]";
	}
}
