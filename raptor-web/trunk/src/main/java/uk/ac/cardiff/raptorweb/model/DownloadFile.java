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
