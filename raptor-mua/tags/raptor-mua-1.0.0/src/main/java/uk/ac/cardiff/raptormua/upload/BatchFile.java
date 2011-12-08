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
