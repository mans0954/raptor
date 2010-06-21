
package RaptorSuite.UA;

import javax.jws.WebService;

@WebService(endpointInterface = "RaptorSuite.UA.HelloWorld")
public class HelloWorldImpl implements HelloWorld {

    public String sayHi(String text) {
        return "Hello " + text;
    }
}

