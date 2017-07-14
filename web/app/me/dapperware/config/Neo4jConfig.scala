package me.dapperware.config

import play.api.Configuration
import ConfigurationImplicits._

/**
  * Remove if unneeded
  */
class Neo4jConfig(configuration: Configuration) {

  lazy val uri = configuration.getOrElse[String]("neo4j.default.uri", "bolt://localhost:7474")

  lazy val username = configuration.getOrElse[String]("neo4j.default.username", "neo4j")

  lazy val password = configuration.getOrElse[String]("neo4j.default.password", "admin")
}
