/**
 *
 */
package main.uk.ac.cf.dao.external.format;

import java.util.Map;

/**
 * @author philsmart
 *
 */
public class LogFileFormat {
	private String delimeter;
	private Map headers;

	public void setHeaders(Map headers) {
		this.headers = headers;
	}

	public Map getHeaders() {
		return headers;
	}

	public void setDelimeter(String delimeter) {
		this.delimeter = delimeter;
	}

	public String getDelimeter() {
		return delimeter;
	}

}
