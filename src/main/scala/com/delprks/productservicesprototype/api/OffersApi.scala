package com.delprks.productservicesprototype.api

import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import com.delprks.productservicesprototype.api.directives.CustomDirectives
import com.delprks.productservicesprototype.config.Config
import com.delprks.productservicesprototype.datasource.DataSource
import com.delprks.productservicesprototype.domain.marshalling.JsonSerializers
import org.slf4s.Logging

trait OffersApi extends JsonSerializers
  with CustomDirectives
  with Config
  with Logging {

  def offersDataSource: DataSource

  val offerRoutes: Route = {
    path("offers") {
      get {
        respondWithCacheHeaders {
          paginate(defaultPageLimit, maximumPageLimit) { pagination =>
            sort { offerSort =>
              complete {
                log.info("/offers")
                toResponse(offerSchemaUrl, pagination) {
                  offersDataSource.offers(pagination.limit, pagination.offset)
                }
              }
            }
          }
        }
      }
    } ~ path("offers" / IntNumber) { id =>
      get {
        respondWithCacheHeaders {
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
}
