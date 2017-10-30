package com.delprks.productservicesprototype.datasource

import com.delprks.productservicesprototype.api.directives.ResponseDirectives.MultiEntityResponseData
import com.delprks.productservicesprototype.client.{OfferClient, OfferFilter}
import com.delprks.productservicesprototype.domain.Offer

import scala.concurrent.{ExecutionContext, Future}

class OffersDataSourceImpl(offersClient: OfferClient)
  (implicit executionContext: ExecutionContext) extends DataSource {

  override def offer(id: Int): Future[Option[Offer]] = offersClient.offer(id)

  override def offers(limit: Int, offset: Int, filter: OfferFilter = OfferFilter()): Future[MultiEntityResponseData[Offer]] = {
    for {
      offers <- offersClient.offers(offset, limit, filter)
      total <- offersClient.offersCount(filter)
    } yield MultiEntityResponseData[Offer](
      entities = offers,
      total = total
    )
  }

}
