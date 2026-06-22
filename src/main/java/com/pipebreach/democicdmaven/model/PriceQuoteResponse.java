package com.pipebreach.democicdmaven.model;

import java.math.BigDecimal;

public record PriceQuoteResponse(
    BigDecimal originalAmount,
    BigDecimal discountPercentage,
    BigDecimal discountAmount,
    BigDecimal finalAmount,
    String currency) {}
