/**
 *
 */
package uk.ac.cardiff.model;

/**
 * @author philsmart
 *
 */
public class AuthenticationFailureEvent {

    private String errorCode;
    private String errorType;
    private String errorDescription;


    public void setErrorCode(String errorCode) {
	this.errorCode = errorCode;
    }
    public String getErrorCode() {
	return errorCode;
    }
    public void setErrorType(String errorType) {
	this.errorType = errorType;
    }
    public String getErrorType() {
	return errorType;
    }
    public void setErrorDescription(String errorDescription) {
	this.errorDescription = errorDescription;
    }
    public String getErrorDescription() {
	return errorDescription;
    }

}
