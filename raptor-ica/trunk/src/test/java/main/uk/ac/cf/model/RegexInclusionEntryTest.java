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
