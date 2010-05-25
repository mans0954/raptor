package SOAPTester.SOAPTester;

import java.rmi.RemoteException;

import javax.xml.ws.Endpoint;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import javax.xml.ws.soap.SOAPBinding;

import org.apache.axis2.AxisFault;

import osl.webservices.eportal_wsdl.EportalStub.Activateportaluser;

import eportal.ActivateportaluserDocument;
import eportal.impl.ActivateportaluserDocumentImpl;





public class SimpleSOAPTester {
	private static final QName SERVICE_NAME  = new QName("http://localhost:9000/", "HelloWorld");
	private static final QName PORT_NAME   = new QName("http://localhost:9000/", "HelloWorldPort");

    private SimpleSOAPTester() {
    	 System.out.println("Starting Server");
         HelloWorldImpl implementor = new HelloWorldImpl();
         String address = "http://localhost:9000/helloWorld";
         Endpoint.publish(address, implementor);
    } 
    
    @SuppressWarnings("restriction")
	public void testClient() throws Exception{
//    	Service service = Service.create(SERVICE_NAME);
//        // Endpoint Address
//        String endpointAddress = "http://localhost:9000/helloWorld";
//
//        // Add a port to the Service
//        service.addPort(PORT_NAME, SOAPBinding.SOAP11HTTP_BINDING, endpointAddress);
//        
//        HelloWorld hw = service.getPort(HelloWorld.class);
//        System.out.println(hw.sayHiToUser("World"));
    	
//    	JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();
//    	factory.getInInterceptors().add(new LoggingInInterceptor());
//    	factory.getOutInterceptors().add(new LoggingOutInterceptor());
//    	factory.setServiceClass(HelloWorld.class);
//    	factory.setAddress("http://localhost:9000/helloWorld");
//    	HelloWorld client = (HelloWorld) factory.create();
//
//    	String reply = client.sayHiToUser("HI");
//    	System.out.println("Server said: " + reply);
//    	System.exit(0);

      EportalStub stub = new EportalStub("http://sgatwebi1.cf.ac.uk/eportal/eportal");
    	ActivateportaluserDocument object =    ActivateportaluserDocument.Factory.newInstance();
    	ActivateportaluserDocument.Activateportaluser	user = object.addNewActivateportaluser();
    //	user.setPEmail("Phil");
    //	user.setPStudentid("2323232");
       System.out.println(object);
      
       stub.activateportaluser(object);
    	eportalProxy proxy = new eportalProxy();
    	
    	System.out.println(proxy.activateportaluser("0000007", "smartp@cf.ac.uk"));
       

        
    }

    public static void main(String args[]) throws Exception {
    	SimpleSOAPTester ss = new SimpleSOAPTester();
       // System.out.println("Server ready...");
        
       // Thread.sleep(1000);
        ss.testClient();
       // System.out.println("Server exiting");
        System.exit(0);

    }

}
