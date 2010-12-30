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

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.junit.Test;

import uk.ac.cardiff.model.Entry;
import uk.ac.cardiff.model.ShibbolethEntry;

/**
 * @author philsmart
 *
 */
public class EntryHandlerTest {

    /**
     * again tests the hashcode values for entries, but using a generic method.
     */
    @Test
    public void testDisjointEntries(){
	ShibbolethEntry entry = new ShibbolethEntry();
	entry.setAuthNMethod("urn:oasis:names:tc:SAML:2.0:ac:classes:PasswordProtectedTransport");
	String eventTime ="20101117T184343";
	DateTimeFormatter dtf = DateTimeFormat.forPattern("yyyyMMdd'T'HHmmss");
	DateTime dt = dtf.parseDateTime(eventTime);
	entry.setEventTime(dt);
	entry.setMessageProfileId("urn:mace:shibboleth:2.0:profiles:saml2:sso");
	entry.setPrincipleName("scmps2");
	entry.setRequestBinding("urn:mace:shibboleth:1.0:profiles:AuthnRequest");
	entry.setReleasedAttributes(new String[]{"eduPersonScopedAffiliation","eduPersonEntitlement"});
	entry.setRequestHost("https://abc.cardiff.ac.uk/sp/shibboleth");
	entry.setRequestHostFriendlyName("");
	entry.setRequestPath("");
	entry.setResponseBinding("urn:oasis:names:tc:SAML:2.0:bindings:HTTP-POST");
	entry.setServerHost("https://idp.cardiff.ac.uk/shibboleth");
	entry.setServerHostFriendlyName("");


	ShibbolethEntry equalEntry = new ShibbolethEntry();
	equalEntry.setAuthNMethod("urn:oasis:names:tc:SAML:2.0:ac:classes:PasswordProtectedTransport");
	eventTime ="20101117T184343";
	dtf = DateTimeFormat.forPattern("yyyyMMdd'T'HHmmss");
	dt = dtf.parseDateTime(eventTime);
	equalEntry.setEventTime(dt);
	equalEntry.setMessageProfileId("urn:mace:shibboleth:2.0:profiles:saml2:sso");
	equalEntry.setPrincipleName("scmps2");
	equalEntry.setRequestBinding("urn:mace:shibboleth:1.0:profiles:AuthnRequest");
	equalEntry.setReleasedAttributes(new String[]{"eduPersonScopedAffiliation","eduPersonEntitlement"});
	equalEntry.setRequestHost("https://abc.cardiff.ac.uk/sp/shibboleth");
	equalEntry.setRequestHostFriendlyName("");
	equalEntry.setRequestPath("");
	equalEntry.setResponseBinding("urn:oasis:names:tc:SAML:2.0:bindings:HTTP-POST");
	equalEntry.setServerHost("https://idp.cardiff.ac.uk/shibboleth");
	equalEntry.setServerHostFriendlyName("");

	assertTrue(isDisjoint(entry, equalEntry));
    }

    /**
     * java makes sure the type passed in becomes the type operated on. Could not do this with
     * (Object entry) as only Object would be seen during execution of the method
     *
     * @param <T>
     * @param entry
     * @param entryTwo
     * @return
     */
    private <T extends Entry> boolean isDisjoint(T entry, T entryTwo){
	System.out.println("testDisjointEntries: Entry One is: "+entry+" With hashCode: "+entry.hashCode());
	System.out.println("testDisjointEntries: Entry Two is: "+entryTwo+" With hashCode: "+entryTwo.hashCode());
	System.out.println("testDisjointEntries: Equality: "+entry.equals(entryTwo));
	return entry.equals(entryTwo);

    }

    @Test
    public void testHashSet(){

	System.out.println("Testing HashSet");
	Set<Entry> entries = new LinkedHashSet<Entry>();

	ShibbolethEntry entry = new ShibbolethEntry();
	entry.setAuthNMethod("urn:oasis:names:tc:SAML:2.0:ac:classes:PasswordProtectedTransport");
	String eventTime ="20101117T184342";
	DateTimeFormatter dtf = DateTimeFormat.forPattern("yyyyMMdd'T'HHmmss");
	DateTime dt = dtf.parseDateTime(eventTime);
	entry.setEventTime(dt);
	entry.setMessageProfileId("urn:mace:shibboleth:2.0:profiles:saml2:sso");
	entry.setPrincipleName("scmps2");
	entry.setRequestBinding("urn:mace:shibboleth:1.0:profiles:AuthnRequest");
	entry.setReleasedAttributes(new String[]{"eduPersonScopedAffiliation","eduPersonEntitlement"});
	entry.setRequestHost("https://abc.cardiff.ac.uk/sp/shibboleth");
	entry.setRequestHostFriendlyName("");
	entry.setRequestId("");
	entry.setRequestPath("");
	entry.setResponseBinding("urn:oasis:names:tc:SAML:2.0:bindings:HTTP-POST");
	entry.setServerHost("https://idp.cardiff.ac.uk/shibboleth");
	entry.setServerHostFriendlyName("");
	entry.setPersistantId(new Long(10));
	entry.setAssertionId(new String[]{"10"});
	entry.setResponseId("10");
	entry.setRequestBinding("");
	entry.setNameIdentifier("20");


	ShibbolethEntry equalEntry = new ShibbolethEntry();
	equalEntry.setAuthNMethod("urn:oasis:names:tc:SAML:2.0:ac:classes:PasswordProtectedTransport");
	eventTime ="20101117T184342";
	dtf = DateTimeFormat.forPattern("yyyyMMdd'T'HHmmss");
	dt = dtf.parseDateTime(eventTime);
	equalEntry.setEventTime(dt);
	equalEntry.setMessageProfileId("urn:mace:shibboleth:2.0:profiles:saml2:sso");
	equalEntry.setPrincipleName("scmps2");
	equalEntry.setRequestBinding("urn:mace:shibboleth:1.0:profiles:AuthnRequest");
	equalEntry.setReleasedAttributes(new String[]{"eduPersonScopedAffiliation","eduPersonEntitlement"});
	equalEntry.setRequestHost("https://abc.cardiff.ac.uk/sp/shibboleth");
	equalEntry.setRequestHostFriendlyName("");
	equalEntry.setRequestPath("");
	equalEntry.setResponseBinding("urn:oasis:names:tc:SAML:2.0:bindings:HTTP-POST");
	equalEntry.setServerHost("https://idp.cardiff.ac.uk/shibboleth");
	equalEntry.setServerHostFriendlyName("");
	equalEntry.setPersistantId(new Long(10));
	equalEntry.setAssertionId(new String[]{"10"});
	equalEntry.setResponseId("10");
	equalEntry.setRequestBinding("");
	equalEntry.setNameIdentifier("20");;

	System.out.println("testHashSet: Hash for entry = "+entry.hashCode());
	entries.add(entry);
	System.out.println("testHashSet: Set has "+entries.size()+" should be 1");
	System.out.println("testHashSet: Has for equalEntry = "+equalEntry.hashCode());
	entries.add(equalEntry);
	System.out.println("testHashSet: Set has "+entries.size()+" should be 1");
	assertTrue(entries.size()==1);

	/* now remove and attribute from the base entry class, and see if it gets added. It should have a different hash*/
	ShibbolethEntry disjointEntry = new ShibbolethEntry();
	disjointEntry.setAuthNMethod("urn:oasis:names:tc:SAML:2.0:ac:classes:PasswordProtectedTransport");
	eventTime ="20101117T184342";
	dtf = DateTimeFormat.forPattern("yyyyMMdd'T'HHmmss");
	dt = dtf.parseDateTime(eventTime);
	disjointEntry.setEventTime(dt);
	disjointEntry.setMessageProfileId("urn:mace:shibboleth:2.0:profiles:saml2:sso");
	disjointEntry.setPrincipleName("scmps2");
	disjointEntry.setRequestBinding("urn:mace:shibboleth:1.0:profiles:AuthnRequest");
	disjointEntry.setReleasedAttributes(new String[]{"eduPersonScopedAffiliation","eduPersonEntitlement"});
	disjointEntry.setRequestHost("https://abc.cardiff.ac.uk/sp/shibboleth");
	disjointEntry.setRequestHostFriendlyName("");
	disjointEntry.setRequestId("");
	disjointEntry.setRequestPath("");
	//equalTwoEntry.setResponseBinding(""); //Removed this
	disjointEntry.setServerHost("https://idp.cardiff.ac.uk/shibboleth");
	disjointEntry.setServerHostFriendlyName("");

	entries.add(disjointEntry);
	System.out.println("testHashSet: Set has "+entries.size() +" should be 2");
	assertTrue(entries.size()==2);

	/* now create a new one, to check hashset containment*/
	ShibbolethEntry equalDisjointEntry = new ShibbolethEntry();
	equalDisjointEntry.setAuthNMethod("urn:oasis:names:tc:SAML:2.0:ac:classes:PasswordProtectedTransport");
	eventTime ="20101117T184342";
	dtf = DateTimeFormat.forPattern("yyyyMMdd'T'HHmmss");
	dt = dtf.parseDateTime(eventTime);
	equalDisjointEntry.setEventTime(dt);
	equalDisjointEntry.setMessageProfileId("urn:mace:shibboleth:2.0:profiles:saml2:sso");
	equalDisjointEntry.setPrincipleName("scmps2");
	equalDisjointEntry.setRequestBinding("urn:mace:shibboleth:1.0:profiles:AuthnRequest");
	equalDisjointEntry.setReleasedAttributes(new String[]{"eduPersonScopedAffiliation","eduPersonEntitlement"});
	equalDisjointEntry.setRequestHost("https://abc.cardiff.ac.uk/sp/shibboleth");
	equalDisjointEntry.setRequestHostFriendlyName("");
	equalDisjointEntry.setRequestId("");
	equalDisjointEntry.setRequestPath("");
	//equalTwoEntry.setResponseBinding(""); //Removed this
	equalDisjointEntry.setServerHost("https://idp.cardiff.ac.uk/shibboleth");
	equalDisjointEntry.setServerHostFriendlyName("");

	System.out.println("testHashSet: Set already has entry: ["+entries.contains(equalDisjointEntry)+"] should be true");
	assertTrue(entries.contains(equalDisjointEntry));


    }



}
