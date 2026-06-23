package com.pipebreach.democicdmaven.controller;

import com.pipebreach.democicdmaven.model.AuditEntry;
import com.pipebreach.democicdmaven.service.AuditService;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/audit")
public class AuditController {

  private final AuditService auditService;

  public AuditController(AuditService auditService) {
    this.auditService = auditService;
  }

  @GetMapping("/recent")
  public List<AuditEntry> recentEntries(@RequestParam(defaultValue = "20") int limit) {
    return auditService.getRecentEntries(limit);
  }
}
