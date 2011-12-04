package uk.ac.cardiff.model.wsmodel;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The Class SuggestionValues.
 */
public class SuggestionValues implements Serializable {

    /** default serial UId. */
    private static final long serialVersionUID = 7312337340123235532L;

    /** Class logger. */
    private final Logger log = LoggerFactory.getLogger(SuggestionValues.class);

    /** The names of all fields from the information model. */
    private List<Suggestion> possibleFieldNameValues;

    /** The possible values of all known fields from the information model. */
    private List<Suggestion> possibleFieldValues;

    /** The possible post processors. */
    private List<ProcessorInformation> possiblePostProcessors;

    /**
     * Sets the possible field name values.
     * 
     * @param possibleFieldNameValues
     *            the new possible field name values
     */
    public void setPossibleFieldNameValues(List<Suggestion> possibleFieldNameValues) {
        this.possibleFieldNameValues = possibleFieldNameValues;
    }

    /**
     * Gets the possible field name values.
     * 
     * @return the possible field name values
     */
    public List<Suggestion> getPossibleFieldNameValues() {
        return possibleFieldNameValues;
    }

    /**
     * Gets the possible field name values list.
     * 
     * @return the possible field name values list
     */
    public List<String> getPossibleFieldNameValuesList() {
        ArrayList<String> fields = new ArrayList<String>();
        if (possibleFieldNameValues == null)
            return fields;
        for (Suggestion entry : possibleFieldNameValues) {
            fields.add(entry.getValue());
        }
        return fields;
    }

    /**
     * Gets the possible field name values list.
     * 
     * @param classFilters
     *            the class filters
     * @return the possible field name values list
     */
    public List<String> getPossibleFieldNameValuesList(String[] classFilters) {
        ArrayList<String> fields = new ArrayList<String>();
        if (possibleFieldNameValues == null)
            return fields;
        for (Suggestion entry : possibleFieldNameValues) {
            for (String filter : classFilters) {
                if (filter.equals(entry.getBase())) {
                    fields.add(entry.getValue());
                }
            }
        }
        return fields;
    }

    /**
     * Autocomplete.
     * 
     * @param fieldName
     *            the field name
     * @return the array list
     */
    public ArrayList<String> autocomplete(String fieldName) {
        log.debug("Suggesting values for selectedfield {}", fieldName);
        ArrayList<String> possibles = new ArrayList<String>();
        for (Suggestion suggestion : possibleFieldValues) {
            if (suggestion.getBase().equals(fieldName)) {
                log.debug("Adding possible value {}", suggestion.getValue());
                possibles.add(suggestion.getValue());
            }
        }
        return possibles;
    }

    /**
     * Sets the possible field values.
     * 
     * @param possibleFieldValues
     *            the possibleFieldValues to set
     */
    public void setPossibleFieldValues(List<Suggestion> possibleFieldValues) {
        this.possibleFieldValues = possibleFieldValues;
    }

    /**
     * Gets the possible field values.
     * 
     * @return the possibleFieldValues
     */
    public List<Suggestion> getPossibleFieldValues() {
        return possibleFieldValues;
    }

    /**
     * @param possiblePostProcessors the possiblePostProcessors to set
     */
    public void setPossiblePostProcessors(List<ProcessorInformation> possiblePostProcessors) {
        this.possiblePostProcessors = possiblePostProcessors;
    }

    /**
     * @return the possiblePostProcessors
     */
    public List<ProcessorInformation> getPossiblePostProcessors() {
        return possiblePostProcessors;
    }

}
