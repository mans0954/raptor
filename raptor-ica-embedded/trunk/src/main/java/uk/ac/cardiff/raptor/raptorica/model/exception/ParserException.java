/**
 *
 */
package uk.ac.cardiff.raptor.raptorica.model.exception;

/**
 * @author philsmart
 *
 */
public class ParserException extends Exception{

    /** Generated SerialUID */
    private static final long serialVersionUID = 5099204480722977726L;

    public ParserException(String message){
	super(message);
    }

    public ParserException(String message, Exception e){
	super(message,e);
    }

}
