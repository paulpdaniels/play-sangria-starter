package me.dapperware.controllers

import me.dapperware.graphql.GraphQLAction
import me.dapperware.schema.{DapperSchemaBuilder, World}
import play.api.mvc.{AbstractController, ControllerComponents, EssentialAction}

import scala.concurrent.ExecutionContext


/**
  * Created by paulp on 7/12/2017.
  */
class DapperController(
  components: ControllerComponents,
  schema: DapperSchemaBuilder
) extends AbstractController(components) {

  private implicit val implicitExecutionContext = defaultExecutionContext

  val graphQL = new GraphQLAction[World](schema.schema, components.actionBuilder)

  def graphql: EssentialAction = graphQL.async(parse.json)(World())

  def graphiql: EssentialAction = Action(Ok)


}
