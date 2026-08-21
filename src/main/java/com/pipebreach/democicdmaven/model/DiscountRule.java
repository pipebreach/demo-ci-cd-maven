package com.pipebreach.democicdmaven.model;

import java.math.BigDecimal;

public record DiscountRule(
    String id,
    String name,
    String currency,
    BigDecimal minAmount,
    BigDecimal maxDiscountPercentage,
    boolean active) {}
