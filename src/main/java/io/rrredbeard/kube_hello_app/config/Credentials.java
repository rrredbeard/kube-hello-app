package io.rrredbeard.kube_hello_app.config;

import io.rrredbeard.kube_hello_app.utils.PrintableBean;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;

@Getter
@ToString(onlyExplicitlyIncluded = true)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
class Credentials implements PrintableBean {

  @NotBlank @lombok.NonNull @ToString.Include private final String username;

  @NotBlank @lombok.NonNull @ToString.Include private final String password;
}
