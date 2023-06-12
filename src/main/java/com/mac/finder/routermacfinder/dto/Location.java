package com.mac.finder.routermacfinder.dto;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class Location {
  String latitude;
  String longitude;
}
