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

package uk.ac.cardiff.raptormua.engine.statistics.processor;

import java.util.ArrayList;
import java.util.List;

import org.opensaml.saml2.metadata.provider.FileBackedHTTPMetadataProvider;
import org.opensaml.saml2.metadata.provider.MetadataProvider;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * @author philsmart
 * 
 */
public class SamlMetadataNameFormatterTest {

    SamlMetadataNameFormatter samlNameFormatter;

  // @Test
    public void instantiateSamlMetadataTest() throws Exception {
        List<MetadataProvider> providers = new ArrayList<MetadataProvider>();
        FileBackedHTTPMetadataProvider provider =
                new FileBackedHTTPMetadataProvider("http://metadata.ukfederation.org.uk/ukfederation-metadata.xml",
                        50000, "/tmp/metadata.xml");
        providers.add(provider);
        samlNameFormatter = new SamlMetadataNameFormatter(providers);
        Assert.assertNotNull(samlNameFormatter);
    }

}
