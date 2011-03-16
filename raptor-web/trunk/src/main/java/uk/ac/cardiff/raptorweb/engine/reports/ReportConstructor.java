/**
 *
 */
package uk.ac.cardiff.raptorweb.engine.reports;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;

import uk.ac.cardiff.raptorweb.model.GraphModel;
import uk.ac.cardiff.raptorweb.model.ReportModel;
import uk.ac.cardiff.raptorweb.model.WebSession;

/**
 * @author philsmart
 *
 */
public abstract class ReportConstructor {
    
    	static Logger log = LoggerFactory.getLogger(CSVReportGenerator.class);

	/* the location of the directory within the application that reports are saved to*/
	protected Resource saveDirectory;
	protected Resource baseDirectory;

	/* the enum list of the report types this handler can deal with e.g. excel or csv */
	public enum HandledReportTypes {excel("xls"),csv("csv"),pdf("pdf");

		public String fileExtension;

		HandledReportTypes(String fileExtension){
			this.fileExtension = fileExtension;
		}

	};

	/* the actual stored value of the enum*/
	private HandledReportTypes handledReportType;
	
	public ReportConstructor(){
	    HandledReportTypes registeredReportType = getRegisterHandledReportType();
	    if (registeredReportType!=null){
		handledReportType = registeredReportType;
	    }
	    else
		log.error("Report Generator did not register a report type");
	}
	
	protected abstract HandledReportTypes getRegisterHandledReportType();


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
