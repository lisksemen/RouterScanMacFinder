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
public class FilterRoutersByDistanceJobConfig {

  private final JobRepository jobRepository;

  @Bean("filterRoutersByDistanceJob")
  public Job filterRoutersByDistanceJob(
          @Qualifier("filterRoutersByDistanceStep") @Nonnull final Step filterRoutersByDistanceStep,
          @Qualifier("countLinesStep") @Nonnull final Step countLinesStep) {
    return new JobBuilder("filterRoutersByDistanceStep", jobRepository)
            .incrementer(new RunIdIncrementer())
            .start(countLinesStep)
            .next(filterRoutersByDistanceStep)
            .build();

  }
}
