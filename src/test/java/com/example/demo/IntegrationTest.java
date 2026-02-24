package com.example.demo;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class IntegrationTest {

    @LocalServerPort
    private int port;

    private String baseUrl() {
        return "http://localhost:" + port;
    }

    @Test
    public void testCreateAccountAndTransaction() throws Exception {
        HttpClient client = HttpClient.newHttpClient();

        // Use snake_case JSON to match spring.jackson.property-naming-strategy=SNAKE_CASE
        String accountJson = "{ \"document_number\": \"12345678900\" }";
        HttpRequest req = HttpRequest.newBuilder()
                .uri(URI.create(baseUrl() + "/accounts"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(accountJson))
                .build();

        HttpResponse<String> resp = client.send(req, HttpResponse.BodyHandlers.ofString());
        assertThat(resp.statusCode()).isEqualTo(200);
        assertThat(resp.body()).contains("account_id");

        String txJson = "{ \"account_id\": 1, \"operation_type_id\": 4, \"amount\": 60.0 }";
        HttpRequest txReq = HttpRequest.newBuilder()
                .uri(URI.create(baseUrl() + "/transactions"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(txJson))
                .build();

        HttpResponse<String> txResp = client.send(txReq, HttpResponse.BodyHandlers.ofString());
        assertThat(txResp.statusCode()).isEqualTo(200);
        assertThat(txResp.body()).contains("amount");
    }
}
