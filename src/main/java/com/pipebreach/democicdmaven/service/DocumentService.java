package com.pipebreach.democicdmaven.service;

import com.pipebreach.democicdmaven.model.DocumentEntry;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class DocumentService {

  private static final List<DocumentEntry> CATALOGUE =
      List.of(
          new DocumentEntry(
              "d1", "Quote Template", "Standard price quote template", "templates/quote.txt"),
          new DocumentEntry(
              "d2", "Terms of Service", "Customer terms and conditions", "docs/tos.txt"),
          new DocumentEntry(
              "d3", "Rate Card", "Currency rate card for current quarter", "docs/rate-card.txt"));

  public List<DocumentEntry> findAll() {
    return CATALOGUE;
  }

  public Optional<DocumentEntry> findById(String id) {
    return CATALOGUE.stream().filter(d -> d.id().equals(id)).findFirst();
  }
}
