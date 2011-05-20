/**
 * 
 */
package uk.ac.cardiff.raptormua.upload;

import java.io.File;

import uk.ac.cardiff.model.wsmodel.LogFileUpload.ParsingEventType;
import uk.ac.cardiff.model.wsmodel.StatisticParameters.EventType;

/**
 * @author philsmart
 *
 */
public class BatchFile {
    
    /** The logfile to upload */
    private File logFile;
    
    /** The event type this file should be parsed as*/
    private ParsingEventType eventType;

    /**
     * @param logFile the logFile to set
     */
    public void setLogFile(File logFile) {
        this.logFile = logFile;
    }

    /**
     * @return the logFile
     */
    public File getLogFile() {
        return logFile;
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
