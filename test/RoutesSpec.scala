import play.api.libs.ws.WSClient
import play.api.test.WsTestClient

class RoutesSpec extends MixedPlaySpecWithNoDefaultApp
{
  "send OK on router test" in new Server((new MyApplicationBuilder()).build()) {
    implicit val ec = app.actorSystem.dispatchers.defaultGlobalDispatcher

    WsTestClient.withClient { ws: WSClient =>
      ws.url(s"http://localhost:${port}/").get().map { response =>
        response.status mustBe 200
      }
    }
  }

}
