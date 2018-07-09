package org.quickstart.http.feign.client;

import feign.RequestInterceptor;
import feign.RequestTemplate;

public class HeadersInterceptor implements RequestInterceptor {

    @Override
    public void apply(RequestTemplate template) {
        template.header("Accept", "application/json");
        template.header("Content-Type", "application/json");
    }
}
