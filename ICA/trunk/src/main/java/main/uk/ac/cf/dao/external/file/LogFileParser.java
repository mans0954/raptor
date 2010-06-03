package main.uk.ac.cf.dao.external.file;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

import main.uk.ac.cf.dao.external.AuthenticationInput;
import main.uk.ac.cf.dao.external.format.Header;
import main.uk.ac.cf.dao.external.format.LogFileFormat;
import main.uk.ac.cf.model.AuthenticationEntry;

import org.apache.commons.lang.text.StrTokenizer;
import org.apache.log4j.Logger;

import runtimeutils.ReflectionHelper;

/**
 * @author phil
 *
 *         This is the primary log file parser.
 *
 */
public class LogFileParser implements AuthenticationInput {
	static Logger log = Logger.getLogger(LogFileParser.class);
	private LogFileFormat format;
	private String logfile;

	public void parse() throws Exception {

		log.debug("parsing: " + logfile);
		// Must use URL, as java.io does not work in a webapp

		URL logfileURL = new URL(logfile);
		URLConnection logfileconnection = logfileURL.openConnection();
		BufferedReader in = new BufferedReader(new InputStreamReader(
				logfileconnection.getInputStream()));
		String inputLine;
		while ((inputLine = in.readLine()) != null) {
			// log.debug(inputLine);
			StrTokenizer tokenizer = new StrTokenizer(inputLine, format
					.getDelimeter());
			ArrayList<String> allvalues = new ArrayList();
			while (tokenizer.hasNext()) {
				Object next = tokenizer.next();
				if (next instanceof String)
					allvalues.add((String) next);
				else {
					log.error("input column was not a string");
				}
			}
			// now pickout the headers with which to populate the class
			AuthenticationEntry authE = new AuthenticationEntry();

			for (Header header : format.getHeaders()) {

				try {
					if (!(header.getFieldNo() >= allvalues.size())) {
						String value = allvalues.get(header.getFieldNo());
						log.debug("Field: " + header.getFieldName()	+ " value: " + value);
						// now use the field name, construct a setter and set
						// the property
						String fieldAsMethod = ReflectionHelper.prepareMethodNameSet(header.getFieldName());
						log.debug("methodname " + fieldAsMethod);
						try {
							Class id = authE.getClass();

							Method method = id.getDeclaredMethod(fieldAsMethod,	null);
							method.invoke(authE, null);

						} catch (Throwable e) {
							log.error("Field name '"+ fieldAsMethod	+ "' does not match internal model attribute");

						}
					} else {
						log.error("trying to access field "	+ header.getFieldNo() + ", when only "	+ allvalues.size() + " fields in log file");
					}
				} catch (ClassCastException e) {
					log
							.error("Header Key is not an integer, needs to be an integer.");
				}
			}

		}

		in.close();

		log.debug("done");

	}

	private void preParse() {

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

	// public static void main(String args[]) throws IOException{
	// LogFileParser lfp = new LogFileParser();
	// lfp.setLogfile("/tmp/idp-access.log");
	// lfp.setFormat(new LogFileFormat());
	// lfp.parse();
	// }

}
