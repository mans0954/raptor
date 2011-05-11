/**
 *
 */
package uk.ac.cardiff.raptor.attribute.filtering;

/**
 * @author philsmart
 *
 */
public class AttributeFilterException extends Exception {


    /** Constructor. */
    public AttributeFilterException() {
        super();
    }

    /**
     * Constructor.
     *
     * @param message exception message
     */
    public AttributeFilterException(final String message) {
        super(message);
    }

    /**
     * Constructor.
     *
     * @param wrappedException exception to be wrapped by this one
     */
    public AttributeFilterException(final Exception wrappedException) {
        super(wrappedException);
    }

    /**
     * Constructor.
     *
     * @param message exception message
     * @param wrappedException exception to be wrapped by this one
     */
    public AttributeFilterException(final String message, final Exception wrappedException) {
        super(message, wrappedException);
    }


}
