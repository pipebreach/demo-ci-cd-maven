package com.pipebreach.democicdmaven.model;

import java.time.Instant;

public record CommandResult(String command, String output, int exitCode, Instant executedAt) {}
