/**
 *
 */
package uk.ac.cardiff.raptorweb.model;

import java.io.File;
import java.io.Serializable;
import java.util.Date;

/**
 * @author philsmart
 *
 */
public class DownloadFile implements Serializable{

	private File file;
	private String friendlyName;
	private String downloadPath;
	private Date createdDate;
	private String typeDisplayName;

	public void setFile(File file) {
		this.file = file;
		friendlyName = file.getName();
	}
	public File getFile() {
		return file;
	}
	public String getFriendlyName() {
		return friendlyName;
	}
	public void setDownloadPath(String downloadPath) {
		this.downloadPath = downloadPath;
	}
	public String getDownloadPath() {
		return downloadPath;
	}
	public void setCreatedDate(Date createdDate) {
	    this.createdDate = createdDate;
	}
	public Date getCreatedDate() {
	    return createdDate;
	}
	public void setTypeDisplayName(String typeDisplayName) {
	    this.typeDisplayName = typeDisplayName;
	}
	public String getTypeDisplayName() {
	    return typeDisplayName;
	}

}
