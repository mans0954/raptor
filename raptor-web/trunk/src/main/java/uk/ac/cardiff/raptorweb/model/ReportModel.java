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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author philsmart
 * 
 */
public class ReportModel implements Serializable {

    /** Generated serial UID */
    private static final long serialVersionUID = -8181099012470152241L;

    /** List of files that can be downloaded. */
    private List<DownloadFile> reportsForDownload;

    /** The selected file to download. */
    private DownloadFile selectedDownloadFile;

    /**
     * 
     * @param location
     *            - Location is only used to generate friendly names
     * @param relativePath
     *            - used as a href to download the file
     */
    public void addReportForDownload(File location, String relativePath, Date created, String typeDisplayName) {
        if (reportsForDownload == null)
            reportsForDownload = new ArrayList<DownloadFile>();
        DownloadFile dlFile = new DownloadFile();
        dlFile.setFile(location);
        dlFile.setCreatedDate(created);
        dlFile.setTypeDisplayName(typeDisplayName);
        dlFile.setDownloadPath(relativePath);
        reportsForDownload.add(dlFile);
    }

    public List<DownloadFile> getReportsForDownload() {
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
        if (reportsForDownload != null)
            reportsForDownload.remove(selectedDownloadFile);

    }

}
