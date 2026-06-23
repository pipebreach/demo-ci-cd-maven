package com.pipebreach.democicdmaven.service;

import com.pipebreach.democicdmaven.model.CurrencyInfo;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class CurrencyService {

  private static final List<CurrencyInfo> CURRENCIES =
      List.of(
          new CurrencyInfo("USD", "US Dollar", "$"),
          new CurrencyInfo("EUR", "Euro", "€"),
          new CurrencyInfo("GBP", "British Pound", "£"),
          new CurrencyInfo("JPY", "Japanese Yen", "¥"),
          new CurrencyInfo("AUD", "Australian Dollar", "A$"),
          new CurrencyInfo("CAD", "Canadian Dollar", "CA$"),
          new CurrencyInfo("CHF", "Swiss Franc", "Fr"),
          new CurrencyInfo("CNY", "Chinese Yuan", "¥"));

  public List<CurrencyInfo> findAll() {
    return CURRENCIES;
  }

  public Optional<CurrencyInfo> findByCode(String code) {
    return CURRENCIES.stream().filter(c -> c.code().equalsIgnoreCase(code)).findFirst();
  }
}
