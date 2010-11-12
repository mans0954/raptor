/**
 * Copyright (C) 2010 Cardiff University, Wales <smartp@cf.ac.uk>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
/**
 *
 */
package main.uk.ac.cf.model;

import java.util.List;

import main.uk.ac.cf.dao.internal.ICADataConnection;

import org.joda.time.DateTime;

import uk.ac.cardiff.model.Entry;

/**
 * @author philsmart
 *
 */
public class PersistantEntryHandler implements EntryHandler{

    private ICADataConnection dataConnection;

    /* (non-Javadoc)
     * @see main.uk.ac.cf.model.EntryHandler#addEntries(java.util.List)
     */
    @Override
    public void addEntries(List<Entry> entries) {
	// TODO Auto-generated method stub

    }

    /* (non-Javadoc)
     * @see main.uk.ac.cf.model.EntryHandler#endTransaction()
     */
    @Override
    public void endTransaction() {
	// TODO Auto-generated method stub

    }

    /* (non-Javadoc)
     * @see main.uk.ac.cf.model.EntryHandler#getEntries()
     */
    @Override
    public List getEntries() {
	// TODO Auto-generated method stub
	return null;
    }

    /* (non-Javadoc)
     * @see main.uk.ac.cf.model.EntryHandler#getLatestEntryTime()
     */
    @Override
    public DateTime getLatestEntryTime() {
	// TODO Auto-generated method stub
	return null;
    }

    /* (non-Javadoc)
     * @see main.uk.ac.cf.model.EntryHandler#isAfter(uk.ac.cardiff.model.Entry)
     */
    @Override
    public boolean isAfter(Entry authE) {
	// TODO Auto-generated method stub
	return false;
    }

    /* (non-Javadoc)
     * @see main.uk.ac.cf.model.EntryHandler#isDisjointFromLastEntry(uk.ac.cardiff.model.Entry)
     */
    @Override
    public boolean isDisjointFromLastEntry(Entry authE) {
	// TODO Auto-generated method stub
	return false;
    }

    /* (non-Javadoc)
     * @see main.uk.ac.cf.model.EntryHandler#isEqualTime(uk.ac.cardiff.model.Entry)
     */
    @Override
    public boolean isEqualTime(Entry authE) {
	// TODO Auto-generated method stub
	return false;
    }

    /* (non-Javadoc)
     * @see main.uk.ac.cf.model.EntryHandler#isNewerOrEqual(uk.ac.cardiff.model.Entry)
     */
    @Override
    public boolean isNewerOrEqual(Entry authE) {
	// TODO Auto-generated method stub
	return false;
    }

    /* (non-Javadoc)
     * @see main.uk.ac.cf.model.EntryHandler#removeAllEntries()
     */
    @Override
    public void removeAllEntries() {
	// TODO Auto-generated method stub

    }

    /* (non-Javadoc)
     * @see main.uk.ac.cf.model.EntryHandler#setLatestEntryTime(org.joda.time.DateTime)
     */
    @Override
    public void setLatestEntryTime(DateTime latestEntryTime) {
	// TODO Auto-generated method stub

    }

    public void setDataConnection(ICADataConnection dataConnection) {
	this.dataConnection = dataConnection;
    }

    public ICADataConnection getDataConnection() {
	return dataConnection;
    }

}
