package com.mac.finder.routermacfinder.batch.job;

import jakarta.annotation.Nonnull;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class LocateRoutersJobConfig {

  private final JobRepository jobRepository;


  @Bean
  public Job locateRoutersJob(@Qualifier("countLinesStep") @Nonnull final Step countLinesStep,
                              @Qualifier("locateRoutersStep") @Nonnull final Step locateRoutersStep) {
    return new JobBuilder("locateRoutersJob", jobRepository)
            .incrementer(new RunIdIncrementer())
            .start(countLinesStep)
            .next(locateRoutersStep)
            .build();
  }

}
