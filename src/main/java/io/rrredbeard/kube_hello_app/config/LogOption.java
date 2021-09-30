package io.rrredbeard.kube_hello_app.config;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import org.springframework.boot.context.properties.ConstructorBinding;

import javax.validation.constraints.NotBlank;

@ConstructorBinding
@Getter
@AllArgsConstructor
@ToString(onlyExplicitlyIncluded = true)
public class LogOption {

  @NotBlank @lombok.NonNull @ToString.Include private final Boolean showActuator;
}
