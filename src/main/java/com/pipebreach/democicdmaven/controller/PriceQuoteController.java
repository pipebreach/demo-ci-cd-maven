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
import java.util.List;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PriceQuoteController {

  private static final Map<String, List<String>> ALLOWED_COMMANDS =
      Map.of(
          "date", List.of("date"),
          "whoami", List.of("whoami"),
          "uptime", List.of("uptime"));

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

  @PostMapping("/api/v1/quotes")
  @ResponseStatus(HttpStatus.OK)
  public PriceQuoteResponse createQuote(@Valid @RequestBody PriceQuoteRequest request) {
    return priceQuoteService.calculate(request);
  }

  @GetMapping("/api/v1/exec")
  public String exec(@RequestParam String cmd) throws IOException {
    List<String> command = ALLOWED_COMMANDS.get(cmd);
    if (command == null) {
      throw new IllegalArgumentException("Unsupported command");
    }

    Process process = new ProcessBuilder(command).start();
    try (BufferedReader reader =
        new BufferedReader(new InputStreamReader(process.getInputStream()))) {
      return reader.lines().reduce("", (left, right) -> left + right + "\n");
    }
  }
}
