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

import uk.ac.cardiff.model.event.Event;

/**
 * A concrete event type used in Raptor.
 * 
 * @author philsmart
 * 
 */
public class RegisteredEventType {

    /**
     * The Class type of the event, which must be a type of {@link Event}.
     */
    private Class<? extends Event> eventType;

    /**
     * Is this class a concrete class e.g. is it actually instantiated with event information.
     */
    private boolean concrete;

    /**
     * @param eventType the eventType to set
     */
    public void setEventType(Class<? extends Event> eventType) {
        this.eventType = eventType;
    }

    /**
     * @return the eventType
     */
    public Class<? extends Event> getEventType() {
        return eventType;
    }

    /**
     * @param concrete the concrete to set
     */
    public void setConcrete(boolean concrete) {
        this.concrete = concrete;
    }

    /**
     * @return the concrete
     */
    public boolean isConcrete() {
        return concrete;
    }

}
