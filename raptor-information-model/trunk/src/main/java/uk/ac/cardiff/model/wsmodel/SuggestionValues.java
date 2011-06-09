package uk.ac.cardiff.model.wsmodel;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SuggestionValues implements Serializable {

    /**
     * default serial UId
     */
    private static final long serialVersionUID = 7312337340123235532L;

    /** Class logger */
    private final Logger log = LoggerFactory.getLogger(SuggestionValues.class);

    /** The names of all fields from the information model*/
    private List<Suggestion> possibleFieldNameValues;
    
    /** The names of all known resourceIds*/
    private List<String> possibleResourceIds;

    public void setPossibleFieldNameValues(List<Suggestion> possibleFieldNameValues) {
        this.possibleFieldNameValues = possibleFieldNameValues;
    }

    public List<Suggestion> getPossibleFieldNameValues() {
        return possibleFieldNameValues;
    }

    public List<String> getPossibleFieldNameValuesList() {
        ArrayList<String> fields = new ArrayList<String>();
        if (possibleFieldNameValues == null)
            return fields;
        for (Suggestion entry : possibleFieldNameValues) {
            fields.add(entry.getValue());
        }
        return fields;
    }

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
    
    public ArrayList<String> autocomplete(Object suggest){
        return (ArrayList<String>) possibleResourceIds;
    }

    /**
     * @param possibleResourceIds the possibleResourceIds to set
     */
    public void setPossibleResourceIds(List<String> possibleResourceIds) {
        this.possibleResourceIds = possibleResourceIds;
    }

    /**
     * @return the possibleResourceIds
     */
    public List<String> getPossibleResourceIds() {
        return possibleResourceIds;
    }

}
