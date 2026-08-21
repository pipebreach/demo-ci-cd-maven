package com.pipebreach.democicdmaven.model;

import java.math.BigDecimal;

public record QuoteStats(
    long quoteCount,
    BigDecimal totalOriginalAmount,
    BigDecimal totalDiscountAmount,
    BigDecimal avgDiscountPercentage) {}
