package com.mac.finder.routermacfinder.batch.processor;

import com.mac.finder.routermacfinder.common.mapper.RouterScanLineMapper;
import com.mac.finder.routermacfinder.dto.Location;
import com.mac.finder.routermacfinder.dto.RouterScanLine;
import com.mac.finder.routermacfinder.service.LocationDistanceCalculator;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Qualifier("distanceFilterProcessor")
@RequiredArgsConstructor
public class DistanceFilterProcessor implements ItemProcessor<RouterScanLine, RouterScanLine> {

  private final LocationDistanceCalculator locationDistanceCalculator;

  private final RouterScanLineMapper routerScanLineMapper;

  @Value("${distance-check.max-distance}")
  private Double maxDistanceAllowed;

  @Value("${distance-check.home-latitude}")
  private Double homeLatitude;

  @Value("${distance-check.home-longitude}")
  private Double homeLongitude;



  @Override
  @Nullable
  public RouterScanLine process(@Nonnull final RouterScanLine item) throws Exception {
    final var result = routerScanLineMapper.toRouterScanLine(item);

    if (hasLocation(result) && inRange(result))
      return result;

    return null;
  }

  private boolean hasLocation(@Nonnull final RouterScanLine routerScanLine) {
    return !routerScanLine.getLongitude().isBlank() && !routerScanLine.getLatitude().isBlank();
  }

  private boolean inRange(RouterScanLine routerScanLine) {
    final var homeLocation = getLocation(String.valueOf(homeLatitude), String.valueOf(homeLongitude));

    final var routerLocation = getLocation(routerScanLine.getLatitude(), routerScanLine.getLongitude());


    final var distance = locationDistanceCalculator.distance(homeLocation, routerLocation);

    return !(distance > maxDistanceAllowed);
  }

  @Nonnull
  private Location getLocation(@Nonnull final String homeLatitude, @Nonnull final String homeLongitude) {
    return Location
            .builder()
            .latitude(homeLatitude)
            .longitude(homeLongitude)
            .build();
  }
}
