package com.mac.finder.routermacfinder.batch;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RouterScanBatchCommonConfig {


  @Bean("routerScanLineNames")
  public String[] routerScanLineNames() {
    return new String[]{"ip", "port", "ping", "status", "authorization", "serverName",
            "radioOff", "hidden", "bssid", "essid", "wifiSecurity", "wifiKey", "wpsPIN",
            "lanIP", "lanSubnetMask", "wanIP", "wanSubnetMask", "wanGateway", "dns",
            "latitude", "longitude", "comments", "additional"};
  }

}
