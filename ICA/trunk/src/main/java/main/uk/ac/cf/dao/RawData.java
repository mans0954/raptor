/**
 *
 */
package main.uk.ac.cf.dao;

import java.util.List;

import org.joda.time.DateTime;

import main.uk.ac.cf.model.EntryHandler;

/**
 * @author philsmart
 *
 */
public abstract class RawData {

	public EntryHandler entryHandler;



	public abstract void parse() throws Exception;

	public RawData(){
		entryHandler = new EntryHandler();
	}


}
