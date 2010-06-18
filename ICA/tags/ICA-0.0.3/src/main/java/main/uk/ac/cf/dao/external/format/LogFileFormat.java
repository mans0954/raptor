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
