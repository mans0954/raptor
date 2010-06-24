
package uk.ac.cardiff.RaptorUA.engine;

import java.util.ArrayList;
import java.util.List;



import org.apache.log4j.Logger;

import uk.ac.cardiff.RaptorUA.model.ICAEntry;

/**
 * @author philsmart handles the list of attached ICAs as injected by Spring
 */
public class ICARegistry {
    static Logger log = Logger.getLogger(ICARegistry.class);

    private List<ICAEntry> ICAEntries;

    public ICARegistry() {
	setICAEntries(new ArrayList<ICAEntry>());
    }

    public void setICAEntries(List<ICAEntry> ICAEntries) {
	System.out.println("Setting ICAs");
	for (ICAEntry entry : ICAEntries)
	    log.info("Registering: " + entry.getClass());
	this.ICAEntries = ICAEntries;
    }

    public List<ICAEntry> getICAEntries() {
	return ICAEntries;
    }
}
