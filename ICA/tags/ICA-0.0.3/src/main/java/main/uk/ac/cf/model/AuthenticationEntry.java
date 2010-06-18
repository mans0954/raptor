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
