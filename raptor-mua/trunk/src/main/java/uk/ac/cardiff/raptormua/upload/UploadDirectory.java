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
import uk.ac.cardiff.model.wsmodel.StatisticParameters.EventType;

/**
 * @author philsmart
 *
 */
public class UploadDirectory {
    
    /** Class Logger */
    private static final Logger log = LoggerFactory.getLogger(UploadDirectory.class);
    
    /** The directory where the upload files exist*/
    private File uploadDirectory;  
    
    /** The file extensions for allowed files */
    private String[] allowedFileExtensions;
    
    /** The event type this file should be parsed as*/
    private ParsingEventType eventType;
    
    public List<BatchFile> getNewFiles() throws UploadFileException{            
        if (uploadDirectory.isDirectory()){
            ArrayList<BatchFile> filesToUpload = new ArrayList<BatchFile>();
            
            File[] files = uploadDirectory.listFiles();
            for (File file : files){
                for (String extension : allowedFileExtensions){
                    if (file.getName().endsWith(extension)){
                        log.info("Parsing file {}",file);
                        BatchFile bFile = new BatchFile();
                        bFile.setLogFile(file);
                        bFile.setEventType(eventType);
                        filesToUpload.add(bFile);                        
                    }
                }
            }
            
            return filesToUpload;
            
        }
        else{
            throw new UploadFileException("Upload Directory "+uploadDirectory+" is not a directory");
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
