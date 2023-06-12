package com.mac.finder.routermacfinder.batch.reader;

import com.mac.finder.routermacfinder.dto.RouterScanLine;
import jakarta.annotation.Nonnull;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

@Configuration
public class RouterScanLineReaderConfig {

  @Value("${input.file-path}")
  private Resource input;

  @Bean("routerScanLineReader")
  public FlatFileItemReader<RouterScanLine> routerScanLineReader(
          @Qualifier("routerScanLineNames") @Nonnull final String[] routerScanLineNames,
          @Qualifier("routerScanFieldSetMapper") @Nonnull final FieldSetMapper<RouterScanLine> routerScanFieldSetMapper,
          @Qualifier("inputFileDelimiter") @Nonnull final String delimiter) {

    return new FlatFileItemReaderBuilder<RouterScanLine>()
            .name("routerScanLineReader")
            .resource(input)
            .delimited()
            .delimiter(delimiter)
            .names(routerScanLineNames)
            .fieldSetMapper(routerScanFieldSetMapper)
            .build();
  }

  @Bean("routerScanFieldSetMapper")
  public FieldSetMapper<RouterScanLine> routerScanFieldSetMapper() {
    final var routerScanFieldSetMapper = new BeanWrapperFieldSetMapper<RouterScanLine>();

    routerScanFieldSetMapper.setTargetType(RouterScanLine.class);

    return routerScanFieldSetMapper;
  }

  @Bean("inputFileDelimiter")
  public String inputFileDelimiter() {
    return "\t";
  }

}
