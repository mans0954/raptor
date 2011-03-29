package uk.ac.cardiff.raptorweb.sei;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
import uk.ac.cardiff.model.wsmodel.Capabilities;
import uk.ac.cardiff.model.wsmodel.StatisticalUnitInformation;
import uk.ac.cardiff.raptor.remoting.server.sei.MultiUnitAggregator;


/**
 * @author philsmart
 *
 *         Instances of this class are responsible for retrieving data from a service endpoint
 *
 */
public class ServiceEndpointClient {
    static Logger log = LoggerFactory.getLogger(ServiceEndpointClient.class);

    /**
     * Method to determine and return the <code>Capabilities</code> of a MultiUnitAggregator.
     * This method uses a hard set connection timeout of 10 miliseconds, and a receive timeout of
     * 20 smilieconds, under the assumption that the capabilities of a MultiUnitAggregator can be sent
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
	    httpClientPolicy.setConnectionTimeout(1000);
	    httpClientPolicy.setReceiveTimeout(2000);
	    httpConduit.setClient(httpClientPolicy);
	    log.debug("Accessing the MUA version " + client.getVersion());
	    capabilities = client.getCapabilities();
	    log.debug("Retrieved capabilities from the MUA [{}]",endpoint);
	}
	catch (SoapFault e) {
	    log.error("Problem trying to retrieving capabilities from MUA [{}] -> {}", new Object[]{endpoint, e.getMessage()});
	    capabilities = new Capabilities();
	    capabilities.setError(true);
	    capabilities.setErrorMessage(e.getMessage());
	   // e.printStackTrace();
	}catch (Exception e) {
	    log.error("Problem trying to retrieving capabilities from MUA [{}] -> {}", new Object[]{endpoint, e.getMessage()});
	    capabilities = new Capabilities();
	    capabilities.setError(true);
	    capabilities.setErrorMessage(e.getMessage());
	    //e.printStackTrace();
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
	    org.apache.cxf.endpoint.Client cl = ClientProxy.getClient(client);
	    HTTPConduit httpConduit = (HTTPConduit) cl.getConduit();
	    HTTPClientPolicy httpClientPolicy = new HTTPClientPolicy();
	    httpClientPolicy.setConnectionTimeout(100000);
	    httpClientPolicy.setReceiveTimeout(200000);
	    httpConduit.setClient(httpClientPolicy);

	    log.debug("Accessing the MUA version {}", client.getVersion());
	    log.debug("Updating statistic {} from the MUA {}", statisticalUnitInformation.getStatisticParameters().getUnitName(), endpoint);
	    client.updateStatisticalUnit(statisticalUnitInformation);

	} catch (SoapFault e) {
	    log.error("Problem trying to update statistical unit {} on MUA [{}] -> {}", new Object[]{statisticalUnitInformation.getStatisticParameters().getUnitName(), endpoint, e.getMessage()});
	    capabilities = new Capabilities();
	    capabilities.setError(true);
	    capabilities.setErrorMessage(e.getMessage());
	   // e.printStackTrace();
	}catch (Exception e) {
	    log.error("Problem trying to update statistical unit {} on MUA [{}] -> {}", new Object[]{statisticalUnitInformation.getStatisticParameters().getUnitName(), endpoint, e.getMessage()});
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
    public static AggregatorGraphModel invokeStatisticalUnit(String endpoint, String selectedStatisticalUnit) {
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
	    httpClientPolicy.setConnectionTimeout(100000);
	    httpClientPolicy.setReceiveTimeout(200000);
	    httpConduit.setClient(httpClientPolicy);

	    log.debug("Accessing the MUA version " + client.getVersion());
	    AggregatorGraphModel gmodel = client.invokeStatisticalUnit(selectedStatisticalUnit);
	    log.debug("Retrieved Graph Model from the MUA [" + endpoint + "]");
	    return gmodel;
	} catch (SoapFault e) {
	    log.error("Problem trying to invoke statistical unit {} on MUA [{}] -> {}", new Object[]{selectedStatisticalUnit, endpoint, e.getMessage()});
	} catch (Exception e){
	   //catching general errors, e.g. no connection to endpoint
	    log.error("Problem trying to invoke statistical unit {} on MUA [{}] -> {}", new Object[]{selectedStatisticalUnit, endpoint, e.getMessage()});
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

	    org.apache.cxf.endpoint.Client cl = ClientProxy.getClient(client);
	    HTTPConduit httpConduit = (HTTPConduit) cl.getConduit();
	    HTTPClientPolicy httpClientPolicy = new HTTPClientPolicy();
	    httpClientPolicy.setConnectionTimeout(100000);
	    httpClientPolicy.setReceiveTimeout(200000);
	    httpConduit.setClient(httpClientPolicy);

	    // client.invokeStatisticalUnit(selectedStatisticalUnit);
	    log.debug("Accessing the MUA version {}",client.getVersion());
	    boolean success = client.performAdministrativeFunction(function);

	    return success;
	}catch (SoapFault e) {
	    log.error("Problem trying to perform administrative function {} on MUA {} -> {}  ", new Object[]{function.getAdministrativeFunction().toString(), endpoint, e.getMessage()});
	    return false;
	} catch (Exception e){
	    log.error("Problem trying to perform administrative function {} on MUA {} -> {} ", new Object[]{function.getAdministrativeFunction().toString(), endpoint, e.getMessage()});
	    log.error("Details, {}",e);
	    return false;
	}


    }

    public static AggregatorGraphModel updateAndinvokeStatisticalUnit(String serviceEndpoint, StatisticalUnitInformation statisticalUnit) {
	try {
	    ClientProxyFactoryBean factory = new ClientProxyFactoryBean();
	    factory.setServiceClass(MultiUnitAggregator.class);
	    AegisDatabinding databinding = new AegisDatabinding();
	    factory.setAddress(serviceEndpoint);
	    factory.getServiceFactory().setDataBinding(databinding);
	    MultiUnitAggregator client = (MultiUnitAggregator) factory.create();

	    org.apache.cxf.endpoint.Client cl = ClientProxy.getClient(client);
	    HTTPConduit httpConduit = (HTTPConduit) cl.getConduit();
	    HTTPClientPolicy httpClientPolicy = new HTTPClientPolicy();
	    httpClientPolicy.setConnectionTimeout(100000);
	    httpClientPolicy.setReceiveTimeout(200000);
	    httpConduit.setClient(httpClientPolicy);

	    log.debug("Accessing the MUA version " + client.getVersion());
	    AggregatorGraphModel gmodel = client.updateAndInvokeStatisticalUnit(statisticalUnit);
	    log.debug("Retrieved Graph Model from the MUA [" + serviceEndpoint + "]");
	    return gmodel;
	} catch (SoapFault e) {
	    log.error("Problem trying to update and invoke statistical unit {} on MUA with error {}",statisticalUnit.getStatisticParameters().getUnitName(), e.getMessage());
	}
	catch (Exception e){
	    log.error("Problem trying to update and invoke statistical unit {} on MUA {} ", statisticalUnit.getStatisticParameters().getUnitName(), e.getMessage());
	}
	return null;
    }

}
