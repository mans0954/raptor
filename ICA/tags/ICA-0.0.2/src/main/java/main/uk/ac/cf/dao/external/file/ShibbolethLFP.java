package main.uk.ac.cf.dao.external.file;

import main.uk.ac.cf.dao.external.AuthenticationInput;

/**
 * @author phil
 *  
 *  This is the primary Shibboleth log file parser.
 *
 */
public class ShibbolethLFP implements AuthenticationInput{ 
	private String logfile;
	
	
	public void parse() {
		// TODO Auto-generated method stub
		
	}
	
	private void preParse(){
		
	}

	private void setLogfile(String logfile) {
		this.logfile = logfile;
	}

	private String getLogfile() {
		return logfile;
	}
	

}
