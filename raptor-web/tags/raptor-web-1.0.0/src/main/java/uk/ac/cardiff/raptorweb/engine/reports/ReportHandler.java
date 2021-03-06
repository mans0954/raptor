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
package uk.ac.cardiff.raptorweb.engine.reports;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;

import uk.ac.cardiff.raptorweb.model.DownloadFile;
import uk.ac.cardiff.raptorweb.model.GraphModel;
import uk.ac.cardiff.raptorweb.model.ReportModel;
import uk.ac.cardiff.raptorweb.model.WebSession;

/**
 * @author philsmart
 *
 */
public class ReportHandler {

	static Logger log = LoggerFactory.getLogger(ReportHandler.class);

	private List<ReportConstructor> reportConstructors;


	public void generateReport(WebSession session, String reportType){
		for (ReportConstructor reportConstructor : reportConstructors){
			log.debug("finding report constructor for {} with {}",reportType,reportConstructor.getHandledReportType().toString());
			if (reportConstructor.getHandledReportType().toString().equals(reportType)){
				reportConstructor.generateReport(session);
			}
		}

	}

	public void setReportConstructors(List<ReportConstructor> reportConstructors) {
		this.reportConstructors = reportConstructors;
	}

	public List<ReportConstructor> getReportConstructors() {
		return reportConstructors;
	}

	/**
	 * @param report
	 */
	public void loadSavedReports(WebSession session) {
		for (ReportConstructor reportConstructor : reportConstructors){
			Resource directory = reportConstructor.getSaveDirectory();
			Resource baseDirectory = reportConstructor.getBaseDirectory();
			String fileExtension = reportConstructor.getHandledReportType().fileExtension;
			
			File dir;
			try {
				dir = new File(directory.getFile().getCanonicalPath()+"/"+session.getUser().getName());
				File[] dirList = dir.listFiles();
				for (File file : dirList){
					if (file.getName().endsWith("."+fileExtension)){
						String relativePath = file.getAbsolutePath().replace(baseDirectory.getFile().getParentFile().getAbsolutePath(),"");
						Date created = new Date(file.lastModified());						
						session.getReportmodel().addReportForDownload(file, relativePath, created, reportConstructor.getHandledReportType().displayName);
					}
				}
			}
			catch (IOException e) {
				log.error("Could not read any file for ReportConstructor {}",reportConstructor+" -> "+e.getMessage());
			}
			catch (NullPointerException e) {
				log.error("Could not read any file for ReportConstructor {}",reportConstructor+" -> "+e.getMessage());
			}

		}
	}

	/**
	 * @param selectDownloadFile
	 */
	public void removeReport(ReportModel model) {
	    log.debug("Removing {} report from filesystem ",model.getSelectedDownloadFile().getFile());
	    boolean wasDeleted=model.getSelectedDownloadFile().getFile().delete();
	    log.debug("file was removed {}",wasDeleted);
	    model.removeSelectedDownloadFile();

	}



}
