package com.delprks.productservicesprototype.datasource

import com.delprks.productservicesprototype.api.directives.ResponseDirectives.MultiEntityResponseData
import com.delprks.productservicesprototype.client.OfferFilter
import com.delprks.productservicesprototype.domain.{Offer, OfferEvent}

import scala.concurrent.Future

trait OfferDataSource {
  def offer(id: Int): Future[Option[Offer]]
  def offers(limit: Int, offset: Int, filter: OfferFilter = OfferFilter()): Future[MultiEntityResponseData[Offer]]
  def create(offer: OfferEvent): Future[Int]
}
