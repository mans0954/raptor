/**
 *
 */
package uk.ac.cardiff.raptorweb.engine.reports;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.core.io.Resource;

import uk.ac.cardiff.raptorweb.model.GraphModel;
import uk.ac.cardiff.raptorweb.model.ReportModel;

/**
 * @author philsmart
 *
 */
public class ReportHandler {

	static Logger log = Logger.getLogger(ReportHandler.class);

	private List<ReportConstructor> reportConstructors;


	public void generateReport(GraphModel model, String reportType, ReportModel report){
		for (ReportConstructor reportConstructor : reportConstructors){
			log.debug("finding report constructor for "+reportType+" with "+reportConstructor.getHandledReportType().toString());
			if (reportConstructor.getHandledReportType().toString().equals(reportType)){
				reportConstructor.generateReport(model,report);
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
	public void loadSavedReports(ReportModel report) {
		for (ReportConstructor reportConstructor : reportConstructors){
			Resource directory = reportConstructor.getSaveDirectory();
			Resource baseDirectory = reportConstructor.getBaseDirectory();
			String fileExtension = reportConstructor.getHandledReportType().fileExtension;

			File dir;
			try {
				dir = directory.getFile();
				File[] dirList = dir.listFiles();
				for (File file : dirList){
					if (file.getName().endsWith("."+fileExtension)){
						String relativePath = file.getAbsolutePath().replace(baseDirectory.getFile().getParentFile().getAbsolutePath(),"");
						report.addReportForDownload(file, relativePath);
					}
				}
			}
			catch (IOException e) {
				log.error("Could not read any file for ReportConstructor "+reportConstructor+" -> "+e.getMessage());
			}

		}
	}



}
