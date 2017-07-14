import Dependencies._

organization := "me.dapperware"

name := "play-sangria-starter"

version := "1.0"

lazy val baseSettings = Seq[SettingsDefinition](
  scalaVersion := "2.12.1",
  // Some libraries use different versions of scala than we do e.g. we use 2.11.6, and others might use 2.11.4 etc.
  // This forces use of the scala version we specify, and means we don't get an eviction notice.
  ivyScala := ivyScala.value map {
    _.copy(overrideScalaVersion = true)
  },
  publishArtifact := true,
  // Don't publish any test artifacts. We don't have any test code that others need
  publishArtifact in Test := false
)

lazy val root = (project in file("."))
  .settings(baseSettings: _*)
  .settings(publish := {}, publishLocal := {})
  .aggregate(web)

lazy val web = (project in file("./web"))
  .enablePlugins(PlayScala)
  .settings(baseSettings: _*)
  .settings(
    libraryDependencies ++= Seq(
      filters,
      Play26.sangriaPlay,
      Ext.macwireMacros,
      Ext.macwireRuntime,
      Ext.sangria,
      Ext.scalaTest,
      Ext.reactiveMongo,
      Ext.neo4j
    )
  )