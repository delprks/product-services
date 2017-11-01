package com.delprks.productservicesprototype.api

import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import com.delprks.productservicesprototype.api.directives.CustomDirectives
import com.delprks.productservicesprototype.api.directives.ErrorResponseDirectives.ErrorResponseData
import com.delprks.productservicesprototype.api.error.ProductServicesPrototypeExceptionHandler
import com.delprks.productservicesprototype.client.OfferFilter
import com.delprks.productservicesprototype.config.Config
import com.delprks.productservicesprototype.datasource.OfferDataSource
import com.delprks.productservicesprototype.domain.marshalling.JsonSerializers
import org.omg.CosNaming.NamingContextPackage.NotFound
import org.slf4s.Logging

import scala.util.{Failure, Success}

trait OffersApi extends JsonSerializers
  with CustomDirectives
  with Config
  with Logging
  with ProductServicesPrototypeExceptionHandler {

  def offersDataSource: OfferDataSource

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
      } ~ post {
        jsonContentType {
          extractOfferEvent { offer =>
            onComplete(offersDataSource.create(offer)) {
              case Success(_) => complete(StatusCodes.Accepted)
              case Failure(exception) => routingExceptionHandler(exception)
            }
          }
        }
      }
    } ~ path("offers" / IntNumber) { id =>
      get {
        onComplete(offersDataSource.offer(id)) {
          case Success(None) => completeWithError(
            schemaUrl = errorSchemaUrl,
            documentationUrl = errorDocumentationUrl
          )(ErrorResponseData(statusCode = StatusCodes.NotFound, message = s"$id was not found"))
          case Success(Some(offer)) => complete(toResponse(offerSchemaUrl)(offer))
          case Failure(exception) => routingExceptionHandler(exception)
        }
      }
    } ~ path("offers" / IntNumber / "status") { id =>
      put {
        jsonContentType {
          extractOfferStatusEvent { offerStatus =>
            onComplete(offersDataSource.updateStatus(id, offerStatus.status)) {
              case Success(_) => complete(StatusCodes.Accepted)
              case Failure(exception) => routingExceptionHandler(exception)
            }
          }
        }
      }
    }
  }
}
