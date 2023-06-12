package com.mac.finder.routermacfinder.batch.step;

import com.mac.finder.routermacfinder.dto.RouterScanLine;
import jakarta.annotation.Nonnull;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@RequiredArgsConstructor
public class FilterRoutersByDistanceStepConfig {

  @Qualifier("routerScanLineReader")
  private final ItemReader<RouterScanLine> routerScanLineReader;

  @Qualifier("routerScanLineWriter")
  private final ItemWriter<RouterScanLine> routerScanLineWriter;

  private final JobRepository jobRepository;

  private final PlatformTransactionManager platformTransactionManager;

  @Bean("filterRoutersByDistanceStep")
  public Step filterRoutersByDistanceStep(@Value("${batch.chunk-size}") final int chunkSize,
                                          @Qualifier("distanceFilterProcessor") @Nonnull final ItemProcessor<RouterScanLine, RouterScanLine> distanceFilterProcessor) {
    return new StepBuilder("filterRoutersByDistanceStep", jobRepository)
            .<RouterScanLine, RouterScanLine>chunk(chunkSize, platformTransactionManager)
            .reader(routerScanLineReader)
            .processor(distanceFilterProcessor)
            .writer(routerScanLineWriter)
            .build();
  }
}
