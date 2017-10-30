package com.delprks.productservicesprototype.api

import akka.actor.ActorSystem
import akka.http.scaladsl.server.Directives._
import akka.stream.Materializer
import com.delprks.productservicesprototype.ProductServicesPrototypeContext
import com.delprks.productservicesprototype.api.error.ProductServicesPrototypeExceptionHandler
import com.delprks.productservicesprototype.api.rejection.ProductServicesPrototypeRejectionHandler
import com.delprks.productservicesprototype.datasource.DataSource

import scala.concurrent.ExecutionContextExecutor

trait Api extends StatusApi
  with OffersApi
  with ProductServicesPrototypeRejectionHandler
  with ProductServicesPrototypeExceptionHandler {

  override def offersDataSource: DataSource = ProductServicesPrototypeContext.offersDataSource

  lazy val routes = {
    logRequestResult("product-services-prototype") {
      statusRoutes ~ offerRoutes
    }
  }
}
