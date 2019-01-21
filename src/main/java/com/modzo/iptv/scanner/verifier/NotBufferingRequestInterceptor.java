package com.modzo.iptv.scanner.verifier;

import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;

public class NotBufferingRequestInterceptor extends HttpComponentsClientHttpRequestFactory {
    public NotBufferingRequestInterceptor() {
        super(HttpClientBuilder.create().build());
        super.setBufferRequestBody(false);
    }
}
