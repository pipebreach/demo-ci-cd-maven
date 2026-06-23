package com.pipebreach.democicdmaven.service;

import com.pipebreach.democicdmaven.model.CommandResult;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class CommandHistoryService {

  private static final int MAX_ENTRIES = 50;

  private final List<CommandResult> history = Collections.synchronizedList(new ArrayList<>());

  public void record(String command, String output, int exitCode) {
    if (history.size() >= MAX_ENTRIES) {
      history.remove(0);
    }
    history.add(new CommandResult(command, output, exitCode, Instant.now()));
  }

  public List<CommandResult> getHistory() {
    return List.copyOf(history);
  }
}
