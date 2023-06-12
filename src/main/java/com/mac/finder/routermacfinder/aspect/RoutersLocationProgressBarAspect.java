package com.mac.finder.routermacfinder.aspect;

import static java.lang.String.format;

import com.mac.finder.routermacfinder.common.LinesCounter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
@RequiredArgsConstructor
public class RoutersLocationProgressBarAspect {

  private final LinesCounter linesCounter;

  @Before("execution(* com.mac.finder.routermacfinder.batch.processor.CountLinesProcessor.process(..))")
  public void countLines() {
    linesCounter.incrementTotal();

  }

  @Before("execution(* com.mac.finder.routermacfinder.batch.processor.LocateRoutersProcessor.process(..)) || " +
          "execution(* com.mac.finder.routermacfinder.batch.processor.DistanceFilterProcessor.process(..))")
  public void logProgress() {
    linesCounter.incrementCurrent();

    if (linesCounter.getCurrentLines() % 10 == 0) {
      log.info(format("Lines processed: %s, all lines count: %d", getLinesProcessedPercentage(), linesCounter.getTotalLines()));
    }
  }



  private String getLinesProcessedPercentage() {
    return Math.round(linesCounter.getCurrentLines() / (double) linesCounter.getTotalLines() * 10000) / 100 + "%";
  }
}
