package me.dapperware

import play.api.mvc.EssentialFilter
import play.api.routing.Router
import play.api.{Application, ApplicationLoader, BuiltInComponentsFromContext}
import com.softwaremill.macwire._
import router.Routes

/**
  * Created by paulp on 7/12/2017.
  */
class DapperApplicationLoader extends ApplicationLoader {
  override def load(context: ApplicationLoader.Context): Application =
    new DapperComponents(context).application
}

class DapperComponents(context: ApplicationLoader.Context)
  extends BuiltInComponentsFromContext(context) {


  lazy val router: Router = {
    val prefix = "/"

    wire[Routes]
  }

  override def httpFilters: Seq[EssentialFilter] = ???
}
