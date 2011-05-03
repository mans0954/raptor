/**
 *
 */
package uk.ac.cardiff.raptor.parse;

/**
 * @author philsmart
 *
 */
public class EventParserNotFoundException extends Exception {

    /** Generated Serial UID*/
    private static final long serialVersionUID = -5099335229694795151L;

    /** Constructor. */
    public EventParserNotFoundException() {
        super();
    }

    /**
     * Constructor.
     *
     * @param message exception message
     */
    public EventParserNotFoundException(final String message) {
        super(message);
    }

    /**
     * Constructor.
     *
     * @param wrappedException exception to be wrapped by this one
     */
    public EventParserNotFoundException(final Exception wrappedException) {
        super(wrappedException);
    }

    /**
     * Constructor.
     *
     * @param message exception message
     * @param wrappedException exception to be wrapped by this one
     */
    public EventParserNotFoundException(final String message, final Exception wrappedException) {
        super(message, wrappedException);
    }

}
