package com.pipebreach.democicdmaven.model;

public record SystemInfoResponse(
    String hostname, String osName, String osVersion, String jvmVersion, long uptimeSeconds) {}
