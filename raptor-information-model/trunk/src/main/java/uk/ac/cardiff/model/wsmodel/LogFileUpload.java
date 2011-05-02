package uk.ac.cardiff.model.wsmodel;

import java.io.Serializable;

public class LogFileUpload implements Serializable{
    
    /** Generated Serial UID */
    private static final long serialVersionUID = -6283086431616419079L;
    
    private String Name;
    private String mime;
    private long length;
    private byte[] data;

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

}
