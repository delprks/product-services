package com.delprks.productservicesprototype.datasource

import com.delprks.productservicesprototype.api.directives.ResponseDirectives.MultiEntityResponseData
import com.delprks.productservicesprototype.client.OfferFilter
import com.delprks.productservicesprototype.domain.Offer

import scala.concurrent.Future

trait DataSource {
  def offer(id: Int): Future[Option[Offer]]
  def offers(limit: Int, offset: Int, filter: OfferFilter = OfferFilter()): Future[MultiEntityResponseData[Offer]]
}
