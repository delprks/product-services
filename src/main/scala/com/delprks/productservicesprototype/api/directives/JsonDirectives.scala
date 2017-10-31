package com.delprks.productservicesprototype.api.directives

import akka.http.scaladsl.model.ContentTypes
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import com.delprks.productservicesprototype.api.rejection.InvalidHeadersRejection

trait JsonDirectives extends ErrorResponseDirectives {
  def jsonContentType(route: Route): Route = extract(_.request.entity.contentType) {
    case ContentTypes.`application/json` =>
      route
    case _ =>
      reject(InvalidHeadersRejection())
  }
}
