package com.pipebreach.democicdmaven.controller;

import com.pipebreach.democicdmaven.model.SearchResult;
import com.pipebreach.democicdmaven.service.CurrencySearchService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/currencies")
public class CurrencySearchController {

  private final CurrencySearchService currencySearchService;

  public CurrencySearchController(CurrencySearchService currencySearchService) {
    this.currencySearchService = currencySearchService;
  }

  @GetMapping("/search")
  public SearchResult search(@RequestParam(defaultValue = "") String q) {
    return currencySearchService.search(q);
  }
}
