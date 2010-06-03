/**
 *
 */
package main.uk.ac.cf.dao.external.format;

import java.util.List;

/**
 * @author philsmart
 *
 */
public class LogFileFormat {
	private String delimeter;
	private List<Header> headers;

	public void setHeaders(List headers) {
		this.headers = headers;
	}

	public List<Header> getHeaders() {
		return headers;
	}

	public void setDelimeter(String delimeter) {
		this.delimeter = delimeter;
	}

	public String getDelimeter() {
		return delimeter;
	}

}
