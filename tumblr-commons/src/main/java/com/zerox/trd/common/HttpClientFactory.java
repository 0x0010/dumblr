package com.zerox.trd.common;

import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

/**
 * Http Client Factory
 * Created by Sam on 10/5/16.
 */
public class HttpClientFactory {

    private HttpClientFactory(){}

    private RequestConfig rc = RequestConfig.custom().setConnectTimeout(10000).build();

    private CloseableHttpClient httpClient =
            HttpClientBuilder.create().setDefaultRequestConfig(rc)
                    .disableAuthCaching().disableAutomaticRetries().build();

    private static HttpClientFactory nativeFactory = new HttpClientFactory();


    public static CloseableHttpClient httpClient() {
        return nativeFactory.httpClient;
    }
}
