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

package uk.ac.cardiff.raptor.remoting.policy;

import java.util.List;

import uk.ac.cardiff.model.event.Event;

/**
 * @author philsmart
 * 
 */
public interface PushPolicy {

    /**
     * Determines if the list of <code>events</code> should be sent to an endpoint
     * 
     * @param events the list of events to evaluate
     * @return true iff the concrete implementation of this push policy determines the list of events are appropriate to
     *         send to an endpoint, false otherwise
     */
    public boolean evaluatePolicy(final List<Event> events);

}
