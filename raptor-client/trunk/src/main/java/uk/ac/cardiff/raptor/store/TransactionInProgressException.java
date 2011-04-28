/**
 *
 */
package uk.ac.cardiff.raptor.store;

/**
 * @author philsmart
 *
 */
public class TransactionInProgressException extends Exception {

    /** Generated Serial version UID. */
    private static final long serialVersionUID = 4865109204665564276L;




    /** Constructor. */
    public TransactionInProgressException() {
        super();
    }

    /**
     * Constructor.
     *
     * @param message exception message
     */
    public TransactionInProgressException(final String message) {
        super(message);
    }

    /**
     * Constructor.
     *
     * @param wrappedException exception to be wrapped by this one
     */
    public TransactionInProgressException(final Exception wrappedException) {
        super(wrappedException);
    }

    /**
     * Constructor.
     *
     * @param message exception message
     * @param wrappedException exception to be wrapped by this one
     */
    public TransactionInProgressException(final String message, final Exception wrappedException) {
        super(message, wrappedException);
    }
}
