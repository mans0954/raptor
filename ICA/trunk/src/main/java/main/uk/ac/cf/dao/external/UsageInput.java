/**
 *
 */
package main.uk.ac.cf.dao.external;

import java.util.List;

import main.uk.ac.cf.dao.RawData;

/**
 * @author philsmart
 *
 */
public abstract class UsageInput extends RawData{



    public List getUsages() {
	return entryHandler.getEntries();

    }

}
