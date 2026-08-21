package com.pipebreach.democicdmaven.controller;

import com.pipebreach.democicdmaven.model.HealthResponse;
import com.pipebreach.democicdmaven.model.PriceQuoteRequest;
import com.pipebreach.democicdmaven.model.PriceQuoteResponse;
import com.pipebreach.democicdmaven.model.ServiceInfoResponse;
import com.pipebreach.democicdmaven.service.PriceQuoteService;
import com.pipebreach.democicdmaven.service.ReportService;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PriceQuoteController {

  private final PriceQuoteService priceQuoteService;
  private final ReportService reportService;

  public PriceQuoteController(PriceQuoteService priceQuoteService, ReportService reportService) {
    this.priceQuoteService = priceQuoteService;
    this.reportService = reportService;
  }

  @GetMapping("/")
  public ServiceInfoResponse root() {
    return new ServiceInfoResponse(
        "demo-ci-cd-maven",
        "ok",
        "0.0.1-SNAPSHOT",
        List.of(
            "GET /",
            "GET /health",
            "POST /api/v1/quotes",
            "GET /api/v1/reports",
            "GET /api/v1/reports/export"));
  }

  @GetMapping("/health")
  public HealthResponse health() {
    return new HealthResponse("ok");
  }

  @PostMapping("/api/v1/quotes")
  @ResponseStatus(HttpStatus.OK)
  public PriceQuoteResponse createQuote(@Valid @RequestBody PriceQuoteRequest request) {
    PriceQuoteResponse response = priceQuoteService.calculate(request);
    reportService.record(response);
    return response;
  }
}
