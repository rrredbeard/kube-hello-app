package io.rrredbeard.kube_hello_app;

import io.rrredbeard.kube_hello_app.config.KubeHelloAppProperties;
import io.rrredbeard.kube_hello_app.dto.AppInfo;
import io.rrredbeard.kube_hello_app.utils.PrintableBean;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Slf4j
@SpringBootApplication
@EnableWebMvc
@EnableConfigurationProperties({ KubeHelloAppProperties.class })
public class KubeHelloApplication {

	public static void main(String[] args) {
		SpringApplication.run(KubeHelloApplication.class, args);
	}

	public static void logConfig(@NonNull PrintableBean bean) {
		if(log.isDebugEnabled()){
			log.debug("LOADED CONF :: {}", bean);
		}
	}


	/* ***/


	@RestController
	@RequestMapping(produces = APPLICATION_JSON_VALUE)
	@RequiredArgsConstructor
	public static class Controller {

		private final KubeHelloAppProperties properties;

		@NonNull
		@GetMapping
		public ResponseEntity<AppInfo> getInfo(){

			return ResponseEntity.ok(
				AppInfo.builder()
					.appId(properties.getId())
					.version(properties.getVersion())
					.build()
			);
		}

	}


}