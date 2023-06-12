package com.mac.finder.routermacfinder.common;

import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class LinesCounter {
  private long totalLines;
  private long currentLines;

  public void incrementTotal() {
    totalLines++;
  }

  public void incrementCurrent() {
    currentLines++;
  }
}
