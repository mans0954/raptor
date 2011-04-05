/**
 *
 */
package uk.ac.cardiff.raptorweb.model;

import java.io.Serializable;

/**
 * @author philsmart
 *
 */
public class MUAEntry implements Serializable {

    private String serviceEndpoint;
    private boolean isAttached;

    /** They public key used to encrypt messages sent to this endpoint */
    private String publicKey;

    /** The password used to access the public key */
    private String publicKeyPassword;

    public void setServiceEndpoint(String serviceEndpoint) {
	this.serviceEndpoint = serviceEndpoint;
    }

    public String getServiceEndpoint() {
	return serviceEndpoint;
    }

    public void setIsAttached(boolean isAttached) {
	this.isAttached = isAttached;
    }

    public boolean getIsAttached() {
	return isAttached;
    }

    public String toString() {
	StringBuilder result = new StringBuilder();
	result.append(this.getClass().getName());
	result.append("Object{");
	result.append(serviceEndpoint);
	result.append("}");
	return result.toString();
    }

    /**
     * @param publicKey the publicKey to set
     */
    public void setPublicKey(String publicKey) {
	this.publicKey = publicKey;
    }

    /**
     * @return the publicKey
     */
    public String getPublicKey() {
	return publicKey;
    }

    /**
     * @param publicKeyPassword the publicKeyPassword to set
     */
    public void setPublicKeyPassword(String publicKeyPassword) {
	this.publicKeyPassword = publicKeyPassword;
    }

    /**
     * @return the publicKeyPassword
     */
    public String getPublicKeyPassword() {
	return publicKeyPassword;
    }

}
