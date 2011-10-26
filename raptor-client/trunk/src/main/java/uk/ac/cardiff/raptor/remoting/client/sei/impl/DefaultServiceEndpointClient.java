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

package uk.ac.cardiff.raptor.remoting.client.sei.impl;

import uk.ac.cardiff.model.wsmodel.EventPushMessage;
import uk.ac.cardiff.raptor.registry.Endpoint;
import uk.ac.cardiff.raptor.remoting.client.sei.ServiceEndpointClient;

public class DefaultServiceEndpointClient implements ServiceEndpointClient {

    /*
     * (non-Javadoc)
     * 
     * @see uk.ac.cardiff.raptor.remoting.client.sei.ServiceEndpointClient#sendEvents(uk.ac.cardiff.model.wsmodel.
     * EventPushMessage, uk.ac.cardiff.raptor.registry.Endpoint)
     */
    @Override
    public boolean sendEvents(EventPushMessage pushed, Endpoint endpoint) {
        // TODO a default?
        return false;
    }

}
