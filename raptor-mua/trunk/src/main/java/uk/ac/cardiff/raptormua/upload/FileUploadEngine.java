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

package uk.ac.cardiff.raptormua.upload;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author philsmart
 * 
 */
public class FileUploadEngine {

    /** Class Logger */
    private static final Logger log = LoggerFactory.getLogger(UploadDirectory.class);

    /** List of directories from which to load files from **/
    private List<UploadDirectory> uploadDirectories;

    /** Whether file uploads are enabled */
    private boolean enabled;

    /**
     * 
     * @return null if no files were found, a <code>List</code> of {@link uk.ac.cardiff.raptormua.upload.BatchFile}
     *         otherwise
     */
    public List<BatchFile> scanDirectories() {
        if (!enabled) {
            return null;
        }
        log.trace("Scanning directories for batch log files");
        ArrayList<BatchFile> allBatchFiles = new ArrayList<BatchFile>();
        for (UploadDirectory uploadDirectory : uploadDirectories) {
            log.trace("Trying directory {} for upload files", uploadDirectory.getUploadDirectory());
            try {
                allBatchFiles.addAll(uploadDirectory.getNewFiles());
            } catch (UploadFileException e) {
                log.warn("Problem trying to scan or upload files from directory, {}", e.getMessage());
            }
        }
        return allBatchFiles;
    }

    /**
     * @param uploadDirectories the uploadDirectories to set
     */
    public void setUploadDirectories(List<UploadDirectory> uploadDirectories) {
        this.uploadDirectories = uploadDirectories;
    }

    /**
     * @return the uploadDirectories
     */
    public List<UploadDirectory> getUploadDirectories() {
        return uploadDirectories;
    }

    /**
     * @param enabled the enabled to set
     */
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    /**
     * @return the enabled
     */
    public boolean isEnabled() {
        return enabled;
    }

}
