/**
 *
 */
package uk.ac.cardiff.raptorweb.engine;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import uk.ac.cardiff.raptorweb.model.MUAEntry;

/**
 * @author philsmart
 *
 */
public class MUARegistry {
	static Logger log = Logger.getLogger(MUARegistry.class);

    private List<MUAEntry> MUAEntries;

    public MUARegistry() {
    	setMUAEntries(new ArrayList<MUAEntry>());
    }

    public void setMUAEntries(List<MUAEntry> MUAEntries) {

    	for (MUAEntry entry : MUAEntries)
    		log.info("Registering: " + entry.getClass());
		this.MUAEntries = MUAEntries;
    }

    public List<MUAEntry> getUAEntries() {
	return MUAEntries;
    }
}
