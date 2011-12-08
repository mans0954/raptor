/**
 * 
 */
package uk.ac.cardiff.raptor.remoting.client.sei.impl;

import java.io.File;

import org.testng.annotations.Test;

/**
 * @author philsmart
 *
 */
public class ClientTLSParametersTest {
    
    @Test
    public void getTrustStoreLocationTest(){
        File trustStoreLocation = new File("/Users/philsmart/Documents/Java/RaptorWorkspace/keys/raptor-ica.jks");
        System.out.println("location "+trustStoreLocation+" exists "+trustStoreLocation.exists());
        
    }

}
