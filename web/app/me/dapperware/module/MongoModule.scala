package me.dapperware.module

import com.softwaremill.macwire._
import me.dapperware.config.MongoConfig
import reactivemongo.api.{MongoConnection, MongoDriver}

import scala.concurrent.{ExecutionContext, Future}

/**
  * Created by paulp on 7/13/2017.
  */
@Module
class MongoModule(mongoConfig: MongoConfig)(implicit ec: ExecutionContext) {

  private val driver: MongoDriver = MongoDriver()

  private val uri = MongoConnection.parseURI(mongoConfig.uri)

  lazy val mongoConnection: Future[MongoConnection] =
    Future.fromTry(uri).map(driver.connection)

}
