package me.dapperware.graphql

import play.api.libs.json._
import play.api.libs.functional.syntax._

case class GraphQLQuery(
  query: String,
  operationName: Option[String],
  variables: JsObject
)

object GraphQLQuery {

  private def parseVariables(variables: String) =
    if (variables.trim == "" || variables.trim == "null") Json.obj() else Json.parse(variables).as[JsObject]

  private val variableReads: Reads[JsObject] =
    __.read[JsObject] or __.read[JsString].map { v => parseVariables(v.value) }

  implicit val reads: Reads[GraphQLQuery] = (
    (__ \ "query").read[String] ~
      (__ \ "operationName").readNullable[String] ~
      (__ \ "variables").readNullable[JsObject](variableReads).fmap(_.getOrElse(Json.obj()))
    )(GraphQLQuery.apply _)

}