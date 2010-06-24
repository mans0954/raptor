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
package main.uk.ac.cf.engine;

import java.util.ArrayList;
import java.util.List;

import main.uk.ac.cf.dao.external.AuthenticationInput;

import org.apache.log4j.Logger;

/**
 * @author philsmart
 *
 * Responsible for ALL low level capture operations
 */
public class CaptureEngine {
    	static Logger log = Logger.getLogger(CaptureEngine.class);

	private DataAccessRegister authRegister;

	public CaptureEngine(){
	    	log.info("ICA Capture Engine is running...");
	}

	public void capturePerform() throws Exception{
		for (AuthenticationInput authI : authRegister.getAuthenticationModules()){
			log.info("Capturing from "+authI);
			authI.parse();
		}
	}

	public void setAuthRegister(DataAccessRegister authRegister) {
		this.authRegister = authRegister;
	}

	public DataAccessRegister getAuthRegister() {
		return authRegister;
	}

	/**
	 * @return all authentication entries (all subtypes of the class AuthenticationEntry
	 */
	public List getAllAuthentications() {
	    List allAuths = new ArrayList();
	    for (AuthenticationInput authI : authRegister.getAuthenticationModules()){
		List authentications = authI.getAuthentications();
		for (Object auth : authentications)allAuths.add(auth);
	    }
	    return allAuths;
	}

	public List getAllUages() {
	    return null;
	}

}
