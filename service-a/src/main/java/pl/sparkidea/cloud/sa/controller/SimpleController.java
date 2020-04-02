package pl.sparkidea.cloud.sa.controller;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/sa")
public class SimpleController {

  private String appName;

  public SimpleController(@Value("${spring.application.name}") String appName) {

    this.appName = appName;
  }

  @RequestMapping(method = GET, path = "/test")
  public ResponseEntity<String> getTestResponse(@AuthenticationPrincipal String principal) {

    return ResponseEntity.ok(String.format("response from %s as principal %s", appName, principal));
  }

}