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

package uk.ac.cardiff.raptorweb.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.faces.model.SelectItem;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import uk.ac.cardiff.model.wsmodel.EventTypeInformation;
import uk.ac.cardiff.raptorweb.engine.RaptorWebEngine;
import uk.ac.cardiff.raptorweb.model.EventTypeDisplayMapper;
import uk.ac.cardiff.raptorweb.service.EventTypeService;

public class EventTypeServiceImpl implements EventTypeService {

    /** Class logger */
    private final Logger log = LoggerFactory.getLogger(EventTypeServiceImpl.class);

    /** The engine that is delegated to for all common internal functions of RaptorWeb */
    private RaptorWebEngine webEngine;

    /** Maps event type class names to friendy UI names. */
    private EventTypeDisplayMapper eventTypeToDisplayMapper;

    /**
     * Gets a list of event types from the attached MUA's capabilities, and places them inside SelectItems for the
     * UI.The label is not the event type class name but a friendly name from the <code>eventTypeToDisplayMapper</code>.
     * Importantly, if the event type is not in the <code>eventTypeToDisplayMapper</code> list, it is not returned.
     * Consequently, the set of event types to display are only those specified in the
     * <code>eventTypeToDisplayMapper</code>.
     */
    @Override
    public List<SelectItem> getEventTypeList() {
        log.debug("Getting event type list as a list of select items");
        List<SelectItem> eventTypes = new ArrayList<SelectItem>();

        if (webEngine.getAttachedCapabilities() != null) {
            List<EventTypeInformation> eventTypesFromAttached = webEngine.getAttachedCapabilities().getEventsPerType();
            if (eventTypesFromAttached != null) {
                for (EventTypeInformation eventType : eventTypesFromAttached) {
                    String eventTypeString = eventType.getEventTypeName();
                    SelectItem item = new SelectItem();

                    String labelValue = eventTypeToDisplayMapper.mapEventType(eventTypeString);

                    if (labelValue != null) {
                        item.setLabel(labelValue);
                        item.setValue(eventTypeString);
                        log.debug("Setting event value to: " + item.getValue());
                        eventTypes.add(item);
                    }

                }
            }
        }
        return eventTypes;
    }

    /**
     * @param webEngine the webEngine to set
     */
    public void setWebEngine(RaptorWebEngine webEngine) {
        this.webEngine = webEngine;
    }

    /**
     * @param eventTypeToDisplayMapper the eventTypeToDisplayMapper to set
     */
    public void setEventTypeToDisplayMapper(EventTypeDisplayMapper eventTypeToDisplayMapper) {
        this.eventTypeToDisplayMapper = eventTypeToDisplayMapper;
    }

}
