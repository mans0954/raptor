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

package uk.ac.cardiff.raptorweb.sei;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.util.ArrayList;
import java.util.List;

import org.apache.cxf.aegis.databinding.AegisDatabinding;
import org.apache.cxf.binding.soap.SoapFault;
import org.apache.cxf.frontend.ClientProxy;
import org.apache.cxf.frontend.ClientProxyFactoryBean;
import org.apache.cxf.transport.http.HTTPConduit;
import org.apache.cxf.transports.http.configuration.HTTPClientPolicy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import uk.ac.cardiff.model.AdministrativeFunction;
import uk.ac.cardiff.model.report.AggregatorGraphModel;
import uk.ac.cardiff.model.resource.ResourceMetadata;
import uk.ac.cardiff.model.wsmodel.Capabilities;
import uk.ac.cardiff.model.wsmodel.DynamicStatisticalUnitInformation;
import uk.ac.cardiff.model.wsmodel.LogFileUpload;
import uk.ac.cardiff.model.wsmodel.LogFileUploadResult;
import uk.ac.cardiff.model.wsmodel.StatisticFunctionType;
import uk.ac.cardiff.model.wsmodel.StatisticalUnitInformation;
import uk.ac.cardiff.raptor.remoting.client.sei.impl.ClientTLSParameters;
import uk.ac.cardiff.raptor.remoting.server.sei.MultiUnitAggregator;
import uk.ac.cardiff.raptorweb.model.MUAEntry;

/**
 * 
 * 
 * @author philsmart
 * 
 *         Instances of this class are responsible for retrieving data from a service endpoint. This class should not be
 *         static and should not recreate the Client for every request.
 * 
 */
public class ServiceEndpointClient {

    /** Class logger */
    private final Logger log = LoggerFactory.getLogger(ServiceEndpointClient.class);

    /** Raptor specific TLS parameters class, that can return cxf TLSParameters */
    private ClientTLSParameters tlsParameters;

    /** How long to wait, in ms, before the connection attempt times out. */
    private int connectionTimeout;

    /** How long to wait, in ms, for the web service call to respond before failing */
    private int receiveTimeout;

    public MultiUnitAggregator getEndpointConnection(MUAEntry endpoint) throws UnrecoverableKeyException,
            KeyStoreException, NoSuchAlgorithmException, CertificateException, FileNotFoundException, IOException {
        ClientProxyFactoryBean factory = new ClientProxyFactoryBean();
        factory.setServiceClass(MultiUnitAggregator.class);
        AegisDatabinding databinding = new AegisDatabinding();
        factory.setAddress(endpoint.getServiceEndpoint());
        factory.getServiceFactory().setDataBinding(databinding);

        MultiUnitAggregator client = (MultiUnitAggregator) factory.create();
        org.apache.cxf.endpoint.Client cl = ClientProxy.getClient(client);
        HTTPConduit httpConduit = (HTTPConduit) cl.getConduit();
        HTTPClientPolicy httpClientPolicy = new HTTPClientPolicy();
        httpClientPolicy.setConnectionTimeout(connectionTimeout);
        httpClientPolicy.setReceiveTimeout(receiveTimeout);
        httpConduit.setClient(httpClientPolicy);

        if (tlsParameters != null)
            httpConduit.setTlsClientParameters(tlsParameters.getTlsClientParameters());

        return client;
    }

    /**
     * Method to determine and return the <code>Capabilities</code> of a MultiUnitAggregator. This method uses a hard
     * set connection timeout of 10 miliseconds, and a receive timeout of 20 smilieconds, under the assumption that the
     * capabilities of a MultiUnitAggregator can be sent inside small XML documents.
     * 
     * @param endpoint
     * @return
     */
    public Capabilities discoverMUACapabilities(MUAEntry endpoint) {
        Capabilities capabilities = null;
        try {
            MultiUnitAggregator client = getEndpointConnection(endpoint);
            log.debug("Accessing the MUA version " + client.getVersion());
            capabilities = client.getCapabilities();
            log.debug("Retrieved capabilities [{}] from the MUA [{}]", (capabilities != null), endpoint);
        } catch (SoapFault e) {
            log.error("SOAP Fault, Problem trying to retrieving capabilities from MUA [{}]", new Object[] {endpoint}, e);
            capabilities = new Capabilities();
            capabilities.setError(true);
            capabilities.setErrorMessage(e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            log.error("Problem trying to retrieving capabilities from MUA [{}]", new Object[] {endpoint}, e);
            capabilities = new Capabilities();
            capabilities.setError(true);
            capabilities.setErrorMessage(e.getMessage());
            e.printStackTrace();
        }
        return capabilities;

    }

    /**
     * @param selectedStatisticFunctionType
     * @return
     */
    public StatisticalUnitInformation lookupStatisticalUnitInformation(MUAEntry endpoint,
            StatisticFunctionType selectedStatisticFunctionType) {
        StatisticalUnitInformation statisticalInformation = null;
        try {
            MultiUnitAggregator client = getEndpointConnection(endpoint);
            log.debug("Accessing the MUA version " + client.getVersion());
            statisticalInformation = client.getStatisticalUnitInformation(selectedStatisticFunctionType);
            log.debug("Retrieved statistical unit information from the MUA [{}]", endpoint);
        } catch (SoapFault e) {
            log.error("SOAP Fault, Problem retrieving statistical unit information from MUA [{}]",
                    new Object[] {endpoint}, e);
            e.printStackTrace();
        } catch (Exception e) {
            log.error("Problem retrieving statistical unit information from MUA [{}]", new Object[] {endpoint}, e);
            e.printStackTrace();
        }
        return statisticalInformation;
    }

    /**
     * This method sends a <code>StatisticalUnitInformaiton</code> instance to the MultiUnitAggregator
     * <code>endpoint</code> The <code>StatisicalUnitInformation</code> instance encapsulates the parameters for a
     * single statistical unit. Allowing the values to be sent back and changed on the MultiUnitAggregator
     * 
     * @param endpoint
     * @param statisticalUnitInformation
     * @return
     */
    public Capabilities updateStatisticalUnit(MUAEntry endpoint, StatisticalUnitInformation statisticalUnitInformation) {
        Capabilities capabilities = null;
        try {
            MultiUnitAggregator client = getEndpointConnection(endpoint);
            log.debug("Accessing the MUA version {}", client.getVersion());
            log.debug("Updating statistic {} from the MUA {}", statisticalUnitInformation.getStatisticParameters()
                    .getUnitName(), endpoint);
            client.updateStatisticalUnit(statisticalUnitInformation);

        } catch (SoapFault e) {
            log.error("SOAP Fault, Problem trying to update statistical unit {} on MUA [{}] -> {}", new Object[] {
                    statisticalUnitInformation.getStatisticParameters().getUnitName(), endpoint, e.getMessage()});
            capabilities = new Capabilities();
            capabilities.setError(true);
            capabilities.setErrorMessage(e.getMessage());
            // e.printStackTrace();
        } catch (Exception e) {
            log.error("Problem trying to update statistical unit {} on MUA [{}] -> {}", new Object[] {
                    statisticalUnitInformation.getStatisticParameters().getUnitName(), endpoint, e.getMessage()});
            capabilities = new Capabilities();
            capabilities.setError(true);
            capabilities.setErrorMessage(e.getMessage());
            // e.printStackTrace();
        }
        return capabilities;

    }

    /**
     * @param selectedStatisticalUnit
     */
    public AggregatorGraphModel invokeStatisticalUnit(MUAEntry endpoint, String selectedStatisticalUnit) {
        try {
            MultiUnitAggregator client = getEndpointConnection(endpoint);
            log.debug("Accessing the MUA version " + client.getVersion());
            AggregatorGraphModel gmodel = client.invokeStatisticalUnit(selectedStatisticalUnit);
            log.debug("Retrieved Graph Model from the MUA [" + endpoint + "]");
            return gmodel;
        } catch (SoapFault e) {
            log.error("SOAP Fault, Problem trying to invoke statistical unit {} on MUA [{}] -> {}", new Object[] {
                    selectedStatisticalUnit, endpoint, e.getMessage()});
        } catch (Exception e) {
            // catching general errors, e.g. no connection to endpoint
            log.error("Problem trying to invoke statistical unit {} on MUA [{}] -> {}", new Object[] {
                    selectedStatisticalUnit, endpoint, e.getMessage()});
        }

        return null;
    }

    /**
     * @param endpoint
     * @param removeall
     */
    public boolean invokeAdministrativeFunction(MUAEntry endpoint, AdministrativeFunction function) {
        try {
            MultiUnitAggregator client = getEndpointConnection(endpoint);
            log.debug("Accessing the MUA version {}", client.getVersion());
            boolean success = client.performAdministrativeFunction(function);

            return success;
        } catch (SoapFault e) {
            log.error("SOAP Fault, Problem trying to perform administrative function {} on MUA {} -> {}  ",
                    new Object[] {function.getAdministrativeFunction().toString(), endpoint, e.getMessage()});
            return false;
        } catch (Exception e) {
            log.error("Problem trying to perform administrative function {} on MUA {} -> {} ", new Object[] {
                    function.getAdministrativeFunction().toString(), endpoint, e.getMessage()});
            log.error("Details, {}", e);
            return false;
        }

    }

    public List<LogFileUploadResult> sendBatch(ArrayList<LogFileUpload> uploadFiles, MUAEntry endpoint) {
        try {
            MultiUnitAggregator client = getEndpointConnection(endpoint);
            log.debug("Accessing the MUA version {}", client.getVersion());
            return client.batchUpload(uploadFiles);

        } catch (SoapFault e) {
            log.error("SOAP Fault, Problem trying to send batch log files to MUA {} -> {}  ",
                    new Object[] {endpoint, e.getMessage()});
            return null;
        } catch (Exception e) {
            log.error("Problem trying to send batch log files to MUA {} -> {} ",
                    new Object[] {endpoint, e.getMessage()});
            log.error("Details, {}", e);
            return null;
        }

    }

    /**
     * @param statisticalUnitInformations
     */
    public AggregatorGraphModel invokeStatisticalUnitDynamic(MUAEntry endpoint,
            DynamicStatisticalUnitInformation statisticalUnitInformations) {
        try {
            MultiUnitAggregator client = getEndpointConnection(endpoint);
            log.debug("Accessing the MUA version {}", client.getVersion());
            AggregatorGraphModel model = client.invokeStatisticalUnitDynamically(statisticalUnitInformations);
            return model;
        } catch (SoapFault e) {
            log.error("Problem trying to invoke a statistic dynamically on MUA {} -> {}  ",
                    new Object[] {endpoint, e.getMessage()});

        } catch (Exception e) {
            log.error("Problem trying to invoke a statistic dynamically on MUA {} -> {} ",
                    new Object[] {endpoint, e.getMessage()});
            log.error("Details, {}", e);

        }
        return null;
    }

    public void sendResourceMetadata(List<ResourceMetadata> resourceMetadata, MUAEntry endpoint) {
        try {
            MultiUnitAggregator client = getEndpointConnection(endpoint);
            log.debug("Accessing the MUA version " + client.getVersion());
            log.debug("Sending resource classification to MUA");
            client.saveResourceMetadata(resourceMetadata);
        } catch (SoapFault e) {
            log.error("Problem trying to send resource metadata to MUA with error {}", e.getMessage());
        } catch (Exception e) {
            log.error("Problem trying to send resource metadata to MUA with error {} ", e.getMessage());
        }

    }

    public AggregatorGraphModel updateAndinvokeStatisticalUnit(MUAEntry endpoint,
            StatisticalUnitInformation statisticalUnit) {
        try {
            MultiUnitAggregator client = getEndpointConnection(endpoint);
            log.debug("Accessing the MUA version " + client.getVersion());
            log.trace("Sending end date [{}] and start date [{}]", statisticalUnit.getStatisticParameters()
                    .getEndTimeAsDate(), statisticalUnit.getStatisticParameters().getStartTimeAsDate());
            AggregatorGraphModel gmodel = client.updateAndInvokeStatisticalUnit(statisticalUnit);
            log.debug("Retrieved Graph Model from the MUA [" + endpoint.getServiceEndpoint() + "]");
            return gmodel;
        } catch (SoapFault e) {
            log.error("Problem trying to update and invoke statistical unit {} on MUA with error {}", statisticalUnit
                    .getStatisticParameters().getUnitName(), e.getMessage());
        } catch (Exception e) {
            log.error("Problem trying to update and invoke statistical unit {} on MUA {} ", statisticalUnit
                    .getStatisticParameters().getUnitName(), e.getMessage());
        }
        return null;
    }

    /**
     * @param tlsParameters the tlsParameters to set
     */
    public void setTlsParameters(ClientTLSParameters tlsParameters) {
        this.tlsParameters = tlsParameters;
    }

    /**
     * @return the tlsParameters
     */
    public ClientTLSParameters getTlsParameters() {
        return tlsParameters;
    }

    /**
     * @param connectionTimeout the connectionTimeout to set
     */
    public void setConnectionTimeout(int connectionTimeout) {
        this.connectionTimeout = connectionTimeout;
    }

    /**
     * @return the connectionTimeout
     */
    public int getConnectionTimeout() {
        return connectionTimeout;
    }

    /**
     * @param receiveTimeout the receiveTimeout to set
     */
    public void setReceiveTimeout(int receiveTimeout) {
        this.receiveTimeout = receiveTimeout;
    }

    /**
     * @return the receiveTimeout
     */
    public int getReceiveTimeout() {
        return receiveTimeout;
    }

}
