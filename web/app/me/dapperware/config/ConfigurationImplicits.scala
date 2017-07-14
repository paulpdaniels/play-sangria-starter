package me.dapperware.config

import play.api.{ConfigLoader, Configuration}

import scala.util.Try

/**
  * Created by paulp on 7/13/2017.
  */
object ConfigurationImplicits {

  implicit class RichConfiguration(val configuration: Configuration) extends AnyVal {

    def getOrElse[T: ConfigLoader](path: String, default: => T): T =
      configuration.getOptional[T](path) getOrElse default

    def getAndValidatedOrElse[T: ConfigLoader](path: String, validated: Set[T], default: => T): T =
      Try(configuration.getAndValidate(path, validated)).toOption getOrElse default
  }
}
