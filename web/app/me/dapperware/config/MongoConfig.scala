package me.dapperware.config

import play.api.{ConfigLoader, Configuration}
import ConfigurationImplicits._

import scala.util.Try

/**
  * Remove if unneeded.
  */
class MongoConfig(configuration: Configuration) {

 lazy val uri: String = configuration.getOrElse[String]("mongo.uri", "mongo://localhost")
}




