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

package uk.ac.cardiff.raptor.registry;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * All event types used in Raptor are registered here. This class should be a singleton and injected into any class that
 * requires knowledge of 'usable' event types.
 * 
 * @author philsmart
 * 
 */
public final class EventTypeRegistry {

    /**
     * The list of {@link RegisteredEventType}s.
     */
    private List<RegisteredEventType> registeredEventTypes;

    /**
     * Returns only those {@link RegisteredEventType}s from the list <code>registeredEventTypes</code> that are concrete
     * event types (instantiated).
     * 
     * @return a list of concrete registered event types.
     */
    public final List<RegisteredEventType> getAllConcreteEventTypes() {
        List<RegisteredEventType> returnEventTypes = new ArrayList<RegisteredEventType>();
        for (RegisteredEventType eventType : registeredEventTypes) {
            if (eventType.isConcrete()) {
                returnEventTypes.add(eventType);
            }
        }
        return returnEventTypes;
    }

    /**
     * @param registeredEventTypes the registeredEventTypes to set
     */
    public void setRegisteredEventTypes(List<RegisteredEventType> registeredEventTypes) {
        this.registeredEventTypes = registeredEventTypes;
    }

    /**
     * @return an unmodifiable list of the registeredEventTypes
     */
    public List<RegisteredEventType> getRegisteredEventTypes() {
        return Collections.unmodifiableList(registeredEventTypes);
    }

}
