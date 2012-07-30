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
package uk.ac.cardiff.raptorweb.model;

import java.io.File;
import java.io.Serializable;
import java.util.Date;

/**
 * The Class DownloadFile.
 * 
 * @author philsmart
 */
public class DownloadFile implements Serializable {

    /**
     * Generated Serial UID.
     */
    private static final long serialVersionUID = 5552980503919792959L;

    /** The file. */
    private File file;

    /** The friendly name. */
    private String friendlyName;

    /** The download path. To be appended to the context path of the servlet. */
    private String downloadPath;

    /** The created date. */
    private Date createdDate;

    /** The type display name. */
    private String typeDisplayName;

    /**
     * Sets the file.
     * 
     * @param file
     *            the new file
     */
    public void setFile(File file) {
        this.file = file;
        friendlyName = file.getName();
    }

    /**
     * Gets the file.
     * 
     * @return the file
     */
    public File getFile() {
        return file;
    }

    /**
     * Gets the friendly name.
     * 
     * @return the friendly name
     */
    public String getFriendlyName() {
        return friendlyName;
    }

    /**
     * Sets the download path.
     * 
     * @param downloadPath
     *            the new download path
     */
    public void setDownloadPath(String downloadPath) {
        this.downloadPath = downloadPath;
    }

    /**
     * Gets the download path.
     * 
     * @return the download path
     */
    public String getDownloadPath() {

        return downloadPath;
    }

    /**
     * Sets the created date.
     * 
     * @param createdDate
     *            the new created date
     */
    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    /**
     * Gets the created date.
     * 
     * @return the created date
     */
    public Date getCreatedDate() {
        return createdDate;
    }

    /**
     * Sets the type display name.
     * 
     * @param typeDisplayName
     *            the new type display name
     */
    public void setTypeDisplayName(String typeDisplayName) {
        this.typeDisplayName = typeDisplayName;
    }

    /**
     * Gets the type display name.
     * 
     * @return the type display name
     */
    public String getTypeDisplayName() {
        return typeDisplayName;
    }

}
