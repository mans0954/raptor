
package ICAVersionTest2.ICAVersionTest2;

import javax.jws.WebService;

@WebService(endpointInterface = "ICAVersionTest2.ICAVersionTest2.HelloWorld")
public class HelloWorldImpl implements HelloWorld {

    public String sayHi(String text) {
        return "Hello " + text;
    }
}

