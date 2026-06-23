package com.pipebreach.democicdmaven.model;

public record FileEntry(String name, String path, String contentType, long sizeBytes) {}
