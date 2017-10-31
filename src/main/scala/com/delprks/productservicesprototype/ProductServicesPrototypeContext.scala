package com.delprks.productservicesprototype

import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import com.delprks.productservicesprototype.client.OfferClient
import com.delprks.productservicesprototype.config.Config
import com.delprks.productservicesprototype.datasource.{OfferDataSource, OfferDataSourceImpl}
import slick.driver.SQLiteDriver.api._
import scala.concurrent.ExecutionContext

object ProductServicesPrototypeContext extends Config {
  implicit val system: ActorSystem = ActorSystem(serviceName)
  implicit val materializer: ActorMaterializer = ActorMaterializer()

  private val dbOperations: ExecutionContext = system.dispatchers.lookup("contexts.db-operations")
  private val cpuOperations: ExecutionContext = system.dispatchers.lookup("contexts.cpu-operations")
  private val database = Database.forConfig("database")

  private val offersClient = new OfferClient(database)(dbOperations)

  val offersDataSource: OfferDataSource = new OfferDataSourceImpl(offersClient)(cpuOperations)

}
