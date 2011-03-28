/**
 *
 */
package uk.ac.cardiff.model;


import static org.junit.Assert.assertTrue;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.junit.Test;

import uk.ac.cardiff.model.event.ShibbolethIdpAuthenticationEvent;


/**
 * @author philsmart
 *
 */
public class ShibbolethEntryTest {

    @Test
    public void testHashCodeEqual(){
	ShibbolethIdpAuthenticationEvent entry = new ShibbolethIdpAuthenticationEvent();
	entry.setAuthenticationType("urn:oasis:names:tc:SAML:2.0:ac:classes:PasswordProtectedTransport");
	String eventTime ="20101117T184343";
	DateTimeFormatter dtf = DateTimeFormat.forPattern("yyyyMMdd'T'HHmmss");
	DateTime dt = dtf.parseDateTime(eventTime);
	entry.setEventTime(dt);
	entry.setMessageProfileId("urn:mace:shibboleth:2.0:profiles:saml2:sso");
	entry.setPrincipalName("scmps2");
	entry.setRequestBinding("urn:mace:shibboleth:1.0:profiles:AuthnRequest");
	entry.setReleasedAttributes(new String[]{"eduPersonScopedAffiliation","eduPersonEntitlement"});
	entry.setServiceHost("https://abc.cardiff.ac.uk/sp/shibboleth");
	entry.setRequestId("");
	entry.setRequestPath("");
	entry.setResponseBinding("urn:oasis:names:tc:SAML:2.0:bindings:HTTP-POST");
	entry.setResourceHost("https://idp.cardiff.ac.uk/shibboleth");


	ShibbolethIdpAuthenticationEvent equalEntry = new ShibbolethIdpAuthenticationEvent();
	equalEntry.setAuthenticationType("urn:oasis:names:tc:SAML:2.0:ac:classes:PasswordProtectedTransport");
	eventTime ="20101117T184343";
	dtf = DateTimeFormat.forPattern("yyyyMMdd'T'HHmmss");
	dt = dtf.parseDateTime(eventTime);
	equalEntry.setEventTime(dt);
	equalEntry.setMessageProfileId("urn:mace:shibboleth:2.0:profiles:saml2:sso");
	equalEntry.setPrincipalName("scmps2");
	equalEntry.setRequestBinding("urn:mace:shibboleth:1.0:profiles:AuthnRequest");
	equalEntry.setReleasedAttributes(new String[]{"eduPersonScopedAffiliation","eduPersonEntitlement"});
	equalEntry.setServiceHost("https://abc.cardiff.ac.uk/sp/shibboleth");
	equalEntry.setRequestId("");
	equalEntry.setRequestPath("");
	equalEntry.setResponseBinding("urn:oasis:names:tc:SAML:2.0:bindings:HTTP-POST");
	equalEntry.setResourceHost("https://idp.cardiff.ac.uk/shibboleth");

	System.out.println("testHashCodeEqual: Entry One Hash ["+entry.hashCode()+"] : Entry Two Hash ["+equalEntry.hashCode()+"] : Are Equal: "+entry.equals(equalEntry));

	assertTrue(entry.equals(equalEntry));


    }

    /**
     * small change made to the date and time
     */
    @Test
    public void testHashCodeNotEqualDate(){
	ShibbolethIdpAuthenticationEvent entry = new ShibbolethIdpAuthenticationEvent();
	entry.setAuthenticationType("urn:oasis:names:tc:SAML:2.0:ac:classes:PasswordProtectedTransport");
	String eventTime ="20101117T184343";
	DateTimeFormatter dtf = DateTimeFormat.forPattern("yyyyMMdd'T'HHmmss");
	DateTime dt = dtf.parseDateTime(eventTime);
	entry.setEventTime(dt);
	entry.setMessageProfileId("urn:mace:shibboleth:2.0:profiles:saml2:sso");
	entry.setPrincipalName("scmps2");
	entry.setRequestBinding("urn:mace:shibboleth:1.0:profiles:AuthnRequest");
	entry.setReleasedAttributes(new String[]{"eduPersonScopedAffiliation","eduPersonEntitlement"});
	entry.setServiceHost("https://abc.cardiff.ac.uk/sp/shibboleth");
	entry.setRequestId("");
	entry.setRequestPath("");
	entry.setResponseBinding("urn:oasis:names:tc:SAML:2.0:bindings:HTTP-POST");
	entry.setResourceHost("https://idp.cardiff.ac.uk/shibboleth");


	ShibbolethIdpAuthenticationEvent equalEntry = new ShibbolethIdpAuthenticationEvent();
	equalEntry.setAuthenticationType("urn:oasis:names:tc:SAML:2.0:ac:classes:PasswordProtectedTransport");
	eventTime ="20101117T184342"; //changed 3 to 2 at the end
	dtf = DateTimeFormat.forPattern("yyyyMMdd'T'HHmmss");
	dt = dtf.parseDateTime(eventTime);
	equalEntry.setEventTime(dt);
	equalEntry.setMessageProfileId("urn:mace:shibboleth:2.0:profiles:saml2:sso");
	equalEntry.setPrincipalName("scmps2");
	equalEntry.setRequestBinding("urn:mace:shibboleth:1.0:profiles:AuthnRequest");
	equalEntry.setReleasedAttributes(new String[]{"eduPersonScopedAffiliation","eduPersonEntitlement"});
	equalEntry.setServiceHost("https://abc.cardiff.ac.uk/sp/shibboleth");
	equalEntry.setRequestId("");
	equalEntry.setRequestPath("");
	equalEntry.setResponseBinding("urn:oasis:names:tc:SAML:2.0:bindings:HTTP-POST");
	equalEntry.setResourceHost("https://idp.cardiff.ac.uk/shibboleth");

	System.out.println("testHashCodeNotEqualDate: Entry One Hash ["+entry.hashCode()+"] : Entry Two Hash ["+equalEntry.hashCode()+"] : Are Equal: "+entry.equals(equalEntry));

	assertTrue(!entry.equals(equalEntry));


    }

    /**
     * small change made to the date and time
     */
    @Test
    public void testHashCodeNotEqualReleasedAttributes(){
	ShibbolethIdpAuthenticationEvent entry = new ShibbolethIdpAuthenticationEvent();
	entry.setAuthenticationType("urn:oasis:names:tc:SAML:2.0:ac:classes:PasswordProtectedTransport");
	String eventTime ="20101117T184343";
	DateTimeFormatter dtf = DateTimeFormat.forPattern("yyyyMMdd'T'HHmmss");
	DateTime dt = dtf.parseDateTime(eventTime);
	entry.setEventTime(dt);
	entry.setMessageProfileId("urn:mace:shibboleth:2.0:profiles:saml2:sso");
	entry.setPrincipalName("scmps2");
	entry.setRequestBinding("urn:mace:shibboleth:1.0:profiles:AuthnRequest");
	entry.setReleasedAttributes(new String[]{"eduPersonScopedAffiliation","eduPersonEntitlement"});
	entry.setServiceHost("https://abc.cardiff.ac.uk/sp/shibboleth");
	entry.setRequestId("");
	entry.setRequestPath("");
	entry.setResponseBinding("urn:oasis:names:tc:SAML:2.0:bindings:HTTP-POST");
	entry.setResourceHost("https://idp.cardiff.ac.uk/shibboleth");


	ShibbolethIdpAuthenticationEvent equalEntry = new ShibbolethIdpAuthenticationEvent();
	equalEntry.setAuthenticationType("urn:oasis:names:tc:SAML:2.0:ac:classes:PasswordProtectedTransport");
	eventTime ="20101117T184343";
	dtf = DateTimeFormat.forPattern("yyyyMMdd'T'HHmmss");
	dt = dtf.parseDateTime(eventTime);
	equalEntry.setEventTime(dt);
	equalEntry.setMessageProfileId("urn:mace:shibboleth:2.0:profiles:saml2:sso");
	equalEntry.setPrincipalName("scmps2");
	equalEntry.setRequestBinding("urn:mace:shibboleth:1.0:profiles:AuthnRequest");
	equalEntry.setReleasedAttributes(new String[]{"eduPersonScopedAffiliation"}); //removed eduPersonEntitlement
	equalEntry.setServiceHost("https://abc.cardiff.ac.uk/sp/shibboleth");
	equalEntry.setRequestId("");
	equalEntry.setRequestPath("");
	equalEntry.setResponseBinding("urn:oasis:names:tc:SAML:2.0:bindings:HTTP-POST");
	equalEntry.setResourceHost("https://idp.cardiff.ac.uk/shibboleth");

	System.out.println("testHashCodeNotEqualReleasedAttributes: Entry One Hash ["+entry.hashCode()+"] : Entry Two Hash ["+equalEntry.hashCode()+"] : Are Equal: "+entry.equals(equalEntry));

	assertTrue(!entry.equals(equalEntry));


    }

}
