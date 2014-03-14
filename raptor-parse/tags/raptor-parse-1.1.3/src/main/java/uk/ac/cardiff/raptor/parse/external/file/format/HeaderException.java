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

package uk.ac.cardiff.raptor.parse.external.file.format;

/**
 * @author philsmart
 * 
 */
public class HeaderException extends Exception {

    /** Generate SerialUID */
    private static final long serialVersionUID = 1717192879246765634L;

    private int headerNo;

    public HeaderException(String message, int headerNoIn, Exception e) {
        super(message, e);
        headerNo = headerNoIn;
    }

    public HeaderException(String message, int headerNoIn) {
        super(message);
        headerNo = headerNoIn;
    }

    /** Constructor. */
    public HeaderException() {
        super();
    }

    /**
     * Constructor.
     * 
     * @param message exception message
     */
    public HeaderException(final String message) {
        super(message);
    }

    /**
     * Constructor.
     * 
     * @param wrappedException exception to be wrapped by this one
     */
    public HeaderException(final Exception wrappedException) {
        super(wrappedException);
    }

    /**
     * Constructor.
     * 
     * @param message exception message
     * @param wrappedException exception to be wrapped by this one
     */
    public HeaderException(final String message, final Exception wrappedException) {
        super(message, wrappedException);
    }

    /**
     * @param headerNo the headerNo to set
     */
    public void setHeaderNo(int headerNo) {
        this.headerNo = headerNo;
    }

    /**
     * @return the headerNo
     */
    public int getHeaderNo() {
        return headerNo;
    }

}