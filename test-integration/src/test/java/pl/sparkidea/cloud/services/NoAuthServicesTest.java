package pl.sparkidea.cloud.services;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;
import static pl.sparkidea.cloud.Topology.SERVICE_A_URL;
import static pl.sparkidea.cloud.Topology.SERVICE_B_URL;

import io.restassured.builder.RequestSpecBuilder;
import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import pl.sparkidea.cloud.TestCase;

public class NoAuthServicesTest {

  private static Stream<TestCase<String>> getTestCases() {

    return Stream.of(
        new TestCase<>(
            SERVICE_A_URL,
            "/sa/test",
            is(String.format("response from %s as principal anonymousUser", SERVICE_A_URL.getServiceName()))),
        new TestCase<>(
            SERVICE_B_URL,
            "/sb/test",
            is(String.format("response from %s as principal anonymousUser", SERVICE_B_URL.getServiceName())))
    );
  }


  @ParameterizedTest
  @MethodSource("getTestCases")
  public void testEndpoint_shouldBeResponsible_whenItIsCalledFromVariousServices(TestCase<String> testCase) {

    RequestSpecBuilder rSpecBuilder = new RequestSpecBuilder();

    rSpecBuilder.setBaseUri(testCase.getCloudService().getServiceUrl());

    given()
        .spec(rSpecBuilder.build())
        .when()
        .get(testCase.getPath())
        .then()
        .assertThat()
        .body(testCase.getMatcher());

  }

}
