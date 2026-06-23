package com.pipebreach.democicdmaven.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.pipebreach.democicdmaven.model.PriceQuoteResponse;
import com.pipebreach.democicdmaven.model.QuoteReportEntry;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class ReportService {

  private final ObjectMapper mapper;
  private final List<QuoteReportEntry> history = Collections.synchronizedList(new ArrayList<>());

  public ReportService() {
    this.mapper =
        new ObjectMapper()
            .registerModule(new JavaTimeModule())
            .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
  }

  public void record(PriceQuoteResponse quote) {
    history.add(
        new QuoteReportEntry(
            quote.originalAmount(),
            quote.finalAmount(),
            quote.discountPercentage(),
            quote.currency(),
            Instant.now()));
  }

  public String exportJson() throws JsonProcessingException {
    return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(history);
  }

  public List<QuoteReportEntry> getHistory() {
    return List.copyOf(history);
  }
}
