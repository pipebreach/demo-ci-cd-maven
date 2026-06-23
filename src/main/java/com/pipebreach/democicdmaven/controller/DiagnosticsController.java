package com.pipebreach.democicdmaven.controller;

import com.pipebreach.democicdmaven.model.SystemInfoResponse;
import com.pipebreach.democicdmaven.service.DiagnosticsService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/diagnostics")
public class DiagnosticsController {

  private final DiagnosticsService diagnosticsService;

  public DiagnosticsController(DiagnosticsService diagnosticsService) {
    this.diagnosticsService = diagnosticsService;
  }

  @GetMapping
  public SystemInfoResponse systemInfo() {
    return diagnosticsService.collectSystemInfo();
  }
}
