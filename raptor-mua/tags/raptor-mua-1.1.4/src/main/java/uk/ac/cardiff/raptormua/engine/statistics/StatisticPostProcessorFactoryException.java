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

package uk.ac.cardiff.raptormua.engine.statistics;

public class StatisticPostProcessorFactoryException extends Exception {

    /**
     * Generated serial UID.
     */
    private static final long serialVersionUID = -4614661463043866258L;

    /** Constructor. */
    public StatisticPostProcessorFactoryException() {
        super();
    }

    /**
     * Constructor.
     * 
     * @param message exception message
     */
    public StatisticPostProcessorFactoryException(final String message) {
        super(message);
    }

    /**
     * Constructor.
     * 
     * @param wrappedException exception to be wrapped by this one
     */
    public StatisticPostProcessorFactoryException(final Exception wrappedException) {
        super(wrappedException);
    }

    /**
     * Constructor.
     * 
     * @param message exception message
     * @param wrappedException exception to be wrapped by this one
     */
    public StatisticPostProcessorFactoryException(final String message, final Exception wrappedException) {
        super(message, wrappedException);
    }

}
