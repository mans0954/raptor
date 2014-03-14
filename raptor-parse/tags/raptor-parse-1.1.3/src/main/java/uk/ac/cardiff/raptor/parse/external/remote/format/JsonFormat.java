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

package uk.ac.cardiff.raptor.parse.external.remote.format;

import java.util.List;

import uk.ac.cardiff.raptor.parse.external.file.format.Header;

/**
 *
 */
public class JsonFormat {

    /** The headers used to parse the fields in this record. */
    private List<Header> headers;

    /**
     * Events from WS may not come with explicit event time, so use current event time when storing as
     * <code>eventTime</code>
     */
    private boolean addEventTimePerEvent;

    /**
     * @return Returns the headers.
     */
    public List<Header> getHeaders() {
        return headers;
    }

    /**
     * @param headers The headers to set.
     */
    public void setHeaders(List<Header> headers) {
        this.headers = headers;
    }

    /**
     * @return Returns the addEventTimePerEvent.
     */
    public boolean isAddEventTimePerEvent() {
        return addEventTimePerEvent;
    }

    /**
     * @param addEventTimePerEvent The addEventTimePerEvent to set.
     */
    public void setAddEventTimePerEvent(boolean addEventTimePerEvent) {
        this.addEventTimePerEvent = addEventTimePerEvent;
    }

}
