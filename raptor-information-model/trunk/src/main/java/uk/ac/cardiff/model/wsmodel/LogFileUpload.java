package uk.ac.cardiff.model.wsmodel;

import java.io.Serializable;

/**
 * The Class LogFileUpload.
 */
public class LogFileUpload implements Serializable {

    /** Generated Serial UID. */
    private static final long serialVersionUID = -6283086431616419079L;

    /** The filename of the file that has been uploaded. */
    private String Name;

    /** The mime type. */
    private String mime;

    /** The length of the file. */
    private long length;

    /** Random ID that we can use for tracking. */
    private long id;

    /** The contents of the file in <code>bytes</code>. */
    private byte[] data;

    /**
     * The Enum ParsingEventType.
     */
    public enum ParsingEventType {

        /** The NA. */
        NA("Not Selected. Please Select"),

        /** The SHIBBOLET h13. */
        SHIBBOLETH13("Shibboleth 1.3"),

        /** The SHIBBOLET h2. */
        SHIBBOLETH2("Shibboleth 2"),

        /** The EZPROXY. */
        EZPROXY("Ezproxy");

        /** The friendly name. */
        public String friendlyName;

        /**
         * Instantiates a new parsing event type.
         * 
         * @param friendlyName
         *            the friendly name
         */
        ParsingEventType(String friendlyName) {
            this.friendlyName = friendlyName;
        }
    }

    /** The type of event that this file upload represents. */
    private ParsingEventType eventType;

    /**
     * Store <code>String</code> messages reflecting, in human readable form, the current processing status of this batch file.
     */
    private String processingStatus;

    /** Whether this file has be proccessed (parsed and stored). */
    private boolean processed;

    /**
     * Instantiates a new log file upload.
     */
    public LogFileUpload() {
        eventType = ParsingEventType.NA;
    }

    /**
     * Gets the data.
     * 
     * @return the data
     */
    public byte[] getData() {
        return data;
    }

    /**
     * Sets the data.
     * 
     * @param data
     *            the new data
     */
    public void setData(byte[] data) {
        this.data = data;
    }

    /**
     * Gets the name.
     * 
     * @return the name
     */
    public String getName() {
        return Name;
    }

    /**
     * Sets the name.
     * 
     * @param name
     *            the new name
     */
    public void setName(String name) {
        Name = name;
        int extDot = name.lastIndexOf('.');
        if (extDot > 0) {
            String extension = name.substring(extDot + 1);
            if ("txt".equals(extension)) {
                mime = "text/plain";
            } else if ("log".equals(extension)) {
                mime = "text/plain";
            } else {
                mime = "image/unknown";
            }
        }
    }

    /**
     * Gets the length.
     * 
     * @return the length
     */
    public long getLength() {
        return length;
    }

    /**
     * Sets the length.
     * 
     * @param length
     *            the new length
     */
    public void setLength(long length) {
        this.length = length;
    }

    /**
     * Gets the mime.
     * 
     * @return the mime
     */
    public String getMime() {
        return mime;
    }

    /**
     * Sets the mime.
     * 
     * @param mime
     *            the new mime
     */
    public void setMime(String mime) {
        this.mime = mime;
    }

    /**
     * Sets the processing status.
     * 
     * @param processingStatus
     *            the new processing status
     */
    public void setProcessingStatus(String processingStatus) {
        this.processingStatus = processingStatus;

    }

    /**
     * Gets the processing status.
     * 
     * @return the processing status
     */
    public String getProcessingStatus() {
        return processingStatus;
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

    /**
     * Sets the event type.
     * 
     * @param eventType
     *            the eventType to set
     */
    public void setEventType(ParsingEventType eventType) {
        this.eventType = eventType;
    }

    /**
     * Sets the event type string.
     * 
     * @param eventType
     *            the new event type string
     */
    public void setEventTypeString(String eventType) {
        for (ParsingEventType pet : ParsingEventType.values()) {
            if (pet.friendlyName.equals(eventType)) {
                this.eventType = pet;
            }
        }
    }

    /**
     * Gets the event type.
     * 
     * @return the eventType
     */
    public ParsingEventType getEventType() {
        return eventType;
    }

    /**
     * Gets the event type string.
     * 
     * @return the event type string
     */
    public String getEventTypeString() {
        return eventType.friendlyName;
    }

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

}
