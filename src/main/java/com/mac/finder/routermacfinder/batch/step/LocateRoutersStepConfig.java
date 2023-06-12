package com.mac.finder.routermacfinder.batch.step;

import com.mac.finder.routermacfinder.dto.RouterScanLine;
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
public class LocateRoutersStepConfig {
  @Qualifier("routerScanLineReader")
  private final ItemReader<RouterScanLine> routerScanLineReader;

  @Qualifier("routerScanLineWriter")
  private final ItemWriter<RouterScanLine> routerScanLineWriter;

  @Qualifier("locateRoutersProcessor")
  private final ItemProcessor<RouterScanLine, RouterScanLine> locateRoutersProcessor;

  private final JobRepository jobRepository;

  private final PlatformTransactionManager platformTransactionManager;

  @Bean("locateRoutersStep")
  public Step locateRoutersStep(@Value("${batch.chunk-size}") final int chunkSize) {
    return new StepBuilder("locateRoutersStep", jobRepository)
            .<RouterScanLine, RouterScanLine>chunk(chunkSize, platformTransactionManager)
            .reader(routerScanLineReader)
            .processor(locateRoutersProcessor)
            .writer(routerScanLineWriter)
            .build();
  }
}
