import play.api.ApplicationLoader.Context
import play.api._
import play.api.cache.EhCacheComponents
import play.api.i18n._
import play.api.inject._
import play.api.routing.Router
import router.Routes
import play.core.DefaultWebCommands

class MyApplicationLoader extends ApplicationLoader {

  override def load(context: ApplicationLoader.Context): Application = {
    Logger.configure(context.environment)
    new MyComponents(context).application
  }
}

/**
 * An application builder for running an application in tests
 */
class MyApplicationBuilder {

  def build(): Application = {
    val env = Environment.simple()
    val context = new ApplicationLoader.Context(
      environment = env,
      sourceMapper = None,
      webCommands = new DefaultWebCommands(),
      initialConfiguration = Configuration.load(env)
    )
    val loader = new MyApplicationLoader()
    loader.load(context)
  }
}

class MyComponents(context: ApplicationLoader.Context) 
  extends BuiltInComponentsFromContext(context)
  with I18nComponents
  with EhCacheComponents {

  override lazy val injector =  {
    new SimpleInjector(NewInstanceInjector) +
      router +
      httpConfiguration +
      tempFileCreator +
      messagesApi +
      defaultCacheApi +
      crypto
  }

  lazy val homeController = new controllers.HomeController()

  lazy val router: Router = new Routes(httpErrorHandler, homeController, assets)

  lazy val assets = new controllers.Assets(httpErrorHandler)
}
