package com.delprks.productservicesprototype.api.directives

import akka.http.scaladsl.server.Directive1
import akka.http.scaladsl.server.Directives._
import com.delprks.productservicesprototype.domain.OfferEvent
import com.delprks.productservicesprototype.domain.marshalling.JsonSerializers

trait OfferDirectives extends JsonSerializers {
  def extractOfferEvent: Directive1[OfferEvent] = entity(as[OfferEvent]) flatMap { offer =>
    provide(offer)
  }
}
