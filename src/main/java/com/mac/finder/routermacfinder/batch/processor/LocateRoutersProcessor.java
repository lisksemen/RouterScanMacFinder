package com.mac.finder.routermacfinder.batch.processor;

import com.mac.finder.routermacfinder.common.mapper.RouterScanLineMapper;
import com.mac.finder.routermacfinder.dto.RouterScanLine;
import com.mac.finder.routermacfinder.service.MacLocator;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
@Qualifier("locateRoutersProcessor")
@RequiredArgsConstructor
public class LocateRoutersProcessor implements ItemProcessor<RouterScanLine, RouterScanLine> {

  private final RouterScanLineMapper routerScanLineMapper;

  private final MacLocator macLocator;

  @Override
  @Nullable
  public RouterScanLine process(@Nonnull final RouterScanLine item) throws Exception {
    final var result = routerScanLineMapper.toRouterScanLine(item);

    if (locatable(result) && noLocationPresent(result)) {
      locateIfPossible(result);
    }
    return result;
  }

  private void locateIfPossible(@Nonnull final RouterScanLine routerScanLine) {
    final var location = macLocator.locateByMac(routerScanLine.getBssid());

    if (location != null) {
      routerScanLine.setLatitude(location.getLatitude());
      routerScanLine.setLongitude(location.getLongitude());
    }
  }

  private boolean noLocationPresent(@Nonnull final RouterScanLine routerScanLine) {
    return (routerScanLine.getLatitude().isBlank() || routerScanLine.getLongitude().isBlank());
  }

  private boolean locatable(@Nonnull final RouterScanLine routerScanLine) {
    return !routerScanLine.getBssid().isBlank();
  }
}
