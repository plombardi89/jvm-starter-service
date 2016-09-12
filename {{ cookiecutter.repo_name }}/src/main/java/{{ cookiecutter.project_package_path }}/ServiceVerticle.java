package {{ cookiecutter.project_package }};

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.http.HttpHeaders;
import io.vertx.core.http.HttpServer;
import io.vertx.core.http.HttpServerRequest;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.core.json.JsonObject;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.ext.web.Router;
import mdk.Functions;
import mdk.MDK;

import java.time.Instant;

public class ServiceVerticle extends AbstractVerticle {

  private final static Logger logger = LoggerFactory.getLogger(ServiceVerticle.class);

  private MDK mdk = null;

  public ServiceVerticle() {
    super();
  }

  @Override
  public void start(Future<Void> startFuture) throws Exception {
    mdk = Functions.init();
    mdk.start();
    logger.info("Datawire MDK started");

    Runtime.getRuntime().addShutdownHook(new Thread() {
      @Override public void run() {
        mdk.stop();
        logger.info("Datawire MDK stopped");
      }
    });

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

  private void registerWithDiscovery() {
    mdk.register(System.getProperty("mdk.service.name"),
                 System.getProperty("mdk.service.version"),
                 String.format("http://%s:%s", System.getProperty("mdk.service.host"),
                                               System.getProperty("mdk.service.port")))
  }
}
