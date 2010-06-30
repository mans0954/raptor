/**
 *
 */
package main.uk.ac.cf.model;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;

import uk.ac.cardiff.model.Entry;

/**
 * @author philsmart
 *
 */
public interface EntryHandler {


	public void addEntries(List<Entry> entries);

	public void setLatestEntryTime(DateTime latestEntryTime);

	public DateTime getLatestEntryTime();

	public boolean isNewerOrEqual(Entry authE);

	public List getEntries();

	public void removeAllEntries();

	public void endTransaction();

}
