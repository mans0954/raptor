
package RaptorSuite.MUA;

import javax.jws.WebService;

@WebService(endpointInterface = "RaptorSuite.MUA.HelloWorld")
public class HelloWorldImpl implements HelloWorld {

    public String sayHi(String text) {
        return "Hello " + text;
    }
}

