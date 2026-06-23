package com.pipebreach.democicdmaven.service;

import com.pipebreach.democicdmaven.model.SearchResult;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class CurrencySearchService {

  private static final List<String> SUPPORTED_CURRENCIES =
      List.of("USD", "EUR", "GBP", "JPY", "AUD", "CAD", "CHF", "CNY", "SEK", "NZD");

  public SearchResult search(String term) {
    if (term == null || term.isBlank()) {
      return new SearchResult(term, List.of(), 0);
    }
    String upper = term.toUpperCase();
    List<String> matches =
        SUPPORTED_CURRENCIES.stream().filter(c -> c.contains(upper)).toList();
    return new SearchResult(term, matches, matches.size());
  }
}
