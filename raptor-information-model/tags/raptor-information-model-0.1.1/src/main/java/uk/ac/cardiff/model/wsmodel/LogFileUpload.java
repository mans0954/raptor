package uk.ac.cardiff.model.wsmodel;

import java.io.Serializable;


public class LogFileUpload implements Serializable{

    /** Generated Serial UID */
    private static final long serialVersionUID = -6283086431616419079L;

    private String Name;
    private String mime;
    private long length;

    /** Random ID that we can use for tracking */
    private long id;

    /** The contents of the file in <code>bytes</code>*/
    private byte[] data;

    public enum ParsingEventType{
        NA("Not Selected. Please Select"),
        SHIBBOLETH13("Shibboleth 1.3"),
        SHIBBOLETH2("Shibboleth 2"),
        EZPROXY("Ezproxy");

        public String friendlyName;

        ParsingEventType(String friendlyName){
            this.friendlyName = friendlyName;
        }
    }

    /** The type of event that this file upload represents*/
    private ParsingEventType eventType;

    /** Store <code>String</code> messages reflecting, in human readable form,
     * the current processing status of this batch file.
     */
    private String processingStatus;

    private boolean processed;

    public LogFileUpload(){
        eventType = ParsingEventType.NA;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    public String getName() {
        return Name;
    }

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

    public long getLength() {
        return length;
    }

    public void setLength(long length) {
        this.length = length;
    }

    public String getMime() {
        return mime;
    }

    public void setMime(String mime){
        this.mime = mime;
    }


    /**
     * @param processingStatus
     */
    public void setProcessingStatus(String processingStatus) {
        this.processingStatus = processingStatus;

    }

    public String getProcessingStatus(){
        return processingStatus;
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

    /**
     * @param eventType the eventType to set
     */
    public void setEventType(ParsingEventType eventType) {
        this.eventType = eventType;
    }

    public void setEventTypeString(String eventType) {
        for (ParsingEventType pet : ParsingEventType.values()){
            if (pet.friendlyName.equals(eventType)){
                this.eventType = pet;
            }
        }
    }


    /**
     * @return the eventType
     */
    public ParsingEventType getEventType() {
        return eventType;
    }

    public String getEventTypeString() {
        return eventType.friendlyName;
    }

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

}
