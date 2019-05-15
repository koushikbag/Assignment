package com.example.assignment.network;

import android.annotation.SuppressLint;
import android.util.Log;

import java.security.KeyStore;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public final class RetrofitService {
    private static IRetrofitClient mRetrofitClient;

    private RetrofitService() {
        // Construct the OkHttpClient.Builder for customizing the Retrofit Client
        final OkHttpClient.Builder httpClientBuilder = getUnsafeOkhttpClient().newBuilder();

        // Create the instance of the logging interceptor
        HttpLoggingInterceptor mHttpLoggingInterceptor = new HttpLoggingInterceptor();
        // Set the log level to body ---> make sure all the detail log information will be printed in the log
        mHttpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        httpClientBuilder.connectTimeout(30, TimeUnit.SECONDS);
        httpClientBuilder.readTimeout(30, TimeUnit.SECONDS);
        httpClientBuilder.addInterceptor(mHttpLoggingInterceptor);


        String BASEURL = "https://a2b7cf8676394fda75de-6e0550a16cd96615f7274fd70fa77109.r93.cf3.rackcdn.com/";
        Retrofit.Builder rfBuilder =
                new Retrofit.Builder().client(httpClientBuilder.build())
                        .baseUrl(BASEURL)
                        .addConverterFactory(GsonConverterFactory.create());

        Retrofit retrofit = rfBuilder.client(httpClientBuilder.build()).build();
        mRetrofitClient = retrofit.create(IRetrofitClient.class);
    }

    public static IRetrofitClient getService() {
        RetrofitService mRetrofitService = new RetrofitService();
        return mRetrofitClient;
    }

    private OkHttpClient getUnsafeOkhttpClient() {
        try {
            final TrustManager[] trustAllCerts = new TrustManager[]{
                    new X509TrustManager() {
                        @SuppressLint("TrustAllX509TrustManager")
                        @Override
                        public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType) throws java.security.cert.CertificateException {
                            //check client trusted
                        }

                        @SuppressLint("TrustAllX509TrustManager")
                        @Override
                        public void checkServerTrusted(java.security.cert.X509Certificate[] chain, String authType) throws java.security.cert.CertificateException {
                            //check server trusted
                        }

                        @Override
                        public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                            return new java.security.cert.X509Certificate[]{};
                        }
                    }
            };


            OkHttpClient.Builder httpClientBuilder = new OkHttpClient.Builder();


            KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
            keyStore.load(null, null);

            SSLContext sslContext = SSLContext.getInstance("TLS");

            TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
            trustManagerFactory.init(keyStore);
            KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
            keyManagerFactory.init(keyStore, "keystore_pass".toCharArray());
            sslContext.init(null, trustAllCerts, new SecureRandom());
            httpClientBuilder.sslSocketFactory(sslContext.getSocketFactory())
                    .hostnameVerifier(new HostnameVerifier() {
                        @SuppressLint("BadHostnameVerifier")
                        @Override
                        public boolean verify(String hostname, SSLSession session) {
                            return true;
                        }
                    });
            /*TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
            trustManagerFactory.init((KeyStore) null);
            TrustManager[] trustManagers = trustManagerFactory.getTrustManagers();
            if (trustManagers.length != 1 || !(trustManagers[0] instanceof X509TrustManager)) {
                throw new IllegalStateException("Unexpected default trust managers:" + Arrays.toString(trustManagers));
            }
            X509TrustManager trustManager = (X509TrustManager) trustManagers[0];
            SSLContext sslContext = SSLContext.getInstance("SSL");
            sslContext.init(null, new TrustManager[]{trustManager}, null);
            SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();
            OkHttpClient.Builder httpClientBuilder = new OkHttpClient.Builder()
                    .sslSocketFactory(sslSocketFactory, trustManager);*/
            return httpClientBuilder.build();
        } catch (Exception e) {
            Log.d("Error", e.getMessage());
            throw new RuntimeException();
        }
    }

}
