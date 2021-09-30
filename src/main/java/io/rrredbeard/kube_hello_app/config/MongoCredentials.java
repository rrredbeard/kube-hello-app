package io.rrredbeard.kube_hello_app.config;

import lombok.NonNull;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

import javax.validation.constraints.NotBlank;

@ConstructorBinding
@ConfigurationProperties(prefix = "hello-app.credentials.mongo")
public class MongoCredentials extends Credentials {

  protected MongoCredentials(
      @NotBlank @NonNull String username, @NotBlank @NonNull String password) {
    //
    super(username, password);
  }
}
