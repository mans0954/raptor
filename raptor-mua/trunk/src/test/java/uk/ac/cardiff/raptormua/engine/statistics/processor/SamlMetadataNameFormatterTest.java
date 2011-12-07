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
