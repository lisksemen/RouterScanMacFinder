package com.mac.finder.routermacfinder.service;

import com.mac.finder.routermacfinder.dto.Location;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;

public interface MacLocator {
  @Nullable
  Location locateByMac(@Nonnull String macAddress);
}
