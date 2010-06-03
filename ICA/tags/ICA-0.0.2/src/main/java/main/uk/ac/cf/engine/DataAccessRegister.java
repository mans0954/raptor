/**
 * 
 */
package main.uk.ac.cf.engine;

import java.util.ArrayList;
import java.util.List;

import main.uk.ac.cf.dao.external.AuthenticationInput;

/**
 * @author philsmart
 *
 * Provides a register for parsing engines to plugin to. Also manages such engines.
 */
public class DataAccessRegister {

	private List<AuthenticationInput> authenticationModules;
	
	public DataAccessRegister(){
		setAuthenticationModules(new ArrayList<AuthenticationInput>());
	}

	public void setAuthenticationModules(List<AuthenticationInput> authenticationModules) {
		for (AuthenticationInput authI : authenticationModules)System.out.println("Registering: "+authI.getClass());
		this.authenticationModules = authenticationModules;
	}

	public List<AuthenticationInput> getAuthenticationModules() {
		return authenticationModules;
	}
	
	
}
