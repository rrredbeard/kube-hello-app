package io.rrredbeard.kube_hello_app.config;

import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.RequestLog;
import org.eclipse.jetty.server.Response;
import org.eclipse.jetty.util.DateCache;
import org.eclipse.jetty.util.component.ContainerLifeCycle;
import org.eclipse.jetty.util.component.LifeCycle;
import org.eclipse.jetty.webapp.AbstractConfiguration;
import org.eclipse.jetty.webapp.WebAppContext;
import org.slf4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;

import java.util.Locale;
import java.util.TimeZone;

import static java.lang.String.format;

class MyCustomRequestLog extends ContainerLifeCycle implements RequestLog {

  @NonNull
  public static AbstractConfiguration asConfiguration(
      @NonNull Writer writer, @NonNull LogOption logOption) {
    //
    return new AbstractConfiguration() {

      @Override
      public void configure(@NonNull WebAppContext context) {
        context.getServer().setRequestLog(new MyCustomRequestLog(writer, logOption));
      }
    };
  }

  private static final Logger log = org.slf4j.LoggerFactory.getLogger(RequestLog.class);
  private static final ThreadLocal<StringBuilder> BUFFER_HOLDER =
      ThreadLocal.withInitial(() -> new StringBuilder(256));

  private final Writer writer;
  private final DateCache dateCache;
  private final LogOption logOption;

  public MyCustomRequestLog(Writer writer, LogOption logOption) {

    this.writer = writer;
    this.logOption = logOption;
    this.dateCache =
        new DateCache("yyyy-MM-dd'T'HH:mm:ss.SSSXXX", Locale.getDefault(), TimeZone.getDefault());

    this.addBean(this.writer);
  }

  @Override
  public void log(Request request, Response response) {

    final String requestURI = request.getRequestURI();

    if (!logOption.getShowActuator() && requestURI.startsWith("/actuator")) {
      return;
    }

    try {
      final StringBuilder sb = BUFFER_HOLDER.get();

      sb.setLength(0);
      sb.append("[")
          .append(dateCache.format(request.getTimeStamp()))
          .append("] - [from: ")
          .append(
              request
                  .getHttpChannel()
                  .getEndPoint()
                  .getRemoteAddress()
                  .getAddress()
                  .getHostAddress())
          .append("] :: ")
          .append(
              format(
                  "%s %s - %d bytes | %s %s",
                  request.getProtocol(),
                  HttpStatus.resolve(response.getStatus()),
                  response.getHttpChannel().getBytesWritten(),
                  request.getMethod(),
                  requestURI));

      writer.write(sb.toString());

    } catch (Exception e) {

      log.warn("Unable to log request", e);
    }
  }

  @Override
  protected void stop(LifeCycle lifeCycle) throws Exception {
    BUFFER_HOLDER.remove();

    super.stop(lifeCycle);
  }
}
