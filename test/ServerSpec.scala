import org.scalatest.concurrent.ScalaFutures
import org.scalatestplus.play._
import play.api.libs.ws.WSClient
import play.api.mvc.Results
import play.api.test.Helpers._
import play.api.test.WsTestClient

class ServerSpec extends PlaySpec
  with OneServerPerSuiteWithMyComponents
  with Results
  with ScalaFutures {

  "Server query should" should {

    "work" in {
      implicit val ec = app.actorSystem.dispatchers.defaultGlobalDispatcher

      WsTestClient.withClient { ws: WSClient =>
        ws.url(s"http://localhost:${port}/").get().map { response =>
          response.status mustBe 200
        }
      }
    }
  }

}

