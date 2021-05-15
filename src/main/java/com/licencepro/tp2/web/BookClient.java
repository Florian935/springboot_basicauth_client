package com.licencepro.tp2.web;

import com.licencepro.tp2.domain.Book;
import org.apache.tomcat.util.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.POST;

@Configuration
public class BookClient {

    private static final Logger log = LoggerFactory.getLogger(BookClient.class);
    private final HttpHeaderUtils clientHeader;
    private final RestTemplate restTemplate;
    private final String AUTHORIZATION_HEADERS = "Authorization";
    private final String BASIC_PREFIX = "Basic ";
    private final String SERVER_URL = "http://localhost:8080/api/v1.0/books";

    public BookClient(HttpHeaderUtils clientHeader, RestTemplate restTemplate) {
        this.clientHeader = clientHeader;
        this.restTemplate = restTemplate;
    }

    public void getAll() {
        final String encodedCredentials = encodeInBase64("user:password");

        final MultiValueMap<String, String> authorizationHeaderInMap =
                getAuthorizationHeaderInMap(encodedCredentials);

        HttpEntity<Book> request = new HttpEntity<>(
                clientHeader.getHeaders(authorizationHeaderInMap));

        ResponseEntity<List> responseEntity = restTemplate.exchange(
                SERVER_URL,
                GET,
                request,
                List.class);

        log.info(Objects.requireNonNull(responseEntity.getBody()).toString());
    }

    private String encodeInBase64(String userCredentials) {
        return new String(Base64.encodeBase64(userCredentials.getBytes(StandardCharsets.UTF_8)));
    }

    public void save() {
        final Book bookToAdd = new Book("Mon livre depuis le client");
        final String encodedCredentials = encodeInBase64("admin:password");

        final MultiValueMap<String, String> authorizationHeaderInMap =
                getAuthorizationHeaderInMap(encodedCredentials);

        HttpEntity<Book> request =
                new HttpEntity<>(bookToAdd, clientHeader.getHeaders(authorizationHeaderInMap));

        ResponseEntity<Book> responseEntity = restTemplate.exchange(
                SERVER_URL,
                POST,
                request,
                Book.class
        );

        log.info(String.format(
                "Status code: %s",
                Objects.requireNonNull(responseEntity.getStatusCode().toString())
                )
        );
    }

    private MultiValueMap<String, String> getAuthorizationHeaderInMap(String encodedCredentials) {
        final MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        headers.add(
                AUTHORIZATION_HEADERS, String.format("%s%s", BASIC_PREFIX, encodedCredentials));

        return headers;
    }
}
