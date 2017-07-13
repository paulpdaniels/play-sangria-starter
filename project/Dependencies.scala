import sbt._
/**
  * Created by paulp on 7/12/2017.
  */
object Dependencies {


  object Play26 {
    val sangriaPlay = "org.sangria-graphql" %% "sangria-play-json" % "1.0.3"
  }

  object Ext {
    val macwireVersion = "2.2.5"

    val sangria = "org.sangria-graphql" %% "sangria" % "1.2.2"
    val scalaTest = "org.scalatest" %% "scalatest" % "3.0.1" % "test"
    val macwireMacros = "com.softwaremill.macwire" %% "macros" % macwireVersion % "provided"
    val macwireRuntime = "com.softwaremill.macwire" %% "util" % macwireVersion
  }


}
