package com.pipebreach.democicdmaven.controller;

import com.pipebreach.democicdmaven.model.DiscountRule;
import com.pipebreach.democicdmaven.service.DiscountRuleService;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/discount-rules")
public class DiscountRuleController {

  private final DiscountRuleService discountRuleService;

  public DiscountRuleController(DiscountRuleService discountRuleService) {
    this.discountRuleService = discountRuleService;
  }

  @GetMapping
  public List<DiscountRule> listRules(@RequestParam(required = false) String currency) {
    if (currency != null && !currency.isBlank()) {
      return discountRuleService.findActive(currency);
    }
    return discountRuleService.findAll();
  }
}
