/**
 *
 */
package uk.ac.cardiff.model.wsmodel;

/**
 * @author philsmart
 *
 */
public class LogFileUploadResult {

    private long id;

    private String status;

    private boolean processed;

    /**
     * @param id the id to set
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * @return the id
     */
    public long getId() {
        return id;
    }

    /**
     * @param status the status to set
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * @return the status
     */
    public String getStatus() {
        return status;
    }

    /**
     * @param processed the processed to set
     */
    public void setProcessed(boolean processed) {
        this.processed = processed;
    }

    /**
     * @return the processed
     */
    public boolean isProcessed() {
        return processed;
    }

}
