package uk.ac.cardiff.raptorweb.model;

import java.io.Serializable;

public class CommonModel implements Serializable{

	private String downloadURL;

	public void setDownloadURL(String downloadURL) {
		this.downloadURL = downloadURL;
	}

	public String getDownloadURL() {
		return downloadURL;
	}



}
