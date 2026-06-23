package com.pipebreach.democicdmaven.controller;

import com.pipebreach.democicdmaven.model.HealthResponse;
import com.pipebreach.democicdmaven.model.PriceQuoteRequest;
import com.pipebreach.democicdmaven.model.PriceQuoteResponse;
import com.pipebreach.democicdmaven.model.ServiceInfoResponse;
import com.pipebreach.democicdmaven.service.PriceQuoteService;
import jakarta.validation.Valid;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.springframework.http.HttpStatus;
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

  @PostMapping("/api/v1/quotes")
  @ResponseStatus(HttpStatus.OK)
  public PriceQuoteResponse createQuote(@Valid @RequestBody PriceQuoteRequest request) {
    return priceQuoteService.calculate(request);
  }

  @GetMapping("/api/v1/download")
  public String download(@RequestParam String path) throws IOException {
    Path baseDirectory = Path.of("downloads").toAbsolutePath().normalize();
    Path requestedPath = baseDirectory.resolve(path).normalize();

    if (Path.of(path).isAbsolute() || !requestedPath.startsWith(baseDirectory)) {
      throw new IllegalArgumentException("Invalid path");
    }

    return Files.readString(requestedPath);
  }
}
