
package uk.ac.cardiff.raptor.store;

/**
 * Information that describes the result of a storage transaction.
 * 
 */
public class StorageResult {

    /** Whether the storage task succeeded. */
    private boolean success;

    /** A numerical id for the storage task. */
    private int transactionId;

    /**
     * @param success the success to set
     */
    public void setSuccess(boolean success) {
        this.success = success;
    }

    /**
     * @return the success
     */
    public boolean isSuccess() {
        return success;
    }

    /**
     * @param transactionId the transactionId to set
     */
    public void setTransactionId(int transactionId) {
        this.transactionId = transactionId;
    }

    /**
     * @return the transactionId
     */
    public int getTransactionId() {
        return transactionId;
    }

}
