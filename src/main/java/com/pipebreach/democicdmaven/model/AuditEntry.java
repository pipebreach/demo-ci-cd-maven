package com.pipebreach.democicdmaven.model;

import java.time.Instant;

public record AuditEntry(String action, String details, String status, Instant timestamp) {}
