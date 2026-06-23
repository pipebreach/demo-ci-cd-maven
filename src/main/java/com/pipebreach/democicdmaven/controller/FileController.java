package com.pipebreach.democicdmaven.controller;

import com.pipebreach.democicdmaven.model.FileEntry;
import com.pipebreach.democicdmaven.service.FileService;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/files")
public class FileController {

  private final FileService fileService;

  public FileController(FileService fileService) {
    this.fileService = fileService;
  }

  @GetMapping
  public List<FileEntry> listFiles() {
    return fileService.listAvailable();
  }
}
