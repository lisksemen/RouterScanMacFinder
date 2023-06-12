package com.mac.finder.routermacfinder.service.impl;

import com.mac.finder.routermacfinder.dto.Location;
import com.mac.finder.routermacfinder.service.LocationDistanceCalculator;
import jakarta.annotation.Nonnull;
import org.springframework.stereotype.Service;

@Service
public class LocationDistanceCalculatorImpl implements LocationDistanceCalculator {
  @Override
  public double distance(@Nonnull final Location location1, @Nonnull final Location location2) {
    return distance(Double.parseDouble(location1.getLatitude()), Double.parseDouble(location2.getLatitude()),
            Double.parseDouble(location1.getLongitude()), Double.parseDouble(location2.getLongitude()),
            0, 0);
  }

  public double distance(final double lat1, final double lat2, final double lon1,
                         final double lon2, final double el1, final double el2) {

    final int R = 6371; // Radius of the earth+

    double latDistance = Math.toRadians(lat2 - lat1);
    double lonDistance = Math.toRadians(lon2 - lon1);
    double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
            + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
            * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
    double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
    double distance = R * c * 1000; // convert to meters

    double height = el1 - el2;

    distance = Math.pow(distance, 2) + Math.pow(height, 2);

    return Math.sqrt(distance);
  }
}
