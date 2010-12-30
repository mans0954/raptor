/**
 *
 */
package main.uk.ac.cf.model;

import org.junit.Test;

/**
 * @author philsmart
 *
 */
public class RegexInclusionEntryTest {

    @Test
    public void testRegexFilter() throws Exception{
	System.out.println("Testing RegexInclusionEntry");
	RegexInclusionEntry inclusionEntry = new RegexInclusionEntry();
	inclusionEntry.setMatch(":sso");
	boolean success = inclusionEntry.filter("https://abc.cardiff.ac.uk/sp/shibboleth|urn:mace:shibboleth:2.0:profiles:saml2:sso");
	System.out.println("testRegexFilter: Should succeed :"+success);
	assert(success);

	success = inclusionEntry.filter("https://idp.cardiff.ac.uk/shibboleth|urn:oasis:names:tc:SAML:2.0:bindings:HTTP-POST");
	System.out.println("testRegexFilter: Should fail :"+success);
	assert(!success);
    }
}
