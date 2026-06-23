package com.pipebreach.democicdmaven.service;

import com.pipebreach.democicdmaven.model.SystemInfoResponse;
import java.lang.management.ManagementFactory;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.concurrent.TimeUnit;
import org.springframework.stereotype.Service;

@Service
public class DiagnosticsService {

  public SystemInfoResponse collectSystemInfo() {
    String hostname = resolveHostname();
    String osName = System.getProperty("os.name", "unknown");
    String osVersion = System.getProperty("os.version", "unknown");
    String jvmVersion = System.getProperty("java.version", "unknown");
    long uptimeSeconds =
        TimeUnit.MILLISECONDS.toSeconds(ManagementFactory.getRuntimeMXBean().getUptime());
    return new SystemInfoResponse(hostname, osName, osVersion, jvmVersion, uptimeSeconds);
  }

  private String resolveHostname() {
    try {
      return InetAddress.getLocalHost().getHostName();
    } catch (UnknownHostException e) {
      return "unknown";
    }
  }
}
