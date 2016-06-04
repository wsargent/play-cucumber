package loader

import controllers.{Assets, HomeController}
import play.api._
import play.api.cache.EhCacheComponents
import play.api.i18n._
import play.api.inject._
import play.api.routing.Router
import play.core.DefaultWebCommands
import router.Routes

class MyApplicationLoader extends ApplicationLoader {

  override def load(context: ApplicationLoader.Context): Application = {
    Logger.configure(context.environment)
    new MyComponents(context).application
  }
}

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

  override lazy val router: Router = new Routes(httpErrorHandler, homeController, assets)

  lazy val homeController = new HomeController()

  lazy val assets = new Assets(httpErrorHandler)
}
