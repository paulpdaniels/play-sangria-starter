package me.dapperware.graphql

/**
  * Created by paulp on 7/12/2017.
  */
import play.api.libs.json.{JsError, JsObject, JsValue, Json}
import play.api.mvc._
import sangria.ast.Document
import sangria.execution.deferred.DeferredResolver
import sangria.execution.{ErrorWithResolver, Executor, QueryAnalysisError}
import sangria.marshalling.playJson._
import sangria.parser.{QueryParser, SyntaxError}
import sangria.schema.Schema

import scala.concurrent.{ExecutionContext, Future}
import scala.util.control.NonFatal

class GraphQLActionToolkit[Ctx](
  schema: Schema[Ctx, Unit],
  actionBuilder: ActionBuilder[Request, AnyContent],
  resolver: DeferredResolver[Ctx] = DeferredResolver.empty
)(implicit ec: ExecutionContext) extends Results {

  def async(bodyParser: BodyParser[JsValue])(context: Ctx): Action[JsValue] =
    actionBuilder.async(bodyParser) { request =>
      request.body.validate[GraphQLQuery].map {
        case GraphQLQuery(query, operationName, variables) =>
          Future.fromTry(QueryParser.parse(query))
            .flatMap(executeRequest(_, context, operationName, variables, resolver))
            .map(Ok(_))
            .recover {
              case error: QueryAnalysisError => BadRequest(error.resolveError)
              case error: ErrorWithResolver => InternalServerError(error.resolveError)
              case error: SyntaxError => BadRequest(
                Json.obj(
                  "syntaxError" → error.getMessage,
                  "locations" → Json.arr(
                    Json.obj(
                      "line" → error.originalError.position.line,
                      "column" → error.originalError.position.column
                    )
                  )
                )
              )
              case NonFatal(e) => InternalServerError(e.getMessage)
            }
      }.recoverTotal {
        errors => Future.successful(BadRequest(JsError.toJson(errors)))
      }
    }

  private def executeRequest(
    queryAst: Document,
    userContext: Ctx,
    operation: Option[String],
    variables: JsObject,
    deferredResolver: DeferredResolver[Ctx]
  ): Future[JsValue] =
    Executor
      .execute(
        schema,
        queryAst,
        userContext,
        operationName = operation,
        variables = variables,
        deferredResolver = deferredResolver
      )
}

object GraphQLActionToolkit {

  def apply[Ctx](
    schema: Schema[Ctx, Unit],
    actionBuilder: ActionBuilder[Request, AnyContent],
    resolver: DeferredResolver[Ctx] = DeferredResolver.empty
  ): GraphQLActionToolkit[Ctx] =
    new GraphQLActionToolkit[Ctx](schema, actionBuilder, resolver)
}
