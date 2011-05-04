package uk.ac.cardiff.model.wsmodel;

import java.io.Serializable;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class SuggestionValues implements Serializable{

    /**
     * default serial UId
     */
    private static final long serialVersionUID = 7312337340123235532L;

    private final Logger log = LoggerFactory.getLogger(SuggestionValues.class);

    private List<String> possibleFieldNameValues;


    public void setPossibleFieldNameValues(List<String> possibleFieldNameValues) {
	this.possibleFieldNameValues = possibleFieldNameValues;
    }

    public List<String> getPossibleFieldNameValues() {
	log.debug("There are {} possible fieldname values",possibleFieldNameValues.size());
	return possibleFieldNameValues;
    }

}
