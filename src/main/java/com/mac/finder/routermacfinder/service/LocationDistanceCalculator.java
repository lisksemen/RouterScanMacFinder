package com.mac.finder.routermacfinder.service;

import com.mac.finder.routermacfinder.dto.Location;
import jakarta.annotation.Nonnull;

public interface LocationDistanceCalculator {

  double distance(@Nonnull Location location1, @Nonnull Location location2);

}
