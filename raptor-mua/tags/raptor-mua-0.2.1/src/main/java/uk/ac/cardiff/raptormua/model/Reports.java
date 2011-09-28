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
package uk.ac.cardiff.raptormua.model;

import java.util.List;

/**
 * Class to store (which are also persisted) saved reports for an individual
 * <code>RaptorUser</code>
 * 
 * @author philsmart
 *
 */
public class Reports {
	
	private List<Report> savedReports;

	public void setSavedReports(List<Report> savedReports) {
		this.savedReports = savedReports;
	}

	public List<Report> getSavedReports() {
		return savedReports;
	}

}
