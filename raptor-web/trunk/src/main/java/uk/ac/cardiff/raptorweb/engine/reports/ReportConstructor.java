/**
 *
 */
package uk.ac.cardiff.raptorweb.engine.reports;

import org.springframework.core.io.Resource;

import uk.ac.cardiff.raptorweb.model.GraphModel;
import uk.ac.cardiff.raptorweb.model.ReportModel;
import uk.ac.cardiff.raptorweb.model.WebSession;

/**
 * @author philsmart
 *
 */
public abstract class ReportConstructor {

	/* the location of the directory within the application that reports are saved to*/
	protected Resource saveDirectory;
	protected Resource baseDirectory;

	/* the enum list of the report types this handler can deal with e.g. excel or csv */
	public enum HandledReportTypes {excel("xls"),csv("csv");

		public String fileExtension;

		HandledReportTypes(String fileExtension){
			this.fileExtension = fileExtension;
		}

	};

	/* the actual stored value of the enum*/
	private HandledReportTypes handledReportType;


	public abstract String generateReport(WebSession session);

	public void setSaveDirectory(Resource saveDirectory) {
		this.saveDirectory = saveDirectory;
	}
	public Resource getSaveDirectory() {
		return saveDirectory;
	}
	public void setBaseDirectory(Resource baseDirectory) {
		this.baseDirectory = baseDirectory;
	}
	public Resource getBaseDirectory() {
		return baseDirectory;
	}


	protected void setHandledReportType(HandledReportTypes handledReportType) {
		this.handledReportType = handledReportType;
	}
	protected HandledReportTypes getHandledReportType() {
		return handledReportType;
	}


}
