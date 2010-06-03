package main.uk.ac.cf.dao.external.file;

import java.io.IOException;

import main.uk.ac.cf.dao.external.AuthenticationInput;
import main.uk.ac.cf.dao.external.format.LogFileFormat;

import org.apache.log4j.Logger;

/**
 * @author phil
 *
 *  This is the primary log file parser.
 *
 */
public class LogFileParser implements AuthenticationInput{
	static Logger log = Logger.getLogger(LogFileParser.class);
	private LogFileFormat format;
	private String logfile;


	public void parse() throws IOException{

		log.debug("parsing: "+logfile);


		log.debug("done");

	}

	private void preParse(){

	}

	public void setLogfile(String logfile) {
		this.logfile = logfile;
	}

	public String getLogfile() {
		return logfile;
	}

	public void setFormat(LogFileFormat format) {
		this.format = format;
	}

	public LogFileFormat getFormat() {
		return format;
	}


}
