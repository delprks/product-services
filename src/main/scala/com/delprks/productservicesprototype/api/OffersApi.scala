package com.delprks.productservicesprototype.api

import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import com.delprks.productservicesprototype.api.directives.CustomDirectives
import com.delprks.productservicesprototype.api.error.ProductServicesPrototypeExceptionHandler
import com.delprks.productservicesprototype.client.OfferFilter
import com.delprks.productservicesprototype.config.Config
import com.delprks.productservicesprototype.datasource.DataSource
import com.delprks.productservicesprototype.domain.marshalling.JsonSerializers
import org.slf4s.Logging

import scala.util.{Failure, Success}

trait OffersApi extends JsonSerializers
  with CustomDirectives
  with Config
  with Logging
  with ProductServicesPrototypeExceptionHandler {

  def offersDataSource: DataSource

  val offerRoutes: Route = {
    path("offers") {
      get {
        paginate(defaultPageLimit, maximumPageLimit) { pagination =>
          extractFilteringParameters {
            case (availabilityStatus, userId) =>
              onComplete(toResponse(offerSchemaUrl, pagination) {
                log.info("/offers")
                offersDataSource.offers(
                  pagination.limit,
                  pagination.offset,
                  OfferFilter(status = availabilityStatus, userId = userId)
                )
              }) {
                case Success(response) => complete(response)
                case Failure(exception) => routingExceptionHandler(exception)
              }
          }
        }
      }
    } ~ path("offers" / IntNumber) { id =>
      get {
        complete {
          log.info(s"/offers/$id")
          toResponse(offerSchemaUrl) {
            offersDataSource.offer(id)
          }
        }
      }
    }
  }
}
