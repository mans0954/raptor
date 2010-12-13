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

import org.apache.log4j.Logger;

import main.uk.ac.cf.dao.external.AuthenticationInput;

/**
 * @author philsmart
 *
 * Provides a register for parsing engines to plugin to. Also manages such engines.
 */
public class DataAccessRegister {
	static Logger log = Logger.getLogger(DataAccessRegister.class);

	private List<AuthenticationInput> authenticationModules;

	public DataAccessRegister(){
		setAuthenticationModules(new ArrayList<AuthenticationInput>());
	}

	public void setAuthenticationModules(List<AuthenticationInput> authenticationModules) {
		for (AuthenticationInput authI : authenticationModules)log.info("Registering: "+authI.getClass());
		this.authenticationModules = authenticationModules;
	}

	public List<AuthenticationInput> getAuthenticationModules() {
		return authenticationModules;
	}




}
