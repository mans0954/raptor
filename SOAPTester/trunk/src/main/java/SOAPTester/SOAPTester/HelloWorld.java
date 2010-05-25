package SOAPTester.SOAPTester;

import javax.jws.WebParam;
import javax.jws.WebService;


@WebService
public interface HelloWorld {
	 /* Advanced usecase of passing an Interface in.  JAX-WS/JAXB does not
     * support interfaces directly.  Special XmlAdapter classes need to
     * be written to handle them
     */
    String sayHiToUser(@WebParam(name="text") String user);


}
