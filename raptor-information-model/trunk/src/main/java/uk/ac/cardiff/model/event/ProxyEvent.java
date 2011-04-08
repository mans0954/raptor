/**
 *
 */
package uk.ac.cardiff.model.event;

import org.joda.time.DateTime;


/**
 * @author philsmart
 *
 */
public class ProxyEvent extends Event{

    private String requestURL;
    private int httpResponseCode;
    private long responseSize;
    private DateTime responseTime;
    private String requesterIp;


    public void setRequestURL(String requestURL) {
	this.requestURL = requestURL;
    }
    public String getRequestURL() {
	return requestURL;
    }
    public void setHttpResponseCode(int httpResponseCode) {
	this.httpResponseCode = httpResponseCode;
    }
    public int getHttpResponseCode() {
	return httpResponseCode;
    }
    public void setResponseSize(long responseSize) {
	this.responseSize = responseSize;
    }
    public long getResponseSize() {
	return responseSize;
    }
    public void setResponseTime(DateTime responseTime) {
	this.responseTime = responseTime;
    }
    public DateTime getResponseTime() {
	return responseTime;
    }
    /**
     * @param requesterIp the requesterIp to set
     */
    public void setRequesterIp(String requesterIp) {
	this.requesterIp = requesterIp;
    }
    /**
     * @return the requesterIp
     */
    public String getRequesterIp() {
	return requesterIp;
    }

}
