package com.pipebreach.democicdmaven.controller;

import com.pipebreach.democicdmaven.model.PartnerResponse;
import com.pipebreach.democicdmaven.service.PartnerService;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/v1/partners")
public class PartnerController {

  private final PartnerService partnerService;

  public PartnerController(PartnerService partnerService) {
    this.partnerService = partnerService;
  }

  @GetMapping
  public List<PartnerResponse> listPartners() {
    return partnerService.findAll();
  }

  @GetMapping("/{id}")
  public PartnerResponse getPartner(@PathVariable String id) {
    return partnerService
        .findById(id)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Partner not found"));
  }
}
