
package ICAVersionTest.ICAVersionTest;

import javax.jws.WebService;

@WebService(endpointInterface = "ICAVersionTest.ICAVersionTest.HelloWorld")
public class HelloWorldImpl implements HelloWorld {

    public String sayHi(String text) {
        return "Hello " + text;
    }
}

