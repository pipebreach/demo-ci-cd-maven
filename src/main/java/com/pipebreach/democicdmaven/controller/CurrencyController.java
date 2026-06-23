package com.pipebreach.democicdmaven.controller;

import com.pipebreach.democicdmaven.model.CurrencyInfo;
import com.pipebreach.democicdmaven.service.CurrencyService;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/v1/currencies")
public class CurrencyController {

  private final CurrencyService currencyService;

  public CurrencyController(CurrencyService currencyService) {
    this.currencyService = currencyService;
  }

  @GetMapping
  public List<CurrencyInfo> listCurrencies() {
    return currencyService.findAll();
  }

  @GetMapping("/{code}")
  public CurrencyInfo getCurrency(@PathVariable String code) {
    return currencyService
        .findByCode(code)
        .orElseThrow(
            () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Currency not found"));
  }
}
