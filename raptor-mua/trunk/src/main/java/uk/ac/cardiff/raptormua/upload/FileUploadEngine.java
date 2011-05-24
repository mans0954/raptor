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
     * @return null if no files were found, a <code>List</code> of {@link uk.ac.cardiff.raptormua.upload.BatchFile} otherwise
     */
    public List<BatchFile> scanDirectories(){
        if (!enabled){
            return null;
        }
        log.debug("Scanning directories for batch log files");
        ArrayList<BatchFile> allBatchFiles  = new ArrayList<BatchFile>();
        for (UploadDirectory uploadDirectory : uploadDirectories){
        	log.debug("Trying directory {} for upload files",uploadDirectory.getUploadDirectory());
            try {
            	allBatchFiles.addAll(uploadDirectory.getNewFiles());
            } catch (UploadFileException e) {
                log.error("Problem uploading files from directory, {}",e.getMessage());
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
