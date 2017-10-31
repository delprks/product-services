package com.delprks.productservicesprototype.api.directives

import akka.http.scaladsl.server.Directive1
import akka.http.scaladsl.server.Directives._
import com.delprks.productservicesprototype.api.rejection.InvalidOfferStatusEventRejection
import com.delprks.productservicesprototype.domain.{OfferEvent, OfferStatusEvent, Status}
import com.delprks.productservicesprototype.domain.marshalling.JsonSerializers

trait OfferDirectives extends JsonSerializers {
  final val validOfferStatusEvents = List(Status.Cancelled.toString, Status.Available.toString)

  def extractOfferEvent: Directive1[OfferEvent] = entity(as[OfferEvent]) flatMap { offer =>
    provide(offer)
  }

  def extractOfferStatusEvent: Directive1[OfferStatusEvent] = entity(as[OfferStatusEvent]) flatMap { offerStatus =>
    if (!validOfferStatusEvent(offerStatus.status)) {
      reject(InvalidOfferStatusEventRejection)
    } else {
      provide(offerStatus)
    }
  }

  private def validOfferStatusEvent(status: String): Boolean = {
    validOfferStatusEvents.contains(status)
  }
}
