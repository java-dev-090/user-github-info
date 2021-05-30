package com.tui.github.controller;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.tui.github.config.WireMockInitializer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.reactive.server.WebTestClient;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.client.WireMock.urlMatching;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ContextConfiguration(initializers = {WireMockInitializer.class})
public class UserGithubControllerIT {

    @Autowired
    private WireMockServer wireMockServer;

    @Autowired
    private WebTestClient webTestClient;

    @AfterEach
    void resetAll() {
        wireMockServer.resetAll();
    }

    @Test
    @DisplayName("Success 200")
    public void testGetUsersGithubReposSuccess(){
        this.getStubFor(".*/test/.*","response-200.json", HttpStatus.OK.value());

        this.webTestClient
                .get()
                .uri("/users/repos/v1/test")
                .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
                .exchange()
                .expectStatus().isOk()
                .expectBody().jsonPath("$.length()").isEqualTo(5)
                .jsonPath("$[0].ownerLogin").isEqualTo("test");
    }

    @Test
    @DisplayName("Failed 404")
    public void testGetUsersGithubReposFailed404(){
        this.getStubFor(".*/test87656743/.*","response-404.json", HttpStatus.NOT_FOUND.value());
        this.webTestClient
                .get()
                .uri("/users/repos/v1/test87656743")
                .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
                .exchange()
                .expectStatus().is4xxClientError()
                .expectBody()
                .jsonPath("$.status").isEqualTo(HttpStatus.NOT_FOUND.value());
    }

    private void getStubFor(String urlRegex, String responseJson, int httpStatus) {
        wireMockServer.stubFor(
                WireMock.get(urlMatching(urlRegex))
                        .willReturn(aResponse()
                                .withStatus(httpStatus)
                                .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                                .withBodyFile("api-response/"+responseJson))
        );
    }

    @Test
    @DisplayName("Failed 406")
    public void testGetUsersGithubReposFailed406(){
        this.webTestClient
                .get()
                .uri("/users/repos/v1/test")
                .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_XML_VALUE)
                .exchange()
                .expectStatus().is4xxClientError()
                .expectBody()
                .jsonPath("$.status").isEqualTo(HttpStatus.NOT_ACCEPTABLE.value());
    }

}
