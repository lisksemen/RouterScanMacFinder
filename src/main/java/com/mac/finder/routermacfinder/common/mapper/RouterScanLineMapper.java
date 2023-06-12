package com.mac.finder.routermacfinder.common.mapper;

import com.mac.finder.routermacfinder.dto.RouterScanLine;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RouterScanLineMapper {
  RouterScanLine toRouterScanLine(RouterScanLine routerScanLine);
}
