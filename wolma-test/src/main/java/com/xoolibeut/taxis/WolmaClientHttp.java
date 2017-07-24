package com.xoolibeut.taxis;

import java.io.IOException;
import java.io.InputStream;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

import javax.naming.Context;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;
import javax.security.cert.CertificateException;

import okhttp3.OkHttpClient;

public class WolmaClientHttp {
	public static OkHttpClient getSSLClient() throws Exception {

		OkHttpClient client;
		SSLContext sslContext;
		SSLSocketFactory sslSocketFactory;
		TrustManager[] trustManagers;
		TrustManagerFactory trustManagerFactory;
		X509TrustManager trustManager;

		trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());

		trustManagerFactory.init(readKeyStore());

		trustManagers = trustManagerFactory.getTrustManagers();

		if (trustManagers.length != 1 || !(trustManagers[0] instanceof X509TrustManager)) {

			throw new IllegalStateException("Unexpected default trust managers:" + Arrays.toString(trustManagers));
		}

		trustManager = (X509TrustManager) trustManagers[0];

		sslContext = SSLContext.getInstance("TLS");

		sslContext.init(null, new TrustManager[] { trustManager }, null);

		sslSocketFactory = sslContext.getSocketFactory();

		client = new OkHttpClient.Builder().sslSocketFactory(sslSocketFactory, trustManager).build();

		return client.newBuilder().hostnameVerifier(new HostnameVerifier() {
			@Override
			public boolean verify(String hostname, SSLSession session) {

				return true;
			}
		}).connectTimeout(5, TimeUnit.SECONDS).readTimeout(10, TimeUnit.SECONDS).build();
	}

	private static KeyStore readKeyStore() throws KeyStoreException, CertificateException, NoSuchAlgorithmException,
			IOException, java.security.cert.CertificateException {

		KeyStore keyStore;
		char[] PASSWORD = "DemMowor!2307".toCharArray();
		ArrayList<InputStream> certificates;
		int certificateIndex;
		InputStream certificate;
		certificates = new ArrayList<>();
		certificates.add(Thread.currentThread().getContextClassLoader().getResourceAsStream("certificat.p12"));
		keyStore = KeyStore.getInstance("pkcs12");
		for (certificateIndex = 0; certificateIndex < certificates.size(); certificateIndex++) {

			certificate = certificates.get(certificateIndex);

			try {
				keyStore.load(certificate, PASSWORD);
			} finally {
				if (certificate != null) {
					certificate.close();
				}
			}
		}

		keyStore.size();

		return keyStore;
	}
}
