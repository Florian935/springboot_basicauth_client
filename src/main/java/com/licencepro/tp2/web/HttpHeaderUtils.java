package com.licencepro.tp2.web;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.util.MultiValueMap;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON;

@Configuration
public class HttpHeaderUtils {

    public HttpHeaders getHeaders() {
        final HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setAccept(List.of(APPLICATION_JSON));

        return httpHeaders;
    }

    public HttpHeaders getHeaders(MultiValueMap<String, String> headers) {
        final HttpHeaders httpHeaders = getHeaders();
        httpHeaders.addAll(headers);

        return httpHeaders;
    }
}
