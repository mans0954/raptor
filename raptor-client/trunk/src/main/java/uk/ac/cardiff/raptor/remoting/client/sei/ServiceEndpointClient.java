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

package uk.ac.cardiff.raptor.remoting.client.sei;

import uk.ac.cardiff.model.wsmodel.EventPushMessage;
import uk.ac.cardiff.raptor.registry.Endpoint;

public interface ServiceEndpointClient {

    /**
     * Sends the <code>EventPushMessage</code> to the endpoint <code>endpoint</code>
     * 
     * @param pushed the <code>EventPushMessage</code> to send to the <code>endpoint</code>
     * @param endpoint the endpoint to send the <code>EventPushMessage</code> to
     * @return true iff event release was successful, false otherwise. Note, this describes success of the transmission
     *         to the client, and not reporting successful processing of that information within the client.
     */
    public boolean sendEvents(final EventPushMessage pushed, final Endpoint endpoint);

}
