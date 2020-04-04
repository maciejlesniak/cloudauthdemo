package pl.sparkidea.cloud.gw;

public class CloudService {

  private final String serviceUrl;

  private final String serviceName;

  public CloudService(String serviceUrl, String serviceName) {

    this.serviceUrl = serviceUrl;
    this.serviceName = serviceName;
  }

  public String getServiceUrl() {

    return serviceUrl;
  }

  public String getServiceName() {

    return serviceName;
  }
}
