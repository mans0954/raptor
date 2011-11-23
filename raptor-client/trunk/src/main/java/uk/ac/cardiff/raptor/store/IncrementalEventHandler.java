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

package uk.ac.cardiff.raptor.store;

/**
 * An incremental event handler should be capable of storing only 'new' events, and capable of removing 'old' events
 * while still be capable of parsing events from the date and time of the last removed event.
 * 
 */
public interface IncrementalEventHandler extends EventHandler {

    /**
     * Rests this <code>IncrementalEntryHandler</code> back to its initial state
     * 
     */
    public void reset();

}
