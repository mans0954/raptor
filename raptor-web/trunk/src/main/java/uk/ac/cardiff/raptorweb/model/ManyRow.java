/**
 *
 */
package uk.ac.cardiff.raptorweb.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author philsmart
 *
 */
public class ManyRow<T> implements Serializable {

    static Logger log = LoggerFactory.getLogger(ManyRow.class);

    private String groupLabel;
    private List<T> values;


    public void setGroupLabel(String groupLabel) {
	this.groupLabel = groupLabel;
    }
    public String getGroupLabel() {
	return groupLabel;
    }
    public void setValues(List<T> values) {
	this.values = values;
    }
    public List<T> getValues() {
	return values;
    }

    public void addValue(T value){
	if (values==null) values = new ArrayList<T>();
	values.add(value);
    }



}
