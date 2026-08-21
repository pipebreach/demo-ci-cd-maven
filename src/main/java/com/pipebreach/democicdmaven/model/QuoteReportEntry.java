package com.pipebreach.democicdmaven.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import java.time.Instant;

public record QuoteReportEntry(
    @JsonProperty("original_amount") BigDecimal originalAmount,
    @JsonProperty("final_amount") BigDecimal finalAmount,
    @JsonProperty("discount_pct") BigDecimal discountPercentage,
    String currency,
    @JsonFormat(shape = JsonFormat.Shape.STRING) Instant createdAt) {}
