/**
 *
 */
package main.uk.ac.cf.engine;

import java.io.IOException;

import org.apache.log4j.Logger;

import main.uk.ac.cf.dao.external.AuthenticationInput;
import main.uk.ac.cf.service.ICAProcess;

/**
 * @author philsmart
 *
 * Responsible for ALL low level capture operations
 */
public class CaptureEngine {
	static Logger log = Logger.getLogger(CaptureEngine.class);

	private DataAccessRegister authRegister;

	public CaptureEngine(){

	}

	public void capturePerform() throws IOException{
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

}
