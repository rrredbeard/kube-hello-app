package io.rrredbeard.kube_hello_app.config;

import io.rrredbeard.kube_hello_app.utils.PrintableBean;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

import javax.validation.constraints.NotBlank;

@ConstructorBinding
@ConfigurationProperties(prefix = "hello-app")
@Getter
@AllArgsConstructor
@ToString(onlyExplicitlyIncluded = true)
public class KubeHelloAppProperties implements PrintableBean {

	@NotBlank
	@lombok.NonNull
	@ToString.Include
	private final String id;

	@NotBlank
	@lombok.NonNull
	@ToString.Include
	private final String version;

}
