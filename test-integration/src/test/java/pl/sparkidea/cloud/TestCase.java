package pl.sparkidea.cloud;

import org.hamcrest.Matcher;
import pl.sparkidea.cloud.gw.CloudService;

public class TestCase<R> {

  private final CloudService cloudService;

  private final String path;

  private final Matcher<R> matcher;

  public TestCase(
      CloudService cloudService,
      String path,
      Matcher<R> matcher) {

    this.cloudService = cloudService;
    this.path = path;
    this.matcher = matcher;
  }

  public CloudService getCloudService() {

    return cloudService;
  }

  public String getPath() {

    return path;
  }

  public Matcher<R> getMatcher() {

    return matcher;
  }

  @Override
  public String toString() {

    return "TestCase{" +
        "cloudService=" + cloudService +
        ", path='" + path + '\'' +
        ", bodyMatcher=" + matcher +
        '}';
  }
}
