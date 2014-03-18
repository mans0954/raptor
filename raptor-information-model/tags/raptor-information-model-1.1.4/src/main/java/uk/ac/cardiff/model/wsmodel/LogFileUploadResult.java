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
package uk.ac.cardiff.model.wsmodel;

/**
 * The Class LogFileUploadResult.
 * 
 * @author philsmart
 */
public class LogFileUploadResult {

    /** The id of this <code>LogFileUploadResult</code> instance. */
    private long id;

    /** The status message from the agent that processed this message. */
    private String status;

    /** Has the upload successfully been processed. */
    private boolean processed;

    /**
     * Sets the id.
     * 
     * @param id
     *            the id to set
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * Gets the id.
     * 
     * @return the id
     */
    public long getId() {
        return id;
    }

    /**
     * Sets the status.
     * 
     * @param status
     *            the status to set
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * Gets the status.
     * 
     * @return the status
     */
    public String getStatus() {
        return status;
    }

    /**
     * Sets the processed.
     * 
     * @param processed
     *            the processed to set
     */
    public void setProcessed(boolean processed) {
        this.processed = processed;
    }

    /**
     * Checks if is processed.
     * 
     * @return the processed
     */
    public boolean isProcessed() {
        return processed;
    }

}
