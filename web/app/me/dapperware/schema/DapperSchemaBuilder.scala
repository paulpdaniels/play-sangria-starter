package me.dapperware.schema

import sangria.schema.{Field, ObjectType, Schema}
import sangria.schema._
import sangria.macros.derive._

/**
  * Created by paulp on 7/12/2017.
  */
class DapperSchemaBuilder {

  val Query = ObjectType(
    "Query",
    fields[World, Unit](
      Field("hello", StringType, description = Some("Gotta start somewhere"), resolve = ctx => "world")
    )
  )

  val schema: Schema[World, Unit] = Schema(Query)
}


case class World()