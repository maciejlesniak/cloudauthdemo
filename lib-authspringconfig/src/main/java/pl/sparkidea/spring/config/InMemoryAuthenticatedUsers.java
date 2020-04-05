package pl.sparkidea.spring.config;

import java.util.Arrays;
import java.util.Set;
import org.springframework.boot.context.properties.ConfigurationProperties;


@ConfigurationProperties(prefix = "spring.security.auth.inmemory")
public class InMemoryAuthenticatedUsers {

  private Set<User> users;

  public Set<User> getUsers() {

    return users;
  }

  public void setUsers(Set<User> users) {

    this.users = users;
  }


  public static class User {

    private String name;

    private String password;

    private String[] roles;

    public String getName() {

      return name;
    }

    public void setName(String username) {

      this.name = username;
    }

    public String getPassword() {

      return password;
    }

    public void setPassword(String password) {

      this.password = password;
    }

    public String[] getRoles() {

      return roles;
    }

    public void setRoles(String[] roles) {

      this.roles = roles;
    }

    @Override
    public String toString() {

      return '{' + "username='" + name + '\'' +
          ", roles=" + Arrays.toString(roles) +
          '}';
    }
  }


}
