package pl.sparkidea.cloud.sa.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.SecurityBuilder;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configurers.provisioning.InMemoryUserDetailsManagerConfigurer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import pl.sparkidea.spring.config.InMemoryAuthenticatedUsers;

@Configuration
@EnableConfigurationProperties(InMemoryAuthenticatedUsers.class)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

  private static final Logger LOG = LoggerFactory.getLogger(SecurityConfig.class);

  @Autowired
  private InMemoryAuthenticatedUsers inMemoryAuthenticatedUsers;

  @Override
  protected void configure(HttpSecurity http) throws Exception {

    // @formatter:off
    http.antMatcher("/**")
        .csrf().disable()
        .csrf().disable()
        .authorizeRequests()
        .antMatchers("/sa/test").anonymous()
        .antMatchers("/sa/**").authenticated()
        .and()
        .httpBasic().realmName("CloudAuthDemo")
        .and()
        .formLogin().disable();
    // @formatter:on
  }

  /**
   * Configures in-memory database of the users that are recognised by this microservice using Spring properties file
   *
   * @param auth Spring authentication manager builder, which is {@link SecurityBuilder} used to create an {@link
   * AuthenticationManager}
   * @throws Exception exception
   */
  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {

    LOG.info("in memory users {}", inMemoryAuthenticatedUsers.getUsers().toString());

    InMemoryUserDetailsManagerConfigurer<AuthenticationManagerBuilder> inMemoryUserDetailsManagerConfigurer =
        auth.inMemoryAuthentication();

    inMemoryAuthenticatedUsers.getUsers()
        .forEach(user -> inMemoryUserDetailsManagerConfigurer
            .withUser(user.getName())
            .password(user.getPassword())
            .roles(user.getRoles())
        );

  }

}
