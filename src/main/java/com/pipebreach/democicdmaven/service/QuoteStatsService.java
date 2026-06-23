package com.pipebreach.democicdmaven.service;

import com.pipebreach.democicdmaven.model.PriceQuoteResponse;
import com.pipebreach.democicdmaven.model.QuoteStats;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.stereotype.Service;

@Service
public class QuoteStatsService {

  private final AtomicLong count = new AtomicLong();
  private BigDecimal totalOriginal = BigDecimal.ZERO;
  private BigDecimal totalDiscount = BigDecimal.ZERO;
  private BigDecimal totalDiscountPct = BigDecimal.ZERO;

  public synchronized void record(PriceQuoteResponse quote) {
    count.incrementAndGet();
    totalOriginal = totalOriginal.add(quote.originalAmount());
    totalDiscount = totalDiscount.add(quote.discountAmount());
    totalDiscountPct = totalDiscountPct.add(quote.discountPercentage());
  }

  public synchronized QuoteStats snapshot() {
    long n = count.get();
    if (n == 0) {
      return new QuoteStats(0, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO);
    }
    BigDecimal divisor = BigDecimal.valueOf(n);
    return new QuoteStats(
        n,
        totalOriginal.setScale(2, RoundingMode.HALF_UP),
        totalDiscount.setScale(2, RoundingMode.HALF_UP),
        totalDiscountPct.divide(divisor, 2, RoundingMode.HALF_UP));
  }
}
