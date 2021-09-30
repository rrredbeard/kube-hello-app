package io.rrredbeard.kube_hello_app.config;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import org.springframework.context.annotation.Configuration;

@Configuration
@Getter
@AllArgsConstructor
@ToString(onlyExplicitlyIncluded = true)
public class CredentialsHolder {

  private final MongoCredentials mongo;

}
