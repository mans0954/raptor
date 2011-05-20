/**
 * 
 */
package uk.ac.cardiff.raptormua.upload;

/**
 * @author philsmart
 * 
 */
public class UploadFileException extends Exception {
    
    
    /** Generated Serial UID    */
    private static final long serialVersionUID = 7228940359808615753L;

    public UploadFileException(String message) {
        super(message);

    }

    public UploadFileException(Throwable cause) {
        super(cause);

    }

    public UploadFileException(String message, Throwable cause) {
        super(message, cause);
    }

    public UploadFileException(String message, int errorId, Throwable cause) {
        super(message, cause);

    }

}
