package io.rrredbeard.kube_hello_app.config;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.Slf4jRequestLogWriter;
import org.eclipse.jetty.webapp.WebAppContext;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.embedded.jetty.JettyServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.lang.NonNull;

@Configuration
public class CustomJettyConfiguration {

  private final int port;
  private final KubeHelloAppProperties properties;

  public CustomJettyConfiguration(
      @NonNull @Value("${server.port}") Integer port, @NonNull KubeHelloAppProperties properties) {
    //
    this.port = port;
    this.properties = properties;
  }

  @Bean
  public JettyServletWebServerFactory servletContainerFactory() {

    final JettyServletWebServerFactory factory = new JettyServletWebServerFactory(port);

    factory.addServerCustomizers(
        (@NonNull Server server) -> {
          final WebAppContext context = (WebAppContext) server.getHandler();
          final org.eclipse.jetty.webapp.Configuration[] configs = context.getConfigurations();

          final org.eclipse.jetty.webapp.Configuration[] modifiedConfigs =
              new org.eclipse.jetty.webapp.Configuration[configs.length + 1];

          System.arraycopy(configs, 0, modifiedConfigs, 0, configs.length);

          modifiedConfigs[configs.length] =
              MyCustomRequestLog.asConfiguration(new Slf4jRequestLogWriter(), properties.getLog());

          context.setConfigurations(modifiedConfigs);
        });

    return factory;
  }
}
