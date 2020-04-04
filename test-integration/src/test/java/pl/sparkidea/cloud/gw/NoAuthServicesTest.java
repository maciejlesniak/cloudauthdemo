package pl.sparkidea.cloud.gw;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;

import io.restassured.builder.RequestSpecBuilder;
import java.util.stream.Stream;
import org.hamcrest.Matcher;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

public class NoAuthServicesTest {

  private static final CloudService GATEWAY_URL = new CloudService("http://localhost:8080", "service-gw");

  private static final CloudService SERVICE_A_URL = new CloudService("http://localhost:8090", "service-a");

  private static final CloudService SERVICE_B_URL = new CloudService("http://localhost:8091", "service-b");

  private static class TestCase {

    private final CloudService cloudService;

    private final String path;

    private final Matcher<String> bodyMatcher;

    public TestCase(
        CloudService cloudService,
        String path,
        Matcher<String> bodyMatcher) {

      this.cloudService = cloudService;
      this.path = path;
      this.bodyMatcher = bodyMatcher;
    }

    public CloudService getCloudService() {

      return cloudService;
    }

    public String getPath() {

      return path;
    }

    public Matcher<String> getBodyMatcher() {

      return bodyMatcher;
    }

    @Override
    public String toString() {

      return "TestCase{" +
          "cloudService=" + cloudService +
          ", path='" + path + '\'' +
          ", bodyMatcher=" + bodyMatcher +
          '}';
    }
  }

  private static Stream<TestCase> getTestCases() {

    return Stream.of(
        new TestCase(
            GATEWAY_URL,
            "/sa/test",
            is(String.format("response from %s as principal anonymousUser", SERVICE_A_URL.getServiceName()))),
        new TestCase(
            GATEWAY_URL,
            "/sb/test",
            is(String.format("response from %s as principal anonymousUser", SERVICE_B_URL.getServiceName()))),
        new TestCase(
            SERVICE_A_URL,
            "/sa/test",
            is(String.format("response from %s as principal anonymousUser", SERVICE_A_URL.getServiceName()))),
        new TestCase(
            SERVICE_B_URL,
            "/sb/test",
            is(String.format("response from %s as principal anonymousUser", SERVICE_B_URL.getServiceName())))
    );
  }


  @ParameterizedTest
  @MethodSource("getTestCases")
  public void testEndpoint_shouldBeResponsible_whenItIsCalledFromVariousServices(TestCase testCase) {

    RequestSpecBuilder rSpecBuilder = new RequestSpecBuilder();

    rSpecBuilder.setBaseUri(testCase.getCloudService().getServiceUrl());

    given()
        .spec(rSpecBuilder.build())
        .when()
        .get(testCase.getPath())
        .then()
        .assertThat()
        .body(testCase.getBodyMatcher());

  }

}
