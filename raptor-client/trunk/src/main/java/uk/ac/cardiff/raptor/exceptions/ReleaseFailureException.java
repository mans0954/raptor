package uk.ac.cardiff.raptor.exceptions;

public class ReleaseFailureException extends Exception{

	/**
	 * Generated serialUID
	 */
	private static final long serialVersionUID = -1091443418866759066L;
	
	public ReleaseFailureException (String message, Exception e){
		super (message,e);
	}

}
