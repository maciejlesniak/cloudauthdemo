package pl.sparkidea.cloud.sa.controller;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
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

  @RequestMapping(method = GET, path = "/auth-test")
  @PreAuthorize("hasAnyRole('sysRole_admin', 'sysRole_test', 'sysRole-AAccess')")
  public ResponseEntity<String> getAuthenticatedTestResponse(@AuthenticationPrincipal User user) {

    return ResponseEntity.ok(String.format("response from %s as user %s", appName, user.getUsername()));
  }

}
