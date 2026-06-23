package com.pipebreach.democicdmaven.service;

import com.pipebreach.democicdmaven.model.AuditEntry;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class AuditService {

  private static final Logger logger = LoggerFactory.getLogger(AuditService.class);

  private final List<AuditEntry> entries = Collections.synchronizedList(new ArrayList<>());

  public void record(String action, String details, String status) {
    AuditEntry entry = new AuditEntry(action, details, status, Instant.now());
    entries.add(entry);
    logger.info("AUDIT action={} status={} details={}", action, status, details);
  }

  public List<AuditEntry> getRecentEntries(int limit) {
    int size = entries.size();
    return List.copyOf(entries.subList(Math.max(0, size - limit), size));
  }
}
