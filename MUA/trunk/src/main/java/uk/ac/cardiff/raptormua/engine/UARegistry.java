
package uk.ac.cardiff.raptormua.engine;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import uk.ac.cardiff.raptormua.model.UAEntry;

/**
 * @author philsmart handles the list of attached ICAs as injected by Spring
 */
public class UARegistry {
    static Logger log = Logger.getLogger(UARegistry.class);

    private List<UAEntry> UAEntries;

    public UARegistry() {
    	setUAEntries(new ArrayList<UAEntry>());
    }

    public void setUAEntries(List<UAEntry> UAEntries) {

	for (UAEntry entry : UAEntries)
	    log.info("Registering: " + entry.getClass());
	this.UAEntries = UAEntries;
    }

    public List<UAEntry> getUAEntries() {
	return UAEntries;
    }
}
