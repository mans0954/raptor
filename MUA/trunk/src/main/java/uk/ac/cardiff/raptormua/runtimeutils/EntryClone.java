/**
 *
 */
package uk.ac.cardiff.raptormua.runtimeutils;

import java.util.ArrayList;
import java.util.List;

import uk.ac.cardiff.model.Entry;

/**
 * @author philsmart
 *
 */
public class EntryClone {

	public static ArrayList<Entry> cloneEntries(ArrayList<Entry> entries){
		return (ArrayList<Entry>) entries.clone();
	}

}
