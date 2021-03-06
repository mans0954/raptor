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
package uk.ac.cardiff.model.event;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.testng.Assert;
import org.testng.annotations.Test;

import uk.ac.cardiff.model.event.auxiliary.PrincipalInformation;

/**
 * @author philsmart
 * 
 */
public class ShibbolethEntryTest {

    @Test
    public void testHashCodeEqual() {
        ShibbolethIdpAuthenticationEvent entry = new ShibbolethIdpAuthenticationEvent();
        entry.setAuthenticationType("urn:oasis:names:tc:SAML:2.0:ac:classes:PasswordProtectedTransport");
        String eventTime = "20101117T184343";
        DateTimeFormatter dtf = DateTimeFormat.forPattern("yyyyMMdd'T'HHmmss");
        DateTime dt = dtf.parseDateTime(eventTime);
        entry.setEventTime(dt);
        entry.setMessageProfileId("urn:mace:shibboleth:2.0:profiles:saml2:sso");
        entry.setPrincipalName("scmps2");
        entry.setRequestBinding("urn:mace:shibboleth:1.0:profiles:AuthnRequest");
        entry.setAttributes(new String[] { "eduPersonScopedAffiliation", "eduPersonEntitlement" });
        entry.setServiceHost("https://abc.cardiff.ac.uk/sp/shibboleth");
        entry.setRequestId("");
        entry.setResponseBinding("urn:oasis:names:tc:SAML:2.0:bindings:HTTP-POST");
        entry.setResourceHost("https://idp.cardiff.ac.uk/shibboleth");

        ShibbolethIdpAuthenticationEvent equalEntry = new ShibbolethIdpAuthenticationEvent();
        equalEntry.setAuthenticationType("urn:oasis:names:tc:SAML:2.0:ac:classes:PasswordProtectedTransport");
        eventTime = "20101117T184343";
        dtf = DateTimeFormat.forPattern("yyyyMMdd'T'HHmmss");
        dt = dtf.parseDateTime(eventTime);
        equalEntry.setEventTime(dt);
        equalEntry.setMessageProfileId("urn:mace:shibboleth:2.0:profiles:saml2:sso");
        equalEntry.setPrincipalName("scmps2");
        equalEntry.setRequestBinding("urn:mace:shibboleth:1.0:profiles:AuthnRequest");
        equalEntry.setAttributes(new String[] { "eduPersonScopedAffiliation", "eduPersonEntitlement" });
        equalEntry.setServiceHost("https://abc.cardiff.ac.uk/sp/shibboleth");
        equalEntry.setRequestId("");
        equalEntry.setResponseBinding("urn:oasis:names:tc:SAML:2.0:bindings:HTTP-POST");
        equalEntry.setResourceHost("https://idp.cardiff.ac.uk/shibboleth");

        System.out.println("testHashCodeEqual: Entry One Hash [" + entry.hashCode() + "] : Entry Two Hash [" + equalEntry.hashCode() + "] : Are Equal: " + entry.equals(equalEntry));

        Assert.assertTrue(entry.equals(equalEntry));

    }

    /**
     * small change made to the date and time
     */
    @Test
    public void testHashCodeNotEqualDate() {
        ShibbolethIdpAuthenticationEvent entry = new ShibbolethIdpAuthenticationEvent();
        entry.setAuthenticationType("urn:oasis:names:tc:SAML:2.0:ac:classes:PasswordProtectedTransport");
        String eventTime = "20101117T184343";
        DateTimeFormatter dtf = DateTimeFormat.forPattern("yyyyMMdd'T'HHmmss");
        DateTime dt = dtf.parseDateTime(eventTime);
        entry.setEventTime(dt);
        entry.setMessageProfileId("urn:mace:shibboleth:2.0:profiles:saml2:sso");
        entry.setPrincipalName("scmps2");
        entry.setRequestBinding("urn:mace:shibboleth:1.0:profiles:AuthnRequest");
        entry.setAttributes(new String[] { "eduPersonScopedAffiliation", "eduPersonEntitlement" });
        entry.setServiceHost("https://abc.cardiff.ac.uk/sp/shibboleth");
        entry.setRequestId("");
        entry.setResponseBinding("urn:oasis:names:tc:SAML:2.0:bindings:HTTP-POST");
        entry.setResourceHost("https://idp.cardiff.ac.uk/shibboleth");

        ShibbolethIdpAuthenticationEvent equalEntry = new ShibbolethIdpAuthenticationEvent();
        equalEntry.setAuthenticationType("urn:oasis:names:tc:SAML:2.0:ac:classes:PasswordProtectedTransport");
        eventTime = "20101117T184342"; // changed 3 to 2 at the end
        dtf = DateTimeFormat.forPattern("yyyyMMdd'T'HHmmss");
        dt = dtf.parseDateTime(eventTime);
        equalEntry.setEventTime(dt);
        equalEntry.setMessageProfileId("urn:mace:shibboleth:2.0:profiles:saml2:sso");
        equalEntry.setPrincipalName("scmps2");
        equalEntry.setRequestBinding("urn:mace:shibboleth:1.0:profiles:AuthnRequest");
        equalEntry.setAttributes(new String[] { "eduPersonScopedAffiliation", "eduPersonEntitlement" });
        equalEntry.setServiceHost("https://abc.cardiff.ac.uk/sp/shibboleth");
        equalEntry.setRequestId("");
        equalEntry.setResponseBinding("urn:oasis:names:tc:SAML:2.0:bindings:HTTP-POST");
        equalEntry.setResourceHost("https://idp.cardiff.ac.uk/shibboleth");

        System.out.println("testHashCodeNotEqualDate: Entry One Hash [" + entry.hashCode() + "] : Entry Two Hash [" + equalEntry.hashCode() + "] : Are Equal: " + entry.equals(equalEntry));

        Assert.assertTrue(!entry.equals(equalEntry));

    }

    /**
     * small change made to the date and time
     */
    @Test
    public void testHashCodeNotEqualReleasedAttributes() {
        ShibbolethIdpAuthenticationEvent entry = new ShibbolethIdpAuthenticationEvent();
        entry.setAuthenticationType("urn:oasis:names:tc:SAML:2.0:ac:classes:PasswordProtectedTransport");
        String eventTime = "20101117T184343";
        DateTimeFormatter dtf = DateTimeFormat.forPattern("yyyyMMdd'T'HHmmss");
        DateTime dt = dtf.parseDateTime(eventTime);
        entry.setEventTime(dt);
        entry.setMessageProfileId("urn:mace:shibboleth:2.0:profiles:saml2:sso");
        entry.setPrincipalName("scmps2");
        entry.setRequestBinding("urn:mace:shibboleth:1.0:profiles:AuthnRequest");
        entry.setAttributes(new String[] { "eduPersonScopedAffiliation", "eduPersonEntitlement" });
        entry.setServiceHost("https://abc.cardiff.ac.uk/sp/shibboleth");
        entry.setRequestId("");
        entry.setResponseBinding("urn:oasis:names:tc:SAML:2.0:bindings:HTTP-POST");
        entry.setResourceHost("https://idp.cardiff.ac.uk/shibboleth");

        ShibbolethIdpAuthenticationEvent equalEntry = new ShibbolethIdpAuthenticationEvent();
        equalEntry.setAuthenticationType("urn:oasis:names:tc:SAML:2.0:ac:classes:PasswordProtectedTransport");
        eventTime = "20101117T184343";
        dtf = DateTimeFormat.forPattern("yyyyMMdd'T'HHmmss");
        dt = dtf.parseDateTime(eventTime);
        equalEntry.setEventTime(dt);
        equalEntry.setMessageProfileId("urn:mace:shibboleth:2.0:profiles:saml2:sso");
        equalEntry.setPrincipalName("scmps2");
        equalEntry.setRequestBinding("urn:mace:shibboleth:1.0:profiles:AuthnRequest");
        equalEntry.setAttributes(new String[] { "eduPersonScopedAffiliation" }); // removed eduPersonEntitlement
        equalEntry.setServiceHost("https://abc.cardiff.ac.uk/sp/shibboleth");
        equalEntry.setRequestId("");
        equalEntry.setResponseBinding("urn:oasis:names:tc:SAML:2.0:bindings:HTTP-POST");
        equalEntry.setResourceHost("https://idp.cardiff.ac.uk/shibboleth");

        System.out.println("testHashCodeNotEqualReleasedAttributes: Entry One Hash [" + entry.hashCode() + "] : Entry Two Hash [" + equalEntry.hashCode() + "] : Are Equal: "
                + entry.equals(equalEntry));

        Assert.assertTrue(!entry.equals(equalEntry));

    }

    @Test
    public void testConstructorCopyChangesDoNotEffectOriginal() {
        ShibbolethIdpAuthenticationEvent entry = new ShibbolethIdpAuthenticationEvent();
        entry.setAuthenticationType("urn:oasis:names:tc:SAML:2.0:ac:classes:PasswordProtectedTransport");
        String eventTime = "20101117T184343";
        DateTimeFormatter dtf = DateTimeFormat.forPattern("yyyyMMdd'T'HHmmss");
        DateTime dt = dtf.parseDateTime(eventTime);
        entry.setEventTime(dt);
        entry.setMessageProfileId("urn:mace:shibboleth:2.0:profiles:saml2:sso");
        entry.setPrincipalName("scmps2");
        entry.setRequestBinding("urn:mace:shibboleth:1.0:profiles:AuthnRequest");
        entry.setAttributes(new String[] { "eduPersonScopedAffiliation", "eduPersonEntitlement" });
        entry.setServiceHost("https://abc.cardiff.ac.uk/sp/shibboleth");
        entry.setRequestId("");
        entry.setResponseBinding("urn:oasis:names:tc:SAML:2.0:bindings:HTTP-POST");
        entry.setResourceHost("https://idp.cardiff.ac.uk/shibboleth");
        entry.setAssertions(new String[] { "1" });
        entry.setEventId(100);
        entry.setNameIdentifier("name1");
        PrincipalInformation principle = new PrincipalInformation();
        principle.setAffiliation("U");
        principle.setSchool("COMSC");
        entry.setPrincipalInformation(principle);
        entry.setResourceId("http://raptor.test");
        entry.setResourceIdCategory(1);

        System.out.println("testConstructorCopy:First Event :" + entry);
        int hashBefore = entry.getHashCode();

        ShibbolethIdpAuthenticationEvent copy = entry.copy();
        copy.setAuthenticationType("urn:oasis:names:tc:SAML:2.0:ac:classes:PasswordProtectedTransport-new");
        eventTime = "20111117T184343";
        dtf = DateTimeFormat.forPattern("yyyyMMdd'T'HHmmss");
        dt = dtf.parseDateTime(eventTime);
        copy.setEventTime(dt);
        copy.setMessageProfileId("urn:mace:shibboleth:2.0:profiles:saml2:sso-new");
        copy.setPrincipalName("new");
        copy.setRequestBinding("urn:mace:shibboleth:1.0:profiles:AuthnRequest-new");
        copy.setAttributes(new String[] { "eduPersonScopedAffiliation" }); // removed eduPersonEntitlement
        copy.setServiceHost("https://abc.cardiff.ac.uk/sp/shibboleth-new");
        copy.setRequestId("new");
        copy.setResponseBinding("urn:oasis:names:tc:SAML:2.0:bindings:HTTP-SOAP-new");
        copy.setResourceHost("https://idp.cardiff.ac.uk/shibboleth-new");
        copy.setAssertions(new String[] { "1", "4" });
        copy.setEventId(300);
        copy.setNameIdentifier("new");
        PrincipalInformation principleCopy = new PrincipalInformation(principle);
        copy.setPrincipalInformation(principleCopy);
        copy.setResourceId("http://raptor.test.new");
        copy.setResourceIdCategory(2);

        System.out.println("testConstructorCopy:Copied Event:" + copy);
        System.out.println("testConstructorCopy:First After :" + entry);

        System.out.println("testConstructorCopy:First:Shibboleth Event, First Instance has hash [" + hashBefore + "], " + "Copy has Hash [" + copy.getHashCode() + "], First after copy has hash ["
                + entry.getHashCode() + "], first and firts after copy" + "should be equal [" + (hashBefore == entry.getHashCode()) + "]");

        Assert.assertTrue(hashBefore == entry.getHashCode());
    }

    /**
     * Test that a copy produces the same object
     */
    @Test
    public void testShibConstructorCopyEquals() {
        ShibbolethIdpAuthenticationEvent entry = new ShibbolethIdpAuthenticationEvent();
        entry.setAuthenticationType("urn:oasis:names:tc:SAML:2.0:ac:classes:PasswordProtectedTransport");
        String eventTime = "20101117T184343";
        DateTimeFormatter dtf = DateTimeFormat.forPattern("yyyyMMdd'T'HHmmss");
        DateTime dt = dtf.parseDateTime(eventTime);
        entry.setEventTime(dt);
        entry.setMessageProfileId("urn:mace:shibboleth:2.0:profiles:saml2:sso");
        entry.setPrincipalName("scmps2");
        entry.setRequestBinding("urn:mace:shibboleth:1.0:profiles:AuthnRequest");
        entry.setAttributes(new String[] { "eduPersonScopedAffiliation", "eduPersonEntitlement" });
        entry.setServiceHost("https://abc.cardiff.ac.uk/sp/shibboleth");
        entry.setRequestId("");
        entry.setResponseBinding("urn:oasis:names:tc:SAML:2.0:bindings:HTTP-POST");
        entry.setResourceHost("https://idp.cardiff.ac.uk/shibboleth");
        entry.setAssertions(new String[] { "1" });
        entry.setEventId(100);
        entry.setNameIdentifier("name1");
        PrincipalInformation principle = new PrincipalInformation();
        principle.setAffiliation("U");
        principle.setSchool("COMSC");
        entry.setPrincipalInformation(principle);
        entry.setResourceId("http://raptor.test");

        ShibbolethIdpAuthenticationEvent copy = entry.copy();
        copy.setAuthenticationType("urn:oasis:names:tc:SAML:2.0:ac:classes:PasswordProtectedTransport");
        eventTime = "20101117T184343";
        dtf = DateTimeFormat.forPattern("yyyyMMdd'T'HHmmss");
        dt = dtf.parseDateTime(eventTime);
        copy.setEventTime(dt);
        copy.setMessageProfileId("urn:mace:shibboleth:2.0:profiles:saml2:sso");
        copy.setPrincipalName("scmps2");
        copy.setRequestBinding("urn:mace:shibboleth:1.0:profiles:AuthnRequest");
        copy.setAttributes(new String[] { "eduPersonScopedAffiliation", "eduPersonEntitlement" }); // removed eduPersonEntitlement
        copy.setServiceHost("https://abc.cardiff.ac.uk/sp/shibboleth");
        copy.setRequestId("");
        copy.setResponseBinding("urn:oasis:names:tc:SAML:2.0:bindings:HTTP-POST");
        copy.setResourceHost("https://idp.cardiff.ac.uk/shibboleth");
        entry.setAssertions(new String[] { "1" });
        entry.setEventId(100);
        entry.setNameIdentifier("name1");
        PrincipalInformation principleCopy = new PrincipalInformation(principle);
        entry.setPrincipalInformation(principleCopy);
        entry.setResourceId("http://raptor.test");

        System.out.println("testShibConstructorCopyEquals:Shibboleth Event, First Instance has hash [" + entry.getHashCode() + "], " + "Copy has Hash [" + copy.getHashCode() + "], are equal ["
                + (entry.getHashCode() == copy.getHashCode()) + "]");

        Assert.assertTrue(entry.getHashCode() == copy.getHashCode());
    }

}
