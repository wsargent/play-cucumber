import org.scalatestplus.play._

class SeleniumSpec
  extends PlaySpec
    with OneServerPerTestWithMyComponents
    with OneBrowserPerTest
    with HtmlUnitFactory {

  "The browser based tests" should {

    "work from within a browser" in {

      go to ("http://localhost:" + port)

      pageSource must include ("Your new application is ready.")
    }
  }
}
