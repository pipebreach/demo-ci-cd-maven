package com.pipebreach.democicdmaven.service;

import com.pipebreach.democicdmaven.model.DiscountRule;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;
import org.springframework.stereotype.Service;

@Service
public class DiscountRuleService {

  private static final List<DiscountRule> RULES =
      List.of(
          new DiscountRule("r1", "Standard USD", "USD", new BigDecimal("100.00"), new BigDecimal("15.00"), true),
          new DiscountRule("r2", "Premium USD", "USD", new BigDecimal("500.00"), new BigDecimal("25.00"), true),
          new DiscountRule("r3", "Standard EUR", "EUR", new BigDecimal("100.00"), new BigDecimal("15.00"), true),
          new DiscountRule("r4", "Legacy GBP", "GBP", new BigDecimal("50.00"), new BigDecimal("10.00"), false));

  @SuppressWarnings("unchecked")
  public List<DiscountRule> findActive(String currency) {
    Predicate activeByCurrency =
        obj -> {
          DiscountRule rule = (DiscountRule) obj;
          return rule.active()
              && (currency == null || currency.isBlank() || rule.currency().equals(currency));
        };
    Collection<DiscountRule> filtered =
        CollectionUtils.select(RULES, activeByCurrency);
    return List.copyOf(filtered);
  }

  public List<DiscountRule> findAll() {
    return RULES;
  }
}
