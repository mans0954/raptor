package uk.ac.cardiff.raptorweb.sei;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.cxf.aegis.databinding.AegisDatabinding;
import org.apache.cxf.frontend.ClientProxy;
import org.apache.cxf.frontend.ClientProxyFactoryBean;
import org.apache.cxf.jaxrs.client.Client;
import org.apache.cxf.transport.http.HTTPConduit;
import org.apache.cxf.transports.http.configuration.HTTPClientPolicy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import uk.ac.cardiff.model.AdministrativeFunction;
import uk.ac.cardiff.model.AuthenticationEntry;
import uk.ac.cardiff.model.Entry;
import uk.ac.cardiff.model.ShibbolethEntry;
import uk.ac.cardiff.model.UsageEntry;
import uk.ac.cardiff.model.Graph.AggregatorGraphModel;
import uk.ac.cardiff.model.wsmodel.Capabilities;
import uk.ac.cardiff.model.wsmodel.StatisticalUnitInformation;
import uk.ac.cardiff.raptorweb.model.MUAEntry;
import uk.ac.cardiff.sei.MultiUnitAggregator;
import uk.ac.cardiff.sei.UnitAggregator;

/**
 * @author philsmart
 *
 *         Instances of this class are responsible for retrieving data from a service endpoint
 *
 */
public class ServiceEndpointInterface {
    static Logger log = LoggerFactory.getLogger(ServiceEndpointInterface.class);

    /**
     * Method to determine and return the <code>Capabilities</code> of a MultiUnitAggregator.
     * This method uses a hard set connection timeout of 5 seconds, and a receive timeout of
     * 20 seconds, under the assumption that the capabilities of a MultiUnitAggregator can be sent
     * inside small XML documents.
     *
     * @param endpoint
     * @return
     */
    public static Capabilities discoverMUACapabilities(String endpoint) {
	Capabilities capabilities = null;
	try {
	    ClientProxyFactoryBean factory = new ClientProxyFactoryBean();
	    factory.setServiceClass(MultiUnitAggregator.class);
	    AegisDatabinding databinding = new AegisDatabinding();
	    factory.setAddress(endpoint);
	    factory.getServiceFactory().setDataBinding(databinding);

	    MultiUnitAggregator client = (MultiUnitAggregator) factory.create();
	    org.apache.cxf.endpoint.Client cl = ClientProxy.getClient(client);
	    HTTPConduit httpConduit = (HTTPConduit) cl.getConduit();
	    HTTPClientPolicy httpClientPolicy = new HTTPClientPolicy();
	    httpClientPolicy.setConnectionTimeout(5);
	    httpClientPolicy.setReceiveTimeout(20);
	    httpConduit.setClient(httpClientPolicy);
	    log.debug("Accessing the MUA version " + client.getVersion());
	    capabilities = client.getCapabilities();
	    log.debug("Retrieved capabilities from the MUA [{}]",endpoint);
	} catch (Exception e) {
	    capabilities = new Capabilities();
	    capabilities.setError(true);
	    capabilities.setErrorMessage(e.getMessage());
	    log.error("Error retrieving capabilities from MUA [{}], {}",endpoint,e);
	    e.printStackTrace();
	}
	return capabilities;

    }

    /**
     * This method sends a <code>StatisticalUnitInformaiton</code> instance to the MultiUnitAggregator <code>endpoint</code>
     * The <code>StatisicalUnitInformation</code> instance encapsulates the parameters for a single statistical unit.
     * Allowing the values to be sent back and changed on the MultiUnitAggregator
     *
     * @param endpoint
     * @param statisticalUnitInformation
     * @return
     */
    public static Capabilities updateStatisticalUnit(String endpoint, StatisticalUnitInformation statisticalUnitInformation) {
	Capabilities capabilities = null;
	try {
	    ClientProxyFactoryBean factory = new ClientProxyFactoryBean();
	    factory.setServiceClass(MultiUnitAggregator.class);
	    AegisDatabinding databinding = new AegisDatabinding();
	    factory.setAddress(endpoint);
	    factory.getServiceFactory().setDataBinding(databinding);

	    MultiUnitAggregator client = (MultiUnitAggregator) factory.create();
	    log.debug("Accessing the MUA version {}", client.getVersion());
	    log.debug("Updating statistic {} from the MUA {}", statisticalUnitInformation.getStatisticParameters().getUnitName(), endpoint);
	    client.updateStatisticalUnit(statisticalUnitInformation);

	} catch (Exception e) {
	    capabilities = new Capabilities();
	    capabilities.setError(true);
	    capabilities.setErrorMessage(e.getMessage());
	    e.printStackTrace();
	}
	return capabilities;

    }


    /**
     * @param selectedStatisticalUnit
     */
    public static AggregatorGraphModel invokeStatisticalUnit(String endpoint, String selectedStatisticalUnit) {
	try {
	    ClientProxyFactoryBean factory = new ClientProxyFactoryBean();
	    factory.setServiceClass(MultiUnitAggregator.class);
	    AegisDatabinding databinding = new AegisDatabinding();
	    factory.setAddress(endpoint);
	    factory.getServiceFactory().setDataBinding(databinding);
	    MultiUnitAggregator client = (MultiUnitAggregator) factory.create();
	    log.debug("Accessing the MUA version " + client.getVersion());
	    AggregatorGraphModel gmodel = client.invokeStatisticalUnit(selectedStatisticalUnit);
	    log.debug("Retrieved Graph Model from the MUA [" + endpoint + "]");
	    return gmodel;
	} catch (Exception e) {
	    log.error("Problem trying to invoke statistical unit " + selectedStatisticalUnit + " on MUA -> " + e.getMessage());
	}
	return null;
    }

    /**
     * @param endpoint
     * @param removeall
     */
    public static boolean invokeAdministrativeFunction(String endpoint, AdministrativeFunction function) {
	try {
	    ClientProxyFactoryBean factory = new ClientProxyFactoryBean();
	    factory.setServiceClass(MultiUnitAggregator.class);
	    AegisDatabinding databinding = new AegisDatabinding();
	    factory.setAddress(endpoint);
	    factory.getServiceFactory().setDataBinding(databinding);

	    MultiUnitAggregator client = (MultiUnitAggregator) factory.create();
	    // client.invokeStatisticalUnit(selectedStatisticalUnit);
	    log.debug("Accessing the MUA version {}",client.getVersion());
	    boolean success = client.performAdministrativeFunction(function);

	    return success;
	} catch (Exception e) {
	    log.error("Problem trying to perform administrative function {} on MUA {} ", function.getAdministrativeFunction().toString(), endpoint);
	    log.error("Details, {}",e);
	    return false;
	}

    }

}
