/**
 * 
 */

package uk.ac.cardiff.raptormua.mail;

import java.io.Serializable;

/**
 * @author philsmart
 * 
 */
public class BaseEmailContext implements Serializable {

    /**
     * Generated SerialUID
     */
    private static final long serialVersionUID = -4980559171828172516L;

    private String[] emailAddress;

    /**
     * @param emailAddress the emailAddress to set
     */
    public void setEmailAddress(String[] emailAddress) {
        this.emailAddress = emailAddress;
    }

    /**
     * @return the emailAddress
     */
    public String[] getEmailAddress() {
        return emailAddress;
    }

}
