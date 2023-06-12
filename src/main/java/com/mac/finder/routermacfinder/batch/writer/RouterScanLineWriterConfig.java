package com.mac.finder.routermacfinder.batch.writer;

import com.mac.finder.routermacfinder.dto.RouterScanLine;
import jakarta.annotation.Nonnull;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.builder.FlatFileItemWriterBuilder;
import org.springframework.batch.item.file.transform.BeanWrapperFieldExtractor;
import org.springframework.batch.item.file.transform.DelimitedLineAggregator;
import org.springframework.batch.item.file.transform.FieldExtractor;
import org.springframework.batch.item.file.transform.LineAggregator;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;

@Configuration
public class RouterScanLineWriterConfig {

  @Value("${output.file-path}")
  private FileSystemResource outputFile;

  @Bean("routerScanLineWriter")
  public ItemWriter<RouterScanLine> routerScanLineWriter(
          @Qualifier("routerScanLineAggregator") @Nonnull final LineAggregator<RouterScanLine> routerScanLineAggregator) {
    return new FlatFileItemWriterBuilder<RouterScanLine>()
            .name("routerScanLineWriter")
            .resource(outputFile)
            .lineAggregator(routerScanLineAggregator)
            .build();
  }

  @Bean("routerScanLineAggregator")
  public LineAggregator<RouterScanLine> routerScanLineAggregator(
          @Qualifier("routerScanLineFieldExtractor") @Nonnull final FieldExtractor<RouterScanLine> fieldExtractor,
          @Qualifier("outputFileDelimiter") @Nonnull final String delimiter) {
    final var routerScanLineAggregator = new DelimitedLineAggregator<RouterScanLine>();

    routerScanLineAggregator.setDelimiter(delimiter);
    routerScanLineAggregator.setFieldExtractor(fieldExtractor);

    return routerScanLineAggregator;
  }

  @Bean("routerScanLineFieldExtractor")
  public FieldExtractor<RouterScanLine> routerScanLineFieldExtractor(
          @Qualifier("routerScanLineNames") @Nonnull final String[] routerScanLineNames) {
    final var routerScanLineFieldExtractor = new BeanWrapperFieldExtractor<RouterScanLine>();

    routerScanLineFieldExtractor.setNames(routerScanLineNames);

    return routerScanLineFieldExtractor;
  }

  @Bean("outputFileDelimiter")
  public String outputFileDelimiter() {
    return "\t";
  }
}
