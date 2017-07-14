package me.dapperware

import play.api.mvc.EssentialFilter
import play.api.routing.Router
import play.api.{Application, ApplicationLoader, BuiltInComponentsFromContext}
import com.softwaremill.macwire._
import me.dapperware.config.{MongoConfig, Neo4jConfig}
import me.dapperware.controllers.DapperController
import me.dapperware.module.{MongoModule, Neo4jModule}
import me.dapperware.schema.DapperSchemaBuilder
import play.filters.cors.CORSComponents
import router.Routes

/**
  * Created by paulp on 7/12/2017.
  */
class DapperApplicationLoader extends ApplicationLoader {
  override def load(context: ApplicationLoader.Context): Application =
    new DapperComponents(context).application
}

class DapperComponents(context: ApplicationLoader.Context)
  extends BuiltInComponentsFromContext(context)
  with CORSComponents {

  // Configs
  lazy val mongoConfig = wire[MongoConfig]
  lazy val neo4jConfig = wire[Neo4jConfig]

  // DBs
  lazy val mongoModule: MongoModule = wire[MongoModule]
  lazy val neo4jModule: Neo4jModule = wire[Neo4jModule]

  // GraphQL Schema
  lazy val schema = wire[DapperSchemaBuilder]

  // Controllers
  lazy val dapperController = wire[DapperController]


  // Routes
  lazy val router: Router = {
    val prefix = "/"

    wire[Routes]
  }

  override def httpFilters: Seq[EssentialFilter] = Seq(corsFilter)
}
