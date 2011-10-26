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

package uk.ac.cardiff.raptor.remoting.client;

import java.util.List;

import uk.ac.cardiff.model.ServiceMetadata;
import uk.ac.cardiff.model.event.Event;
import uk.ac.cardiff.raptor.registry.Endpoint;

/**
 * Sends event records to a client endpoint
 * 
 */
public interface EventReleaseClient {

    /**
     * Release the <code>events</code> to the endpoint specified in this clients implementation
     * 
     * @param events the <code>List</code> of events that are to be released
     * @param ServiceMetadata the metadata associated to the sending service
     * @return true of the release was successful and false otherwise
     * @throws ReleaseFailureException
     */
    public boolean release(List<Event> events, ServiceMetadata serviceMetadata) throws ReleaseFailureException;

    /** Used to determine if the event release client has been enabled */
    public boolean isEnabled();

    /**
     * A way of accessing the list of endpoints this client is communicating with
     * 
     * @return a list of endpoints
     */
    public List<Endpoint> getEndpoints();

}
