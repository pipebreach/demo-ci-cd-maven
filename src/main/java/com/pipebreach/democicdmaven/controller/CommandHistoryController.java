package com.pipebreach.democicdmaven.controller;

import com.pipebreach.democicdmaven.model.CommandResult;
import com.pipebreach.democicdmaven.service.CommandHistoryService;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/exec/history")
public class CommandHistoryController {

  private final CommandHistoryService commandHistoryService;

  public CommandHistoryController(CommandHistoryService commandHistoryService) {
    this.commandHistoryService = commandHistoryService;
  }

  @GetMapping
  public List<CommandResult> history() {
    return commandHistoryService.getHistory();
  }
}
