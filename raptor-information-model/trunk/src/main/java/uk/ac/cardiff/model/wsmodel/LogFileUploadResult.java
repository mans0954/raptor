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

    /** The status. */
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
