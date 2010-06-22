/**
 *
 */
package main.uk.ac.cf.wsinterface;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import main.uk.ac.cf.model.AuthenticationEntry;
import main.uk.ac.cf.model.Entry;
import main.uk.ac.cf.model.ShibbolethEntry;
import main.uk.ac.cf.model.UsageEntry;

import org.apache.cxf.aegis.databinding.AegisDatabinding;
import org.apache.cxf.frontend.ClientProxyFactoryBean;

/**
 * @author philsmart
 *
 */
public class Client {

    public static void main(String args[]){
	ClientProxyFactoryBean factory = new ClientProxyFactoryBean();
	factory.setServiceClass(Collector.class);
	//factory.setServiceName(new QName("http://impl.wsinterface.cf.ac.uk.main/", "CollectorImplService"));
	AegisDatabinding databinding = new AegisDatabinding();


        Set<String> overrides = new HashSet();
        overrides.add(ShibbolethEntry.class.getName());
        overrides.add(AuthenticationEntry.class.getName());
        overrides.add(UsageEntry.class.getName());
        databinding.setOverrideTypes(overrides);

	factory.setAddress("http://localhost:8080/ICA/Collector");
	//factory.setWsdlLocation("http://localhost:8080/ICA/Collector?wsdl");
	factory.getServiceFactory().setDataBinding(databinding);

	Collector client = (Collector) factory.create();
	System.out.println(client.getVersion());
	List<Entry> auths = client.getAllAuthentications();
	System.out.println("Have "+auths.size());
	for (Entry ent : auths){

		System.out.println(ent+" "+ent.getClass());

	}
    }

}
