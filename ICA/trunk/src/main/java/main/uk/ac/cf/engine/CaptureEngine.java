/**
 * 
 */
package main.uk.ac.cf.engine;

/**
 * @author philsmart
 *
 * Responsible for ALL low level capture operations
 */
public class CaptureEngine {

	private DataAccessRegister authRegister;
	
	public CaptureEngine(){
		
	}

	public void setAuthRegister(DataAccessRegister authRegister) {
		this.authRegister = authRegister;
	}

	public DataAccessRegister getAuthRegister() {
		return authRegister;
	}
	
}
