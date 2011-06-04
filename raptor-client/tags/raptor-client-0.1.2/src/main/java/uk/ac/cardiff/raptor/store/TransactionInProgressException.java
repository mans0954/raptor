/**
 * Copyright (C) 2010 Cardiff University, Wales <smartp@cf.ac.uk>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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
