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
package uk.ac.cardiff.raptormua.engine.statistics;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.opensaml.DefaultBootstrap;
import org.opensaml.saml2.metadata.OrganizationName;
import org.opensaml.saml2.metadata.provider.FilesystemMetadataProvider;
import org.opensaml.saml2.metadata.provider.MetadataProvider;
import org.opensaml.saml2.metadata.provider.MetadataProviderException;
import org.opensaml.xml.ConfigurationException;
import org.opensaml.xml.parse.BasicParserPool;
//import org.opensaml.saml2.metadata.provider.FileBackedHTTPMetadataProvider;
//import org.opensaml.saml2.metadata.provider.MetadataProviderException;

import uk.ac.cardiff.model.Entry;
import uk.ac.cardiff.model.ShibbolethEntry;
import uk.ac.cardiff.raptormua.exceptions.PreprocessorException;
import uk.ac.cardiff.raptormua.runtimeutils.ReflectionHelper;



/**
 * @author philsmart
 *
 */
public class ShibbolethMetadataNameFormatter implements StatisticsPreProcessor{

	/** Parser manager used to parse XML. */
    protected static BasicParserPool parser;

    /* use SAML metadata off the filesystem*/
    private FilesystemMetadataProvider provider;

    /* class logger */
	static Logger log = Logger.getLogger(ShibbolethMetadataNameFormatter.class);

	private String mapFieldName;
	private String mapToFieldName;
	/** this is not a proper URI at the moment, just a UNC path*/
	private String SAMLMetadataURI;

	public ShibbolethMetadataNameFormatter(String SAMLMetadataURI){
		this.SAMLMetadataURI = SAMLMetadataURI;
		try {
			loadSAMLMetadata();
		} catch (MetadataProviderException e) {
			log.error("ShibbolethMetadataNameFormatter could not load SAML metadata file "+e.getMessage());
			e.printStackTrace();
		} catch (ConfigurationException e) {
			log.error("Error parsing SAML metadata file "+e.getMessage());
			e.printStackTrace();
		}
	}


	/* (non-Javadoc)
	 * @see uk.ac.cardiff.raptormua.engine.statistics.StatisticsPreProcessing#preProcess(java.util.List)
	 */
	@Override
	public List<Entry> preProcess(List<Entry> entries) throws PreprocessorException{
		ArrayList<Entry> processedList = new ArrayList<Entry>();
		log.debug("Entries into preprocessor: "+entries.size());
		for (Entry entry : entries){
			if (entry instanceof ShibbolethEntry){
				ShibbolethEntry shibEntry = (ShibbolethEntry)entry;
				Object result = ReflectionHelper.getValueFromObject(getMapFieldName(), shibEntry);
				if (result instanceof String){
					String resultAsString = (String) result;
					String mapTo=resultAsString;
					try {
						mapTo = getOrganisationName(resultAsString);
					} catch (Exception e) {
						//if the mapping fails, keep the original, so leave as original non mapped resultAsString
						log.error("Failed to map "+resultAsString);
					}
					ReflectionHelper.setValueOnObject(getMapToFieldName(), mapTo, shibEntry);
					processedList.add(shibEntry);
				}
				else{
					throw new PreprocessorException("Not a valid field for preprocessing, "+getMapFieldName());
				}
			}
			else{
				//just add it to the return list
				processedList.add(entry);
			}

		}
		log.debug("Entries out of preprocessor: "+entries.size());
		return processedList;
	}

	/**
	 * This method returns the organizational name of the entityID passed into it from the SAML metadata
	 * This will fail if the organizational name is not the first name in the list of organizational names
	 * can use https://idp.cardiff.ac.uk/shibboleth for testing.
	 * @param entityID
	 * @return
	 * @throws MetadataProviderException
	 */
	private String getOrganisationName(String entityID) throws MetadataProviderException, NullPointerException{
		//log.debug("Getting organisationName for "+entityID);
		OrganizationName org = (OrganizationName)provider.getEntityDescriptor(entityID).getOrganization().getOrganizationNames().get(0);
		//log.debug("Found organisationName '"+org.getName().getLocalString()+"'");
		return org.getName().getLocalString();
	}

	private void loadSAMLMetadata() throws MetadataProviderException, ConfigurationException{
		DefaultBootstrap.bootstrap();
		parser = new BasicParserPool();
		parser.setNamespaceAware(true);
		//FileBackedHTTPMetadataProvider fileprovider = new FileBackedHTTPMetadataProvider("http://iam.cf.ac.uk/cufederation/metadata.cufederation.xml", 100,"/Users/philsmart/shibMetadata.xml");
		provider = new FilesystemMetadataProvider(new File(SAMLMetadataURI));
		provider.setParserPool(parser);
		provider.initialize();
		log.debug("Loaded SAML metatada "+provider+" into "+this.getClass());
	}

	/**
	 *
	 * @param changeFieldName
	 */
	public void setMapFieldName(String changeFieldName) {
		this.mapFieldName = changeFieldName;
	}

	/**
	 *
	 * @return
	 */
	public String getMapFieldName() {
		return mapFieldName;
	}


	public void setMapToFieldName(String mapToFieldName) {
		this.mapToFieldName = mapToFieldName;
	}


	public String getMapToFieldName() {
		return mapToFieldName;
	}


	public void setSAMLMetadataURI(String sAMLMetadataURI) {
		SAMLMetadataURI = sAMLMetadataURI;
	}


	public String getSAMLMetadataURI() {
		return SAMLMetadataURI;
	}



}
