package {{ cookiecutter.project_package }};

import io.vertx.core.AbstractVerticle;
import io.vertx.core.http.HttpHeaders;
import io.vertx.core.http.HttpServer;
import io.vertx.core.http.HttpServerRequest;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.core.json.JsonObject;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.ext.web.Router;

import java.time.Instant;

public class ServiceVerticle extends AbstractVerticle {

  private final static Logger logger = LoggerFactory.getLogger(ServiceVerticle.class);

  public ServiceVerticle() {
    super();
  }

  @Override
  public void start(Future<Void> startFuture) throws Exception {
    logger.info("Datawire MDK started");
    super.start(startFuture);
  }

  public void start() throws Exception {
    HttpServer server = vertx.createHttpServer();
    Router router = Router.router(vertx);

    router.get("/hello/:name").handler( routingContext -> {
      HttpServerRequest request = routingContext.request();

      JsonObject json = new JsonObject()
          .put("message", "Hello, " + request.getParam("name") + "!")
          .put("time", Instant.now().toEpochMilli());

      HttpServerResponse response = routingContext.response();
      response.putHeader(HttpHeaders.CONTENT_TYPE, "application/json");
      response.end(json.encode());
    });

    server.requestHandler(router::accept).listen(config().getInteger("port", 5000));
  }
}
