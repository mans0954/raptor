package uk.ac.cardiff.raptor.event.expansion.connector;


/**
 * @author philsmart
 *
 */
public class AttributeAssociationException extends Exception{

        /** Generated Serial version UID. */
        private static final long serialVersionUID = -3921717206902437845L;

        /** Constructor. */
        public AttributeAssociationException() {
            super();
        }

        /**
         * Constructor.
         *
         * @param message exception message
         */
        public AttributeAssociationException(final String message) {
            super(message);
        }

        /**
         * Constructor.
         *
         * @param wrappedException exception to be wrapped by this one
         */
        public AttributeAssociationException(final Exception wrappedException) {
            super(wrappedException);
        }

        /**
         * Constructor.
         *
         * @param message exception message
         * @param wrappedException exception to be wrapped by this one
         */
        public AttributeAssociationException(final String message, final Exception wrappedException) {
            super(message, wrappedException);
        }

}
