package me.dapperware.controllers

import me.dapperware.graphql.GraphQLActionToolkit
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

  /**
    * A custom action encompassing a GraphQL request
    */
  val graphQL = GraphQLActionToolkit[World](schema.schema, components.actionBuilder)

  /**
    * An endpoint for processing GraphQL queries
    */
  def graphql: EssentialAction = graphQL.async(parse.json)(World())

  /**
    * Computed on the first interaction and cached for all subsequent calls
    */
  private lazy val prerenderedGraphiql = Action(Ok(views.html.graphiql()))

  /**
    * Renders the GraphQL API explorer
    */
  def graphiql: EssentialAction = prerenderedGraphiql


}
