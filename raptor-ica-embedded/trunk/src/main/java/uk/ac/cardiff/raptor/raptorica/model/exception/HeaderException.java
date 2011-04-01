/**
 *
 */
package uk.ac.cardiff.raptor.raptorica.model.exception;

/**
 * @author philsmart
 *
 */
public class HeaderException extends Exception{

    /** Generate SerialUID*/
    private static final long serialVersionUID = 1717192879246765634L;

    private int headerNo;

    public HeaderException(String message, int headerNo, Exception e){
	super(message,e);
	this.headerNo = headerNo;
    }

    public HeaderException(String message, int headerNo){
	super(message);
	this.headerNo = headerNo;
    }

    /**
     * @return the headerNo
     */
    public int getHeaderNo() {
	return headerNo;
    }

}
