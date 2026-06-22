package com.pipebreach.democicdmaven.model;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import java.math.BigDecimal;

public record PriceQuoteRequest(
    @DecimalMin(value = "0.01") @Digits(integer = 10, fraction = 2) BigDecimal amount,
    @DecimalMin(value = "0.00") @DecimalMax(value = "100.00") @Digits(integer = 3, fraction = 2)
        BigDecimal discountPercentage,
    @NotBlank @Pattern(regexp = "^[A-Z]{3}$") String currency) {}
