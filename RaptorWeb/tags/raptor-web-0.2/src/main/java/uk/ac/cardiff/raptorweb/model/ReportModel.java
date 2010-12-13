/**
 *
 */
package uk.ac.cardiff.raptorweb.model;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author philsmart
 *
 */
public class ReportModel implements Serializable{


    	private static final long serialVersionUID = -8181099012470152241L;

	private List<DownloadFile> reportsForDownload;
	private DownloadFile selectedDownloadFile;

	/**
	 *
	 * @param location - Location is only used to generate friendly names
	 * @param relativePath - used as a href to download the file
	 */
	public void addReportForDownload(File location, String relativePath){
		if (reportsForDownload==null)reportsForDownload = new ArrayList<DownloadFile>();
		DownloadFile dlFile = new DownloadFile();
		dlFile.setFile(location);
		dlFile.setDownloadPath(relativePath);
		reportsForDownload.add(dlFile);
	}


	public List<DownloadFile> getReportsForDownload(){
		return reportsForDownload;
	}


	public void setSelectedDownloadFile(DownloadFile selectedDownloadFile) {
	    this.selectedDownloadFile = selectedDownloadFile;
	}


	public DownloadFile getSelectedDownloadFile() {
	    return selectedDownloadFile;
	}


	/**
	 * Removes the selectedDownloadFile form the <code>reportsForDownload</code> list
	 */
	public void removeSelectedDownloadFile() {
	    if (reportsForDownload!=null)reportsForDownload.remove(selectedDownloadFile);

	}




}
