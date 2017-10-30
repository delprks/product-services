package com.delprks.productservicesprototype.api

import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import com.delprks.productservicesprototype.ProductServicesPrototypeContext
import com.delprks.productservicesprototype.api.error.ProductServicesPrototypeExceptionHandler
import com.delprks.productservicesprototype.api.rejection.ProductServicesPrototypeRejectionHandler
import com.delprks.productservicesprototype.datasource.DataSource

trait Api extends OffersApi
  with ProductServicesPrototypeRejectionHandler
  with ProductServicesPrototypeExceptionHandler {

  override def offersDataSource: DataSource = ProductServicesPrototypeContext.offersDataSource

  lazy val routes: Route = {
    logRequestResult("product-services-prototype") {
      offerRoutes
    }
  }
}
