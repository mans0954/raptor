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

package uk.ac.cardiff.raptor.parse.external.file.format;

import java.util.List;

/**
 * The Class LogFileFormat.
 * 
 */
public class LogFileFormat {

    /** The delimiter used to separate fields in this record. */
    private String delimeter;

    /** The headers used to parse the fields in this record. */
    private List<Header> headers;

    /**
     * Sets the headers.
     * 
     * @param headers the new headers
     */
    public void setHeaders(List<Header> headers) {
        this.headers = headers;
    }

    /**
     * Gets the headers.
     * 
     * @return the headers
     */
    public List<Header> getHeaders() {
        return headers;
    }

    /**
     * Sets the delimeter.
     * 
     * @param delimeter the new delimeter
     */
    public void setDelimeter(String delimeter) {
        this.delimeter = delimeter;
    }

    /**
     * Gets the delimeter.
     * 
     * @return the delimeter
     */
    public String getDelimeter() {
        return delimeter;
    }

}
