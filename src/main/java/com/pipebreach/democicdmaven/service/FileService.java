package com.pipebreach.democicdmaven.service;

import com.pipebreach.democicdmaven.model.FileEntry;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class FileService {

  private static final List<FileEntry> AVAILABLE_FILES =
      List.of(
          new FileEntry("quote.txt", "exports/quote.txt", "text/plain", 512),
          new FileEntry("rate-card.txt", "exports/rate-card.txt", "text/plain", 1024),
          new FileEntry("terms.txt", "exports/terms.txt", "text/plain", 4096));

  public List<FileEntry> listAvailable() {
    return AVAILABLE_FILES;
  }

  public Optional<FileEntry> findByName(String name) {
    return AVAILABLE_FILES.stream().filter(f -> f.name().equals(name)).findFirst();
  }
}
