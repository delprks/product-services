package com.delprks.productservicesprototype.api

import akka.http.scaladsl.server.Directives._
import com.delprks.productservicesprototype.api.directives.CustomDirectives
import com.delprks.productservicesprototype.domain.response.ApiStatus
import com.delprks.productservicesprototype.domain.marshalling.JsonSerializers

trait StatusApi extends JsonSerializers
  with CustomDirectives {

  val statusRoutes = {
    path("status") {
      get {
        complete {
          ApiStatus("OK")
        }
      }
    }
  }
}
