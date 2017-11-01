package com.delprks.productservicesprototype.client

import com.delprks.productservicesprototype.domain.Status

case class OfferFilter(
  status: Option[Status.Type] = None,
  userId: Option[Int] = None
)
