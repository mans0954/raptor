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
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;

import org.opensaml.DefaultBootstrap;
import org.opensaml.saml2.metadata.EntitiesDescriptor;
import org.opensaml.saml2.metadata.EntityDescriptor;
import org.opensaml.saml2.metadata.Organization;
import org.opensaml.saml2.metadata.OrganizationName;
import org.opensaml.saml2.metadata.OrganizationDisplayName;
import org.opensaml.saml2.metadata.provider.FilesystemMetadataProvider;
import org.opensaml.saml2.metadata.provider.MetadataProvider;
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
public class SamlMetadataNameFormatter implements StatisticsPostProcessor {

    /** Parser manager used to parse XML. */
    protected static BasicParserPool parser;

    /** Registered providers. */
    private List<MetadataProvider> providers;

    /** class logger */
    private final Logger log = LoggerFactory.getLogger(SamlMetadataNameFormatter.class);

    /** this is not a proper URI at the moment, just a UNC path */
    private String SAMLMetadataURI;

    public SamlMetadataNameFormatter(List<String> SAMLMetadataURIs) {	
	try {
	    providers = new ArrayList<MetadataProvider>();
	    loadSAMLMetadata(SAMLMetadataURIs);
	} catch (MetadataProviderException e) {
	    log.error("ShibbolethMetadataNameFormatter could not load SAML metadata file",e);
	  
	} catch (ConfigurationException e) {
	    log.error("Error parsing SAML metadata file ",e);
	}
    }
    
    public SamlMetadataNameFormatter(String SAMLMetadataURI) {       
        try {
            providers = new ArrayList<MetadataProvider>();
            ArrayList<String> SAMLMetadataURIs = new ArrayList<String>();
            SAMLMetadataURIs.add(SAMLMetadataURI);
            loadSAMLMetadata(SAMLMetadataURIs);
        } catch (MetadataProviderException e) {
            log.error("ShibbolethMetadataNameFormatter could not load SAML metadata file",e);
          
        } catch (ConfigurationException e) {
            log.error("Error parsing SAML metadata file ",e);
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
			          //do nothing, so keep original
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
	OrganizationName org = (OrganizationName) getEntityDescriptor(entityID).getOrganization().getOrganizationNames().get(0);
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
	OrganizationDisplayName orgName = (OrganizationDisplayName) getEntityDescriptor(entityID).getOrganization().getDisplayNames().get(0);
	return orgName.getName().getLocalString();
    }
    
    /**
     * Gets the valid metadata for a given entity.
     * 
     * @param entityID the ID of the entity
     * 
     * @return the entity's metadata or null if there is no metadata or no valid metadata
     * 
     * @throws MetadataProviderException thrown if the provider can not fetch the metadata, must not be thrown if there
     *             is simply no EntityDescriptor with the given ID
     */
    public EntityDescriptor getEntityDescriptor(String entityID) throws MetadataProviderException {      
        EntityDescriptor descriptor = null;
        try {
            for (MetadataProvider provider : providers) {
                log.debug("Checking child metadata provider for entity descriptor with entity ID: {}", entityID);
                descriptor = provider.getEntityDescriptor(entityID);
                if (descriptor != null) {
                    break;
                }
            }
        } catch (MetadataProviderException e) {
            throw e;
        }
        return descriptor;
    }

    private void loadSAMLMetadata(List<String> SAMLMetadataURIs) throws MetadataProviderException, ConfigurationException {
	DefaultBootstrap.bootstrap();
	parser = new BasicParserPool();
	parser.setNamespaceAware(true);
	
	for (String metadataURI : SAMLMetadataURIs){
        	FilesystemMetadataProvider provider = new FilesystemMetadataProvider(new File(metadataURI));
        	provider.setParserPool(parser);
        	provider.initialize();
        	providers.add(provider);
        	log.debug("Loaded SAML metatada {}",metadataURI);
	}
    }


    /**
     * @param providers the providers to set
     */
    public void setProviders(List<MetadataProvider> providers) {
        this.providers = providers;
    }


    /**
     * @return the providers
     */
    public List<MetadataProvider> getProviders() {
        return providers;
    }



}
