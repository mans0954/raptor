/**
 *
 */
package uk.ac.cardiff.raptorweb.engine;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import uk.ac.cardiff.raptorweb.model.MUAEntry;

/**
 * @author philsmart
 * 
 */
public class MUARegistry {

    /** Class logger */
    private final Logger log = LoggerFactory.getLogger(MUARegistry.class);

    /** List of all MUA's that this ICA has been configured to talk to*/
    private List<MUAEntry> MUAEntries;

    public MUARegistry() {
        setMUAEntries(new ArrayList<MUAEntry>());
    }

    public void setMUAEntries(List<MUAEntry> MUAEntries) {

        for (MUAEntry entry : MUAEntries)
            log.info("Registering the MUA [{}]", entry.getClass());
        this.MUAEntries = MUAEntries;
    }

    public List<MUAEntry> getUAEntries() {
        return MUAEntries;
    }
}
