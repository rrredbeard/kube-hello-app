package io.rrredbeard.kube_hello_app.config;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import org.springframework.context.annotation.Configuration;
import org.springframework.lang.NonNull;
import org.springframework.util.Assert;

import javax.annotation.PostConstruct;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Configuration
@Getter
@AllArgsConstructor
@ToString(onlyExplicitlyIncluded = true)
public class CredentialsHolder {

  private static final Map<String, Credentials> credentialsMap = new HashMap<>();
  private final MongoCredentials mongo;

  @PostConstruct
  private void initMap() {

    Assert.state(credentialsMap.isEmpty(), "CredentialsMap should be empty");

    credentialsMap.put("mongo", getMongo());
  }

  @NonNull
  public final Map<String, Credentials> getCredentialsMap() {
    return Collections.unmodifiableMap(credentialsMap);
  }
}
