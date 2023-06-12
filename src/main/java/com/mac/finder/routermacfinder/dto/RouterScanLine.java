package com.mac.finder.routermacfinder.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RouterScanLine {
  String ip;
  Integer port;
  Integer ping;
  String status;
  String authorization;
  String serverName;
  String radioOff;
  String hidden;
  String bssid;
  String essid;
  String wifiSecurity;
  String wifiKey;
  String wpsPIN;
  String lanIP;
  String lanSubnetMask;
  String wanIP;
  String wanSubnetMask;
  String wanGateway;
  String dns;
  String latitude;
  String longitude;
  String comments;
  String additional;
}
