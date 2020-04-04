package pl.sparkidea.cloud.services;

import static io.restassured.RestAssured.given;
import static java.net.HttpURLConnection.HTTP_OK;
import static java.net.HttpURLConnection.HTTP_UNAUTHORIZED;
import static org.hamcrest.Matchers.is;
import static pl.sparkidea.cloud.Topology.SERVICE_A_URL;
import static pl.sparkidea.cloud.Topology.SERVICE_B_URL;

import io.restassured.builder.RequestSpecBuilder;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import pl.sparkidea.cloud.TestCase;

public class AuthTestEndpointTest {


  private static Stream<TestCase<String>> getTestCases() {

    return Stream.of(
        new TestCase<>(
            SERVICE_A_URL,
            "/sa/auth-test",
            is(String.format("response from %s as principal anonymousUser", SERVICE_A_URL.getServiceName()))),
        new TestCase<>(
            SERVICE_B_URL,
            "/sb/auth-test",
            is(String.format("response from %s as principal anonymousUser", SERVICE_B_URL.getServiceName())))
    );
  }


  @ParameterizedTest
  @MethodSource("getTestCases")
  public void securedTestEndpoint_shouldBeNotAvailableForAnonymousUser(TestCase<String> testCase) {

    RequestSpecBuilder rSpecBuilder = new RequestSpecBuilder();

    rSpecBuilder.setBaseUri(testCase.getCloudService().getServiceUrl());

    given()
        .spec(rSpecBuilder.build())
        .when()
        .get(testCase.getPath())
        .then()
        .statusCode(HTTP_UNAUTHORIZED);
  }

  @Test
  public void securedTestEndpoint_shouldRespondServiceA_whenAuthenticatedRequest() {

    RequestSpecBuilder rSpecBuilder = new RequestSpecBuilder();
    rSpecBuilder.setBaseUri(SERVICE_A_URL.getServiceUrl());

    given()
        .auth().preemptive().basic("sa", "1234")
        .spec(rSpecBuilder.build())
        .when()
        .get("/sa/auth-test")
        .then()
        .statusCode(HTTP_OK)
        .and()
        .body(is("response from service-a as user sa"));
  }

  @Test
  public void securedTestEndpoint_shouldRespondServiceB_whenAuthenticatedRequest() {

    RequestSpecBuilder rSpecBuilder = new RequestSpecBuilder();
    rSpecBuilder.setBaseUri(SERVICE_B_URL.getServiceUrl());

    given()
        .auth().preemptive().basic("sb", "4321")
        .spec(rSpecBuilder.build())
        .when()
        .get("/sb/auth-test")
        .then()
        .statusCode(HTTP_OK)
        .and()
        .body(is("response from service-b as user sb"));
  }


}
