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

import java.util.List;

import org.opensaml.DefaultBootstrap;
import org.opensaml.saml2.metadata.OrganizationDisplayName;
import org.opensaml.saml2.metadata.provider.ChainingMetadataProvider;
import org.opensaml.saml2.metadata.provider.FileBackedHTTPMetadataProvider;
import org.opensaml.saml2.metadata.provider.MetadataProvider;
import org.opensaml.saml2.metadata.provider.MetadataProviderException;
import org.opensaml.xml.ConfigurationException;
import org.opensaml.xml.parse.BasicParserPool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import uk.ac.cardiff.model.wsmodel.MethodParameter;
import uk.ac.cardiff.raptormua.engine.statistics.records.Group;
import uk.ac.cardiff.raptormua.engine.statistics.records.Observation;

/**
 * @author philsmart
 * 
 */
public class SamlMetadataNameFormatter extends AbstractStatisticPostProcessor {

    /** Parser manager used to parse XML. */
    protected static BasicParserPool parser;

    /** ChainingMetadataProvider to allow 1..* types of metadata providers. */
    protected ChainingMetadataProvider metadataProvider;

    /** class logger */
    private final Logger log = LoggerFactory.getLogger(SamlMetadataNameFormatter.class);

    /** this is not a proper URI at the moment, just a UNC path */
    private String SAMLMetadataURI;

    /**
     * Default constructor
     * 
     */
    public SamlMetadataNameFormatter() {

    }

    public SamlMetadataNameFormatter(List<MetadataProvider> providers) {
        metadataProvider = new ChainingMetadataProvider();
        try {
            loadSAMLMetadata(providers);
        } catch (MetadataProviderException e) {
            log.error("Could not load SAML metadata: {} ", e.getMessage());
        } catch (ConfigurationException e) {
            log.error("Could not load SAML metadata, configuration exception ", e);
        }
    }

    /**
     * Takes a <code>List</code> of SAML
     * <code>MetadataProvider<code>s and adds them to the <code>metadataProvider</code>.
     * 
     * @param SAMLMetadataProviders list of MetadataProviders to use within this processor.
     * @throws MetadataProviderException
     * @throws ConfigurationException
     */
    private void loadSAMLMetadata(List<MetadataProvider> SAMLMetadataProviders) throws MetadataProviderException,
            ConfigurationException {
        DefaultBootstrap.bootstrap();
        parser = new BasicParserPool();
        parser.setNamespaceAware(true);
        metadataProvider = new ChainingMetadataProvider();
        for (MetadataProvider providerGeneric : SAMLMetadataProviders) {
            FileBackedHTTPMetadataProvider provider = (FileBackedHTTPMetadataProvider) providerGeneric;
            // provider.setMinRefreshDelay(1000);
            // provider.setMaxRefreshDelay(2000);
            log.info("Loading SAML metadata [{}]", provider.getMetadataURI());
            provider.setParserPool(parser);
            provider.initialize();
            metadataProvider.addMetadataProvider(provider);
            log.debug("Loaded SAML metatada {}", provider.getMetadataURI());
        }

    }

    public Observation[] process(Observation[] observations) throws PostprocessorException {
        log.debug("{} post processor called, entries into postprocessor: {}", this.getClass(), observations.length);
        for (Observation obs : observations) {
            if (obs instanceof Group) {
                Group obsG = (Group) obs;
                String oldName = obsG.getGroupName();
                String mapTo = null;
                try {
                    mapTo = getOrganisationDisplayName(oldName);
                } catch (Exception e) {
                    // do nothing, so keep original
                }
                if (mapTo != null)
                    obsG.setGroupName(mapTo);

            }

        }
        return observations;
    }

    /**
     * This method returns the organizational display name of the entityID passed into it from the SAML metadata This
     * will fail if the organizational name is not the first name in the list of organizational names can use
     * https://idp.cardiff.ac.uk/shibboleth for testing.
     * 
     * @param entityID
     * @return
     * @throws MetadataProviderException
     */
    private String getOrganisationDisplayName(String entityID) throws MetadataProviderException, NullPointerException {
        OrganizationDisplayName orgName =
                (OrganizationDisplayName) metadataProvider.getEntityDescriptor(entityID).getOrganization()
                        .getDisplayNames().get(0);
        return orgName.getName().getLocalString();
    }

    public void registerAndSetMethodParameters(List<MethodParameter> methodParameters) {
        // nothing to do here.

    }

}
