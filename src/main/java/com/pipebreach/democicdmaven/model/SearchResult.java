package com.pipebreach.democicdmaven.model;

import java.util.List;

public record SearchResult(String term, List<String> matches, int totalCount) {}
