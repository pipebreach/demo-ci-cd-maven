package com.pipebreach.democicdmaven.service;

import com.pipebreach.democicdmaven.model.PartnerResponse;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class PartnerService {

  private static final List<PartnerResponse> PARTNERS =
      List.of(
          new PartnerResponse("p1", "Acme Corp", "https://acme.example.com", true),
          new PartnerResponse("p2", "Globex", "https://globex.example.com", true),
          new PartnerResponse("p3", "Initech", "https://initech.example.com", false));

  public List<PartnerResponse> findAll() {
    return PARTNERS;
  }

  public Optional<PartnerResponse> findById(String id) {
    return PARTNERS.stream().filter(p -> p.id().equals(id)).findFirst();
  }

  public boolean isRegisteredUrl(String url) {
    return PARTNERS.stream()
        .filter(PartnerResponse::active)
        .anyMatch(p -> url.startsWith(p.baseUrl()));
  }
}
