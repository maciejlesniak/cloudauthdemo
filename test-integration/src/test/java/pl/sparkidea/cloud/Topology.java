package pl.sparkidea.cloud;

import pl.sparkidea.cloud.gw.CloudService;

public class Topology {

  public static final CloudService GATEWAY_URL = new CloudService("http://localhost:8080", "service-gw");

  public static final CloudService SERVICE_A_URL = new CloudService("http://localhost:8090", "service-a");

  public static final CloudService SERVICE_B_URL = new CloudService("http://localhost:8091", "service-b");

}
