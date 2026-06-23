package com.pipebreach.democicdmaven.controller;

import com.pipebreach.democicdmaven.model.DocumentEntry;
import com.pipebreach.democicdmaven.service.DocumentService;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/v1/documents")
public class DocumentController {

  private final DocumentService documentService;

  public DocumentController(DocumentService documentService) {
    this.documentService = documentService;
  }

  @GetMapping
  public List<DocumentEntry> listDocuments() {
    return documentService.findAll();
  }

  @GetMapping("/{id}")
  public DocumentEntry getDocument(@PathVariable String id) {
    return documentService
        .findById(id)
        .orElseThrow(
            () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Document not found"));
  }
}
