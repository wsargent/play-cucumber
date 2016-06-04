package features.steps

import cucumber.api.scala.{EN, ScalaDsl}
import org.fluentlenium.core.filter.FilterConstructor._
import org.scalatest.Matchers
import play.api._
import play.api.test._

class StepDefinitions extends ScalaDsl with EN with Matchers {

  val webDriverClass = Helpers.HTMLUNIT

  // Use a custom loader (FakeApplication uses GuiceApplicationBuilder under the hood)
  val app = new loader.MyApplicationBuilder().build()

  implicit val port: play.api.http.Port = new play.api.http.Port(-1)

  lazy val browser: TestBrowser = TestBrowser.of(webDriverClass, Some("http://localhost:" + port))

  lazy val server = TestServer(port.value, app)

  def driver = browser.getDriver()

  Before() { s =>
    // initialize play-cucumber
    server.start()
  }

  After() { s =>
    // shut down play-cucumber
    server.stop()
    browser.quit()
  }

  Given("""^my application is running$""") { () =>
    Logger.debug("Yeah, application IS running")
  }

  When("""^I go to the "([^"]*)" page$""") { (pageName: String) =>
    val pageUrl = pageName match {
      case "start" => controllers.routes.HomeController.index.url
      case _ => throw new RuntimeException(s"unsupported page: $pageName")
    }
    browser.goTo(pageUrl)
  }

  Then("""^I should see "([^"]*)"$""") { (expectedText: String) =>
    val element = browser.find("body", withText().contains(expectedText))
    withClue("Expected text not found in body: " + expectedText) {
      element shouldNot be(empty)
    }
  }

}
