package com.pipebreach.democicdmaven.model;

import java.util.List;

public record ServiceInfoResponse(
    String service, String status, String version, List<String> endpoints) {

  public ServiceInfoResponse {
    endpoints = List.copyOf(endpoints);
  }
}
