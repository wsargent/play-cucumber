package features.steps

import org.scalatest.Matchers
import cucumber.api.scala.{ ScalaDsl, EN }
import cucumber.api.DataTable
import cucumber.api.PendingException

import play.api.test._
import play.api._
import play.api.mvc._

import org.openqa.selenium._
import org.fluentlenium.core.filter.FilterConstructor._


class StepDefinitions extends ScalaDsl with EN with Matchers {

  val webDriverClass = Helpers.HTMLUNIT

  val app = FakeApplication()
  val port = 3333 // or whatever you want

  lazy val browser: TestBrowser = TestBrowser.of(webDriverClass, Some("http://localhost:" + port))
  lazy val server = TestServer(port, app)
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
