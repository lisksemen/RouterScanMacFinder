package com.mac.finder.routermacfinder.service.impl;

import com.mac.finder.routermacfinder.dto.Location;
import com.mac.finder.routermacfinder.service.MacLocator;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class MacLocatorImpl implements MacLocator {
  @Override
  @Nullable
  public Location locateByMac(@Nonnull final String macAddress) {
    final var process = startGeoMacProcess(macAddress);

    final var processResults = getProcessResults(process);

    final var filteredResults = filterNotFoundResults(processResults);

    return chooseFromResults(filteredResults);
  }

  @Nonnull
  private Map<String, Location> filterNotFoundResults(Map<String, Location> processResults) {
    return processResults
            .entrySet()
            .stream()
            .filter(entry -> !entry.getValue().getLatitude().isBlank())
            .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
  }

  @Nullable
  private Location chooseFromResults(@Nonnull final Map<String, Location> results) {
    if (results.containsKey("Google")) {
      return results.get("Google");
    }
    return results.values().stream().findFirst().orElse(null);
  }

  @Nonnull
  private Map<String, Location> getProcessResults(@Nonnull final Process process) {
    final var output = new BufferedReader(new InputStreamReader(process.getInputStream()));
    return output
            .lines()
            .skip(1)
            .collect(Collectors.toMap(this::getProviderName, this::getLocation));
  }

  @Nonnull
  private Location getLocation(@Nonnull final String line) {
    return getLocationFromString(line.split("\\|")[1]);
  }

  @Nonnull
  private Location getLocationFromString(@Nonnull final String locationString) {
    final var locationArray = locationString.replace(" ", "").split(",");

    if (locationArray.length <= 1)
      return Location.builder()
              .longitude("")
              .latitude("")
              .build();

    return Location.builder()
            .latitude(locationArray[0])
            .longitude(locationArray[1])
            .build();
  }

  @Nonnull
  private String getProviderName(@Nonnull final String line) {
    return line.split("\\|")[0];
  }

  @Nonnull
  private Process startGeoMacProcess(@Nonnull final String macAddress) {
    ProcessBuilder processBuilder = new ProcessBuilder("geomac_portable_64/geomac.exe", "-P", macAddress);
    try {
      return processBuilder.start();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }
}
