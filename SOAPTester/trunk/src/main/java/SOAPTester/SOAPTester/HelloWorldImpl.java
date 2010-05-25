package SOAPTester.SOAPTester;

import javax.jws.WebService;

@WebService(endpointInterface = "SOAPTester.SOAPTester.HelloWorld", serviceName = "HelloWorld")
public class HelloWorldImpl implements HelloWorld {

	public String sayHiToUser(String user) {
		// TODO Auto-generated method stub
		return "HI: "+user;
	}

}
