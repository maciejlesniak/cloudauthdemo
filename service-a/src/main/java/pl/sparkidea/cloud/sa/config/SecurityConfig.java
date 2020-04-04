package pl.sparkidea.cloud.sa.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.SecurityBuilder;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

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
   * Configures in-memory database of the users that are recognised by this microservice
   *
   * @param auth Spring authentication manager builder, which is {@link SecurityBuilder} used to create an {@link
   * AuthenticationManager}
   * @throws Exception exception
   */
  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {

    auth.inMemoryAuthentication()
        .withUser("sa")
        .password("{noop}1234")
        .roles("sysRole_admin", "sysRole_test", "sysRole-AAccess");
  }

}
