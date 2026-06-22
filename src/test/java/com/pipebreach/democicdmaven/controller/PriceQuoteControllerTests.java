package com.pipebreach.democicdmaven.controller;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.server.LocalServerPort;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class PriceQuoteControllerTests {

  private final HttpClient httpClient = HttpClient.newHttpClient();

  @LocalServerPort private int port;

  @Test
  void rootReturnsServiceMetadata() throws Exception {
    HttpResponse<String> response = sendGet("/");

    assertThat(response.statusCode()).isEqualTo(200);
    assertThat(response.body()).contains("\"service\":\"demo-ci-cd-maven\"");
    assertThat(response.body()).contains("\"status\":\"ok\"");
    assertThat(response.body()).contains("POST /api/v1/quotes");
  }

  @Test
  void healthReturnsOk() throws Exception {
    HttpResponse<String> response = sendGet("/health");

    assertThat(response.statusCode()).isEqualTo(200);
    assertThat(response.body()).contains("\"status\":\"ok\"");
  }

  @Test
  void quoteEndpointCalculatesDiscount() throws Exception {
    HttpResponse<String> response =
        sendPost(
            "/api/v1/quotes",
            """
            {
              "amount": 125.50,
              "discountPercentage": 10.00,
              "currency": "USD"
            }
            """);
    assertThat(response.statusCode()).isEqualTo(200);
    assertThat(response.body()).contains("\"originalAmount\":125.50");
    assertThat(response.body()).contains("\"discountAmount\":12.55");
    assertThat(response.body()).contains("\"finalAmount\":112.95");
    assertThat(response.body()).contains("\"currency\":\"USD\"");
  }

  @Test
  void quoteEndpointRejectsInvalidPayload() throws Exception {
    HttpResponse<String> response =
        sendPost(
            "/api/v1/quotes",
            """
            {
              "amount": 0,
              "discountPercentage": 120,
              "currency": "usd"
            }
            """);

    assertThat(response.statusCode()).isEqualTo(400);
  }

  private HttpResponse<String> sendGet(String path) throws IOException, InterruptedException {
    HttpRequest request =
        HttpRequest.newBuilder().uri(URI.create("http://127.0.0.1:" + port + path)).GET().build();
    return httpClient.send(request, HttpResponse.BodyHandlers.ofString());
  }

  private HttpResponse<String> sendPost(String path, String body)
      throws IOException, InterruptedException {
    HttpRequest request =
        HttpRequest.newBuilder()
            .uri(URI.create("http://127.0.0.1:" + port + path))
            .header("Content-Type", "application/json")
            .POST(HttpRequest.BodyPublishers.ofString(body))
            .build();
    return httpClient.send(request, HttpResponse.BodyHandlers.ofString());
  }
}
