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

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import uk.ac.cardiff.model.wsmodel.LogFileUpload.ParsingEventType;

/**
 * @author philsmart
 * 
 */
public class UploadDirectory {

    /** Class Logger */
    private static final Logger log = LoggerFactory.getLogger(UploadDirectory.class);

    /** The directory where the upload files exist */
    private File uploadDirectory;

    /** The file extensions for allowed files */
    private String[] allowedFileExtensions;

    /** The event type this file should be parsed as */
    private ParsingEventType eventType;

    public List<BatchFile> getNewFiles() throws UploadFileException {
        if (uploadDirectory.isDirectory()) {
            ArrayList<BatchFile> filesToUpload = new ArrayList<BatchFile>();

            File[] files = uploadDirectory.listFiles();
            for (File file : files) {
                for (String extension : allowedFileExtensions) {
                    if (file.getName().endsWith(extension)) {
                        log.info("Parsing file {}", file);
                        BatchFile bFile = new BatchFile();
                        bFile.setLogFile(file);
                        bFile.setEventType(eventType);
                        filesToUpload.add(bFile);
                    }
                }
            }

            return filesToUpload;

        } else {
            throw new UploadFileException("Upload Directory " + uploadDirectory
                    + " does not exist or is not a directory");
        }
    }

    /**
     * @param allowedFileExtensions the allowedFileExtensions to set
     */
    public void setAllowedFileExtensions(String[] allowedFileExtensions) {
        this.allowedFileExtensions = allowedFileExtensions;
    }

    /**
     * @return the allowedFileExtensions
     */
    public String[] getAllowedFileExtensions() {
        return allowedFileExtensions;
    }

    /**
     * @param uploadDirectory the uploadDirectory to set
     */
    public void setUploadDirectory(File uploadDirectory) {
        this.uploadDirectory = uploadDirectory;
    }

    /**
     * @return the uploadDirectory
     */
    public File getUploadDirectory() {
        return uploadDirectory;
    }

    /**
     * @param eventType the eventType to set
     */
    public void setEventType(ParsingEventType eventType) {
        this.eventType = eventType;
    }

    /**
     * @return the eventType
     */
    public ParsingEventType getEventType() {
        return eventType;
    }

}
