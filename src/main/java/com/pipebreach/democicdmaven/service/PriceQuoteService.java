package com.pipebreach.democicdmaven.service;

import com.pipebreach.democicdmaven.model.PriceQuoteRequest;
import com.pipebreach.democicdmaven.model.PriceQuoteResponse;
import java.math.BigDecimal;
import java.math.RoundingMode;
import org.springframework.stereotype.Service;

@Service
public class PriceQuoteService {

  public PriceQuoteResponse calculate(PriceQuoteRequest request) {
    BigDecimal amount = request.amount().setScale(2, RoundingMode.HALF_UP);
    BigDecimal discountPercentage = request.discountPercentage().setScale(2, RoundingMode.HALF_UP);

    BigDecimal discountAmount =
        amount
            .multiply(discountPercentage)
            .divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP);
    BigDecimal finalAmount = amount.subtract(discountAmount).setScale(2, RoundingMode.HALF_UP);

    return new PriceQuoteResponse(
        amount, discountPercentage, discountAmount, finalAmount, request.currency());
  }
}
