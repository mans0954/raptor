/**
 *
 */
package uk.ac.cardiff.raptor.remoting.client.sei.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;

import javax.net.ssl.KeyManager;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;

import org.apache.cxf.configuration.jsse.TLSClientParameters;

import uk.ac.cardiff.raptor.registry.Endpoint;

/**
 * Raptor client specific class to hold TLS parameters for easy configuration. Will then
 * produce an implementation specific TLS parameter class. Like a factory.
 *
 * @author philsmart
 *
 */
public class ClientTLSParameters {

    	/** The public certificates for trusted servers*/
	private String trustStoreLocation;

	/** The private certificate for this client */
	private String keyStoreLocation;

	/** The password for the trust store*/
	private String trustStorePassword;

	/** The password for the key store */
	private String keyStorePassword;

	public TLSClientParameters getTlsClientParameters() throws KeyStoreException, NoSuchAlgorithmException, CertificateException, FileNotFoundException, IOException, UnrecoverableKeyException{
	    TLSClientParameters tls = new TLSClientParameters();

  	    tls.setDisableCNCheck(true);//disable URL and CN on cert match

  	    //clients private key / public key
  	    KeyStore keyStoreKeyManager = KeyStore.getInstance("JKS");
  	    File keyStoreFile = new File(keyStoreLocation);
  	    keyStoreKeyManager.load(new FileInputStream(keyStoreFile),  keyStorePassword.toCharArray());
  	    KeyManagerFactory keyFactory = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
  	    keyFactory.init(keyStoreKeyManager, keyStorePassword.toCharArray());

  	    KeyManager[] km = keyFactory.getKeyManagers();
  	    tls.setKeyManagers(km);

  	    //servers public key
  	    KeyStore keyStore = KeyStore.getInstance("JKS");
  	    File truststore = new File(trustStoreLocation);
  	    keyStore.load(new FileInputStream(truststore),  trustStorePassword.toCharArray());
  	    TrustManagerFactory trustFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
  	    trustFactory.init(keyStore);

  	    TrustManager[] tm = trustFactory.getTrustManagers();
  	    tls.setTrustManagers(tm);

  	    return tls;

	}

	public void setTrustStoreLocation(String trustStoreLocation) {
		this.trustStoreLocation = trustStoreLocation;
	}

	public String getTrustStoreLocation() {
		return trustStoreLocation;
	}

	public void setKeyStoreLocation(String keyStoreLocation) {
		this.keyStoreLocation = keyStoreLocation;
	}

	public String getKeyStoreLocation() {
		return keyStoreLocation;
	}

	public void setTrustStorePassword(String trustStorePassword) {
		this.trustStorePassword = trustStorePassword;
	}

	public String getTrustStorePassword() {
		return trustStorePassword;
	}

	public void setKeyStorePassword(String keyStorePassword) {
		this.keyStorePassword = keyStorePassword;
	}

	public String getKeyStorePassword() {
		return keyStorePassword;
	}

}
