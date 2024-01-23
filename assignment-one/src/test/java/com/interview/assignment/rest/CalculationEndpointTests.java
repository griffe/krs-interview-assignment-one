package com.interview.assignment.rest;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;

import static org.springframework.http.MediaType.APPLICATION_JSON;

@AutoConfigureWebTestClient
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("prod")
public class CalculationEndpointTests {

    @Autowired
    WebTestClient webTestClient;

    @Test
    public void calculationEndpointRespondsWithBadRequestOnError() {
        webTestClient
                .get().uri("/calculate/4b5760ba-6bd6-479d-8264-69a90a2b1a21/100/")
                .accept(APPLICATION_JSON)
                .header("X-API-KEY", "testToken")
                .exchange()
                .expectStatus().isBadRequest()
                .expectHeader().contentType(APPLICATION_JSON);
    }

    @Test
    public void calculationEndpointRespondsCorrectlyOnSuccess() {
        webTestClient
                .get().uri("/calculate/4b5760ba-6bd6-479d-8264-69a90a2b1a21/100/?discountType=PERCENTAGE&discountPercentage=5")
                .accept(APPLICATION_JSON)
                .header("X-API-KEY", "testToken")
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(APPLICATION_JSON)
                .expectBody()
                .jsonPath("$.price").isEqualTo("52772.5");
    }
}
