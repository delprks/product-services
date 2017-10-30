package com.delprks.productservicesprototype.client

import com.delprks.productservicesprototype.domain.Status

case class OfferFilter(
  status: Seq[Status.Type] = Seq()
)
