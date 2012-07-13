package uk.ac.cardiff.raptorweb.service;

import java.util.List;

import javax.faces.model.SelectItem;

/**
 * Service for managing and retrieving information about event types
 * 
 * @author philsmart
 * 
 */
public interface EventTypeService {

    /** Get a list of event types supported by the currently attached MUA. */
    public List<SelectItem> getEventTypeList();

}
