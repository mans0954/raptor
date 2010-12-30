/**
 *
 */
package uk.ac.cardiff.raptorweb.model;

import java.io.File;
import java.io.Serializable;

/**
 * @author philsmart
 *
 */
public class DownloadFile implements Serializable{

	private File file;
	private String friendlyName;
	private String downloadPath;

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

}
