/**
 * Copyright (C) 2010 Cardiff University, Wales <smartp@cf.ac.uk>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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
	    final TLSClientParameters tls = new TLSClientParameters();

  	    tls.setDisableCNCheck(true);//disable URL and CN on cert match

  	    //clients private key / public key
  	    final KeyStore keyStoreKeyManager = KeyStore.getInstance("JKS");
  	    final File keyStoreFile = new File(keyStoreLocation);
  	    keyStoreKeyManager.load(new FileInputStream(keyStoreFile),  keyStorePassword.toCharArray());
  	    final KeyManagerFactory keyFactory = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
  	    keyFactory.init(keyStoreKeyManager, keyStorePassword.toCharArray());

  	    final KeyManager[] km = keyFactory.getKeyManagers();
  	    tls.setKeyManagers(km);

  	    //servers public key
  	    final KeyStore keyStore = KeyStore.getInstance("JKS");
  	    final File truststore = new File(trustStoreLocation);
  	    keyStore.load(new FileInputStream(truststore),  trustStorePassword.toCharArray());
  	    final TrustManagerFactory trustFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
  	    trustFactory.init(keyStore);

  	    final TrustManager[] tm = trustFactory.getTrustManagers();
  	    tls.setTrustManagers(tm);

  	    return tls;

	}

	public void setTrustStoreLocation(final String trustStoreLocation) {
		this.trustStoreLocation = trustStoreLocation;
	}

	public String getTrustStoreLocation() {
		return trustStoreLocation;
	}

	public void setKeyStoreLocation(final String keyStoreLocation) {
		this.keyStoreLocation = keyStoreLocation;
	}

	public String getKeyStoreLocation() {
		return keyStoreLocation;
	}

	public void setTrustStorePassword(final String trustStorePassword) {
		this.trustStorePassword = trustStorePassword;
	}

	public String getTrustStorePassword() {
		return trustStorePassword;
	}

	public void setKeyStorePassword(final String keyStorePassword) {
		this.keyStorePassword = keyStorePassword;
	}

	public String getKeyStorePassword() {
		return keyStorePassword;
	}

}
