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

import java.io.File;

import org.opensaml.DefaultBootstrap;
import org.opensaml.saml2.metadata.Organization;
import org.opensaml.saml2.metadata.OrganizationName;
import org.opensaml.saml2.metadata.OrganizationDisplayName;
import org.opensaml.saml2.metadata.provider.FilesystemMetadataProvider;
import org.opensaml.saml2.metadata.provider.MetadataProviderException;
import org.opensaml.xml.ConfigurationException;
import org.opensaml.xml.parse.BasicParserPool; //import org.opensaml.saml2.metadata.provider.FileBackedHTTPMetadataProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
//import org.opensaml.saml2.metadata.provider.MetadataProviderException;

import uk.ac.cardiff.raptormua.engine.statistics.StatisticsPostProcessor;
import uk.ac.cardiff.raptormua.engine.statistics.records.Group;
import uk.ac.cardiff.raptormua.engine.statistics.records.Observation;


/**
 * @author philsmart
 *
 */
public class ShibbolethMetadataNameFormatter implements StatisticsPostProcessor {

    /** Parser manager used to parse XML. */
    protected static BasicParserPool parser;

    /** use SAML metadata off the filesystem */
    private FilesystemMetadataProvider provider;

    /** class logger */
    private final Logger log = LoggerFactory.getLogger(ShibbolethMetadataNameFormatter.class);

    /** this is not a proper URI at the moment, just a UNC path */
    private String SAMLMetadataURI;

    public ShibbolethMetadataNameFormatter(String SAMLMetadataURI) {
	this.SAMLMetadataURI = SAMLMetadataURI;
	try {
	    loadSAMLMetadata();
	} catch (MetadataProviderException e) {
	    log.error("ShibbolethMetadataNameFormatter could not load SAML metadata file " + e.getMessage());
	    // e.printStackTrace();
	} catch (ConfigurationException e) {
	    log.error("Error parsing SAML metadata file " + e.getMessage());
	    // e.printStackTrace();
	}
    }


	public Observation[] postProcess(Observation[] observations) throws PostprocessorException {
		log.debug("Entries into postprocessor: {}",observations.length);
		for (Observation obs : observations) {
		    if (obs instanceof Group) {
		    	Group obsG = (Group)obs;
		    	String oldName = obsG.getGroupName();
		    	String mapTo = null;
			    try {
			    	mapTo = getOrganisationDisplayName(oldName);
			    } catch (Exception e) {
				// if the mapping fails, keep the original, so leave as original non mapped resultAsString
				// log.error("Failed to map "+oldName);
				//e.printStackTrace();
			    }
			    if (mapTo!=null)
			    	obsG.setGroupName(mapTo);

		    }

		}
		return observations;
	}

    /**
     * This method returns the organizational name of the entityID passed into it from the SAML metadata This will fail if the organizational name is not the
     * first name in the list of organizational names can use https://idp.cardiff.ac.uk/shibboleth for testing.
     *
     * @param entityID
     * @return
     * @throws MetadataProviderException
     */
    private String getOrganisationName(String entityID) throws MetadataProviderException, NullPointerException {
	// log.debug("Getting organisationName for "+entityID);
	OrganizationName org = (OrganizationName) provider.getEntityDescriptor(entityID).getOrganization().getOrganizationNames().get(0);
        log.debug("Found organisationName '"+org.getName().getLocalString()+"'");
	return org.getName().getLocalString();
    }

    /**
     * This method returns the organizational display name of the entityID passed into it from the SAML metadata This will fail if the organizational name is not the
     * first name in the list of organizational names can use https://idp.cardiff.ac.uk/shibboleth for testing.
     *
     * @param entityID
     * @return
     * @throws MetadataProviderException
     */
    private String getOrganisationDisplayName(String entityID) throws MetadataProviderException, NullPointerException {
	// log.debug("Getting organisationName for "+entityID);
	//Organization org = provider.getEntityDescriptor(entityID).getOrganization();
	OrganizationDisplayName orgName = (OrganizationDisplayName) provider.getEntityDescriptor(entityID).getOrganization().getDisplayNames().get(0);
	// log.debug("Found organisationName '"+orgName.getName().getLocalString()+"'");
	return orgName.getName().getLocalString();
    }

    private void loadSAMLMetadata() throws MetadataProviderException, ConfigurationException {
	DefaultBootstrap.bootstrap();
	parser = new BasicParserPool();
	parser.setNamespaceAware(true);
	// FileBackedHTTPMetadataProvider fileprovider = new FileBackedHTTPMetadataProvider("http://iam.cf.ac.uk/cufederation/metadata.cufederation.xml",
	// 100,"/Users/philsmart/shibMetadata.xml");
	provider = new FilesystemMetadataProvider(new File(SAMLMetadataURI));
	provider.setParserPool(parser);
	provider.initialize();
	log.debug("Loaded SAML metatada " + provider + " into " + this.getClass());
    }


    public void setSAMLMetadataURI(String sAMLMetadataURI) {
	SAMLMetadataURI = sAMLMetadataURI;
    }

    public String getSAMLMetadataURI() {
	return SAMLMetadataURI;
    }



}
