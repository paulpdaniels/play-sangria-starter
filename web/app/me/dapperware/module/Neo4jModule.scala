package me.dapperware.module

import com.softwaremill.macwire.Module
import me.dapperware.config.Neo4jConfig
import org.neo4j.driver.v1.{AuthTokens, Driver, GraphDatabase, Session}

/**
  * Created by paulp on 7/13/2017.
  */
@Module
class Neo4jModule(config: Neo4jConfig) {

  lazy val driver: Driver = GraphDatabase.driver(
    config.uri,
    AuthTokens.basic(config.username, config.password)
  )

  lazy val session: Session = driver.session

}
