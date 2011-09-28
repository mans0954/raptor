
package uk.ac.cardiff.raptormua.engine.statistics.processor;

import java.util.List;

/**
 * An alias class to support backward compatibility in statistical-units.xml configuration file.
 * 
 */
@Deprecated
public class ShibbolethMetadataNameFormatter extends SamlMetadataNameFormatter {

    public ShibbolethMetadataNameFormatter(List<String> SAMLMetadataURIs) {
        super(SAMLMetadataURIs);
    }

    public ShibbolethMetadataNameFormatter(String SAMLMetadataURI) {
        super(SAMLMetadataURI);
    }

}
