package com.pipebreach.democicdmaven.controller;

import com.pipebreach.democicdmaven.model.QuoteStats;
import com.pipebreach.democicdmaven.service.QuoteStatsService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/quotes")
public class QuoteStatsController {

  private final QuoteStatsService quoteStatsService;

  public QuoteStatsController(QuoteStatsService quoteStatsService) {
    this.quoteStatsService = quoteStatsService;
  }

  @GetMapping("/stats")
  public QuoteStats stats() {
    return quoteStatsService.snapshot();
  }
}
