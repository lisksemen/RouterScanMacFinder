package com.mac.finder.routermacfinder;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class JobRunner implements CommandLineRunner {

    private final JobLauncher jobLauncher;

    @Qualifier("locateRoutersJob")
    private final Job locateRoutersJob;

    @Qualifier("filterRoutersByDistanceJob")
    private final Job filterRoutersByDistanceJob;

//    @Override
//    public void run(String... args) throws Exception {
//        jobLauncher.run(filterRoutersByDistanceJob, new JobParameters());
//    }

  @Override
  public void run(String... args) throws JobInstanceAlreadyCompleteException, JobExecutionAlreadyRunningException, JobParametersInvalidException, JobRestartException {
    jobLauncher.run(locateRoutersJob, new JobParameters());
  }
}
