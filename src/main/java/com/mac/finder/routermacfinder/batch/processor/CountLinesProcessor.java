package com.mac.finder.routermacfinder.batch.processor;

import com.mac.finder.routermacfinder.dto.RouterScanLine;
import jakarta.annotation.Nonnull;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
@Qualifier("countLinesProcessor")
public class CountLinesProcessor implements ItemProcessor<RouterScanLine, RouterScanLine> {

  @Override
  public RouterScanLine process(@Nonnull final RouterScanLine item) throws Exception {
    return item;
  }
}
