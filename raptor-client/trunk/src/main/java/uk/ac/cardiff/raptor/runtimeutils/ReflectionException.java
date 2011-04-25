package uk.ac.cardiff.raptor.runtimeutils;

public class ReflectionException extends Exception{
	
	/** Generated serial UID	 */
	private static final long serialVersionUID = -5563584524845646074L;

	public ReflectionException(String message){
		super (message);
	}
	
	public ReflectionException(String message, Exception wrappedException){
		super(message,wrappedException);
	}

}
